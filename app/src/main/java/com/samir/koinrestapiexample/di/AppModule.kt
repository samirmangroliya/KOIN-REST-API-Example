package com.samir.koinrestapiexample.di

import com.samir.koinrestapiexample.domain.UserUseCase
import com.samir.koinrestapiexample.repository.UserRepository
import com.samir.koinrestapiexample.viewmodel.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory {
        UserRepository()
        UserUseCase()
    }
    viewModel { UserViewModel() }
}