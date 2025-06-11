package com.samir.koinrestapiexample.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.AndroidScopeComponent
import org.koin.core.qualifier.named

open class BaseActivity: AppCompatActivity(), AndroidScopeComponent {
    override val scope by lazy {
        getKoin().createScope("scopeIdForActivity", named("activityScope"))
    }
}