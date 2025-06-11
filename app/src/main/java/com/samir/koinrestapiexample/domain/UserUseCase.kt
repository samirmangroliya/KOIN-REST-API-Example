package com.samir.koinrestapiexample.domain

import com.samir.koinrestapiexample.data.model.User

class UserUseCase(private val userRepository: UserRepository) {
    suspend fun getUserList(): List<User> {
        return userRepository.getUserList()
    }

    fun getFilterDataByEmail(filter: String, list: List<User>):List<User> {
        return list.filter { it.email?.endsWith(filter, ignoreCase = true) == true }
    }
}