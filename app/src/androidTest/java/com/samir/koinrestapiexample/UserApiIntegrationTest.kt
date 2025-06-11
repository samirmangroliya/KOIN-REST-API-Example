package com.samir.koinrestapiexample

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samir.koinrestapiexample.data.apiservice.UserApiService
import com.samir.koinrestapiexample.data.model.User
import com.samir.koinrestapiexample.data.repository.UserRepositoryImpl
import com.samir.koinrestapiexample.domain.UserRepository
import com.samir.koinrestapiexample.domain.UserUseCase
import com.samir.koinrestapiexample.presentation.activity.MainActivity
import com.samir.koinrestapiexample.presentation.utils.NetworkResult
import com.samir.koinrestapiexample.presentation.viewmodels.UserViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class UserApiIntegrationTest {

    private lateinit var api: UserApiService
    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserViewModel

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        api = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)

        repository = UserRepositoryImpl(api)
        viewModel = UserViewModel(UserUseCase(repository))
    }

    @Test
    fun testGetUsersAndCheckResult() = runBlocking {
        viewModel.getUserData()

        val result = viewModel.users.first { it !is NetworkResult.Loading }

        when (result) {
            is NetworkResult.Success -> {
                Log.d("Test Result", "User count: ${result.data.size}")
                Assert.assertTrue(result.data.isNotEmpty())
                Assert.assertEquals(10, result.data.size)
                testUI(activityRule, result.data)
            }
            is NetworkResult.Error -> {
                Assert.fail("API call failed: ${result.message}")
            }
            else -> {
                Assert.fail("Unexpected result type")
            }
        }
    }

    fun testUI(activityRule: ActivityScenarioRule<MainActivity>, list: List<User>) {
        activityRule.scenario.onActivity { activityScenarioRule ->
            val recyclerView = activityScenarioRule.findViewById<RecyclerView>(R.id.recyclerview)
            val itemCount = recyclerView.adapter?.itemCount?:0

            assertThat(itemCount, `is`<Int>(list.size))
        }

        Log.d(this@UserApiIntegrationTest.javaClass.simpleName, "Name on 0th position: ${list[0].name}")
        Espresso.onView(
            allOf(
                withId(R.id.tvname),
                isDescendantOfA(UserApiIntegrationTest().nthChildOf(withId(R.id.recyclerview), 0))
            )
        ).check(matches(withText(list[0].name)))
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && parent.getChildAt(childPosition) == view
            }

            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("Nth child of parent ")
                parentMatcher.describeTo(description)
                description.appendText(" at position $childPosition")
            }

        }
    }
}

