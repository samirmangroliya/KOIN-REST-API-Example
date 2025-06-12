package com.samir.koinrestapiexample

import com.samir.koinrestapiexample.data.model.User
import com.samir.koinrestapiexample.domain.UserRepository
import com.samir.koinrestapiexample.domain.UserUseCase
import org.junit.Before
import org.junit.Test


class FilterUnitTest {
    private lateinit var userUseCase: UserUseCase

    @Before
    fun setup(){
        userUseCase = UserUseCase(FakeUserRepository())
    }

    @Test
    fun checkFilterList() {
        val list = (1..5).map {
            User(
                if(it%2!=0) "user$it@test.biz" else "nouser@gmail.com" ,
                id = it,
                name = "",
                phone = "",
                username = "",
                website = ""
            ) }.toList()

        val result = userUseCase.getFilterDataByEmail("biz", list)
        println("total:: ${result.size}")

        assert(result.size == 3)
    }

    class FakeUserRepository : UserRepository {
        override suspend fun getUserList(): List<User> {
            return emptyList()
        }
    }
}