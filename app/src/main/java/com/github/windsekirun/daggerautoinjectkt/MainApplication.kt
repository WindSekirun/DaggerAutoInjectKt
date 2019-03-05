package com.github.windsekirun.daggerautoinjectkt

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import com.github.windsekirun.daggerautoinjectkt.di.AppComponent
import dagger.android.*
import javax.inject.Inject


@InjectApplication(component = AppComponent::class)
class MainApplication : Application(), HasActivityInjector, HasServiceInjector, HasBroadcastReceiverInjector,
    HasContentProviderInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var serviceDispatchingAndroidInjector: DispatchingAndroidInjector<Service>
    @Inject
    lateinit var broadDispatchingAndroidInjector: DispatchingAndroidInjector<BroadcastReceiver>
    @Inject
    lateinit var contentDispatchingAndroidInjector: DispatchingAndroidInjector<ContentProvider>

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

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> {
        return broadDispatchingAndroidInjector
    }

    override fun contentProviderInjector(): AndroidInjector<ContentProvider> {
        return contentDispatchingAndroidInjector
    }

    companion object {
        /**
         * @return [DaggerAppComponent] to inject
         */
        var appComponent: AppComponent? = null
            private set
    }
}
