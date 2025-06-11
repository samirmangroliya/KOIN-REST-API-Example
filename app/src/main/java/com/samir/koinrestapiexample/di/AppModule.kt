package com.samir.koinrestapiexample.di

import com.samir.koinrestapiexample.data.repository.UserRepositoryImpl
import com.samir.koinrestapiexample.domain.UserRepository
import com.samir.koinrestapiexample.domain.UserUseCase
import com.samir.koinrestapiexample.presentation.activity.MainActivity
import com.samir.koinrestapiexample.presentation.viewmodels.UserViewModel
import org.koin.dsl.module

val appModule = module {
    scope<MainActivity> {
        scoped<UserRepository> { UserRepositoryImpl(get()) }
        scoped { UserUseCase(get()) }
        scoped { UserViewModel(get()) }
    }
}