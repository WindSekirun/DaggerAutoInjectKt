package com.github.windsekirun.daggerautoinjectkt.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.github.windsekirun.daggerautoinjectkt.InjectService
import dagger.android.AndroidInjection

@InjectService
class MainService : Service() {

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
