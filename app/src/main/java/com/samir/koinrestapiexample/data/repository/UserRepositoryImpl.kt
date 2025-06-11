package com.samir.koinrestapiexample.data.repository

import com.samir.koinrestapiexample.data.apiservice.UserApiService
import com.samir.koinrestapiexample.data.model.User
import com.samir.koinrestapiexample.domain.UserRepository

class UserRepositoryImpl(private val userApiService: UserApiService) : UserRepository {

    override suspend fun getUserList(): List<User> {
        return userApiService.getUsers()
    }
}
