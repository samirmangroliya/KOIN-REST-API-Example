package com.samir.koinrestapiexample.data.apiservice

import com.samir.koinrestapiexample.data.model.User
import retrofit2.http.GET

interface UserApiService {
   @GET("users")
   suspend fun getUsers(): List<User>
}