package com.samir.koinrestapiexample.domain

import com.samir.koinrestapiexample.model.User
import com.samir.koinrestapiexample.network.UserApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserUseCase: KoinComponent {
    val userApiService: UserApiService by inject()

    suspend fun getUserList(): List<User> {
        return userApiService.getUsers()
    }
}