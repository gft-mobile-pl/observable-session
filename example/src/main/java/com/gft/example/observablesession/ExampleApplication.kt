package com.gft.example.observablesession

import android.app.Application
import com.gft.example.observablesession.di.domainModule
import com.gft.example.observablesession.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                domainModule,
                uiModule
            )
        }
    }
}