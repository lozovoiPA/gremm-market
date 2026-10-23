package com.implus.gremmarket

import android.app.Application
import com.implus.gremmarket.ui.deviceapps.AppsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class GremApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    val appsRepository by lazy { AppsRepository(applicationScope) }

    override fun onCreate() {
        super.onCreate()
    }
}
