package com.github.windsekirun.daggerautoinjectkt.compiler.data

import com.github.windsekirun.daggerautoinjectkt.*
import com.github.windsekirun.daggerautoinjectkt.holder.AnnotationHolder
import com.github.windsekirun.daggerautoinjectkt.holder.ApplicationHolder
import com.squareup.kotlinpoet.asClassName
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * DaggerAutoInjectKt
 * Class: InvestTarget
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */

class InvestTarget {
    val activityHolder = mutableSetOf<AnnotationHolder>()
    val fragmentHolder = mutableSetOf<AnnotationHolder>()
    val viewModelHolder = mutableSetOf<AnnotationHolder>()
    val serviceHolder = mutableSetOf<AnnotationHolder>()
    val broadcastHolder = mutableSetOf<AnnotationHolder>()
    val contentHolder = mutableSetOf<AnnotationHolder>()

    val applicationHolder: ApplicationHolder
        get() {
            return _applicationHolder ?: throw NullPointerException("Not initialized")
        }

    private var _applicationHolder: ApplicationHolder? = null

    fun invest(env: RoundEnvironment) {
        activityHolder.addAll(this.investInternal(InjectActivity::class, env))
        fragmentHolder.addAll(this.investInternal(InjectFragment::class, env))
        viewModelHolder.addAll(this.investInternal(InjectViewModel::class, env))
        serviceHolder.addAll(this.investInternal(InjectService::class, env))
        broadcastHolder.addAll(this.investInternal(InjectBroadcastReceiver::class, env))
        contentHolder.addAll(this.investInternal(InjectContentProvider::class, env))

        _applicationHolder = env.getElementsAnnotatedWith(InjectApplication::class.java)
            .map {
                ApplicationHolder(it, (it as TypeElement).asClassName(), it.simpleName.toString()).apply {
                    val component = it.getAnnotation(InjectApplication::class.java).getComponent()
                    componentClass = component
                }
            }.firstOrNull()
    }

    fun isInitialized() = _applicationHolder != null

    fun clear() {
        activityHolder.clear()
        fragmentHolder.clear()
        viewModelHolder.clear()
        serviceHolder.clear()
        broadcastHolder.clear()
        contentHolder.clear()
        _applicationHolder = null
    }

    private fun <T : Annotation> investInternal(cls: KClass<T>, env: RoundEnvironment): Set<AnnotationHolder> =
        env.getElementsAnnotatedWith(cls.java)
            .map { AnnotationHolder(it, (it as TypeElement).asClassName(), it.simpleName.toString()) }.toMutableSet()
}