package com.samir.koinrestapiexample.data.repository

import com.samir.koinrestapiexample.data.apiservice.UserApiService
import com.samir.koinrestapiexample.data.model.User
import com.samir.koinrestapiexample.domain.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepositoryImpl : UserRepository, KoinComponent {
    private val userApiService: UserApiService by inject()

    override suspend fun getUserList(): List<User> {
        return userApiService.getUsers()
    }
}