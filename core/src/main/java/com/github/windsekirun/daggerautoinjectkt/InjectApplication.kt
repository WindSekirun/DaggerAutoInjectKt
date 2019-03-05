package com.github.windsekirun.daggerautoinjectkt

import kotlin.reflect.KClass

/**
 * DaggerAutoInjectKt
 * Class: InjectApplication
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class InjectApplication(val component: KClass<*>)