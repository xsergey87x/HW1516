package com.example.homework1516

import android.app.Application
import com.example.homework1516.di.networkModule
import com.example.homework1516.di.repoModule
import com.example.homework1516.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    uiModule,
                    repoModule,
                    networkModule
                )
            )
        }
    }

}