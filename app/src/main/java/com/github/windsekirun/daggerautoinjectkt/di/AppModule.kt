package com.github.windsekirun.daggerautoinjectkt.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.github.windsekirun.daggerautoinjectkt.MainApplication
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(application: MainApplication): SharedPreferences {
        return application.getSharedPreferences("user", Context.MODE_PRIVATE)
    }

}
