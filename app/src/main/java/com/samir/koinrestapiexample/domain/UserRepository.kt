package com.samir.koinrestapiexample.domain

import com.samir.koinrestapiexample.data.model.User

interface UserRepository {
   suspend fun getUserList(): List<User>
}