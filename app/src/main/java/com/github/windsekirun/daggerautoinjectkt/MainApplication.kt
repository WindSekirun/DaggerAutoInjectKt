package com.github.windsekirun.daggerautoinjectkt

import android.app.Activity
import android.app.Application
import android.app.Service
import com.github.windsekirun.daggerautoinjectkt.di.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject


@InjectApplication(component = AppComponent::class)
class MainApplication : Application(), HasActivityInjector, HasServiceInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var serviceDispatchingAndroidInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        DaggerAutoInject.init(this, appComponent)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service>? {
        return serviceDispatchingAndroidInjector
    }

    companion object {
        /**
         * @return [DaggerAppComponent] to inject
         */
        var appComponent: AppComponent? = null
            private set
    }
}
