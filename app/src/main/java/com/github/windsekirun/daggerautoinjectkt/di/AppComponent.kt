package com.github.windsekirun.daggerautoinjectkt.di

import com.github.windsekirun.daggerautoinjectkt.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, ActivityModule::class, FragmentModule::class, ViewModelModule::class, ServiceModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MainApplication): Builder

        fun build(): AppComponent
    }

    fun inject(mainApp: MainApplication)

}