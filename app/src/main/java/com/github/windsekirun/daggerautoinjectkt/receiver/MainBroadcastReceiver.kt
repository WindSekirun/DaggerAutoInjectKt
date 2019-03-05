package com.github.windsekirun.daggerautoinjectkt.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.windsekirun.daggerautoinjectkt.InjectBroadcastReceiver
import dagger.android.AndroidInjection

@InjectBroadcastReceiver
class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)
    }
}
