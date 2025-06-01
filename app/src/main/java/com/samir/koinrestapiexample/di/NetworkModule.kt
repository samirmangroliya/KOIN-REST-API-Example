package com.samir.koinrestapiexample.di

import com.samir.koinrestapiexample.BuildConfig
import com.samir.koinrestapiexample.network.UserApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        provideOkHttpClient()
        provideGsonConverterFactory()
        provideRetrofit(get(), get())
        provideUserApiService(get())
    }
}

fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .callTimeout(1, TimeUnit.MINUTES)
        .apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(httpLoggingInterceptor)
            }
        }
        .build()

fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

fun  provideUserApiService(retrofit: Retrofit) : UserApiService = retrofit.create(UserApiService::class.java)