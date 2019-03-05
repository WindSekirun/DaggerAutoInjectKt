package com.github.windsekirun.daggerautoinjectkt

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

/**
 * DaggerAutoInjectKt
 * Class: CompilerEx
 * Created by Pyxis on 2019-03-05.
 *
 * Description: internal function to reduce code-base
 */

internal fun KClass<*>.javaName() = this.java.name

internal fun InjectApplication.getComponent(): TypeMirror {
    return try {
        this.component as TypeMirror
    } catch (e: MirroredTypeException) {
        e.typeMirror
    }
}

internal fun TypeMirror.asClassName(): ClassName {
    val typeName = this.asTypeName() as ClassName
    val packageName = typeName.packageName
    val className = typeName.simpleName
    return ClassName.bestGuess("$packageName.$className")
}