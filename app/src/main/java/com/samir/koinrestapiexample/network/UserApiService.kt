package com.samir.koinrestapiexample.network

import com.samir.koinrestapiexample.model.User
import retrofit2.http.GET

interface UserApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
}