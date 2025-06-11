package com.samir.koinrestapiexample

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samir.koinrestapiexample.data.repository.UserRepositoryImpl
import com.samir.koinrestapiexample.di.networkModule
import com.samir.koinrestapiexample.domain.UserRepository
import com.samir.koinrestapiexample.domain.UserUseCase
import com.samir.koinrestapiexample.presentation.activity.MainActivity
import com.samir.koinrestapiexample.presentation.utils.NetworkResult
import com.samir.koinrestapiexample.presentation.viewmodels.UserViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class UserApiIntegrationKoinTest : KoinTest {

    private lateinit var viewModel: UserViewModel

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        //need to stop koin if its already started
        stopKoin()
        // Start Koin with both your modules: networkModule and scoped MainActivity scope module
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                networkModule,
                module {
                    scope(named("activityScope")) {
                        scoped<UserRepository> { UserRepositoryImpl(get()) }
                        scoped { UserUseCase(get()) }
                        scoped { UserViewModel(get()) }
                    }
                }
            )
        }

        // Open the MainActivity scope & get ViewModel
        val scope = getKoin().createScope("testScopeId", named("activityScope"))
        viewModel = scope.get()
    }

    @Test
    fun testGetUsersAndCheckResultKoin() = runBlocking {
        viewModel.getUserData()

        // Wait for first non-Loading result
        val result = viewModel.users.first { it !is NetworkResult.Loading }

        when (result) {
            is NetworkResult.Success -> {
                Log.d("Test Result", "User count: ${result.data.size}")
                Assert.assertTrue(result.data.isNotEmpty())
                Assert.assertEquals(
                    10,
                    result.data.size
                )

                Thread.sleep(3000)

                UserApiIntegrationTest().testUI(activityRule, result.data)
            }

            is NetworkResult.Error -> {
                Assert.fail("API call failed: ${result.message}")
            }

            else -> {
                Assert.fail("Unexpected result type")
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin() // clean up Koin context after each test
    }
}