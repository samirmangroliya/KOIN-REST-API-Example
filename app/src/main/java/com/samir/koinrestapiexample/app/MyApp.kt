package com.samir.koinrestapiexample.app

import android.app.Application
import com.samir.koinrestapiexample.di.appModule
import com.samir.koinrestapiexample.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(listOf(networkModule, appModule))
        }

    }
}