package com.github.windsekirun.daggerautoinjectkt.di

import androidx.lifecycle.ViewModelProvider
import com.github.windsekirun.daggerautoinjectkt.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * DaggerAutoInjectKt
 * Class: BindsModule
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */

@Module
abstract class BindsModule {
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}