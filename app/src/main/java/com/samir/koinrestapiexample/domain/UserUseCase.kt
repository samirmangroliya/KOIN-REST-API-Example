package com.samir.koinrestapiexample.domain

import com.samir.koinrestapiexample.data.model.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserUseCase : KoinComponent {

    private val userRepositoryImpl: UserRepository by inject()

     suspend fun getUserList(): List<User> {
        return userRepositoryImpl.getUserList()
    }
}