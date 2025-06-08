package com.samir.koinrestapiexample.di

import com.samir.koinrestapiexample.domain.UserUseCase
import com.samir.koinrestapiexample.data.repository.UserRepositoryImpl
import com.samir.koinrestapiexample.domain.UserRepository
import com.samir.koinrestapiexample.presentation.viewmodels.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<UserRepository> {
        UserRepositoryImpl()
    }

    factory {
        UserUseCase()
    }

    viewModel { UserViewModel(get()) }
}