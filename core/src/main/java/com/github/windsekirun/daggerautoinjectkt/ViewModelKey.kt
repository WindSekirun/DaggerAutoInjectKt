package com.github.windsekirun.daggerautoinjectkt

import dagger.MapKey

/**
 * DaggerAutoInjectKt
 * Class: ViewModelKey
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: String)