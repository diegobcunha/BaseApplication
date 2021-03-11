package com.diegocunha.baseapplication

import android.app.Application
import com.diegocunha.baseapplication.core.api.rest.apiModule
import com.diegocunha.baseapplication.core.api.rest.restModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(apiModule, restModule)
        }
    }
}