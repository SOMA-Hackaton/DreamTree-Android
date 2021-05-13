package com.soma_quokka.dreamtree

import android.app.Application
import android.content.Context
import com.soma_quokka.dreamtree.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: BaseApplication
        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            androidFileProperties()
            modules(listOf(viewModelModule))
        }
    }
}