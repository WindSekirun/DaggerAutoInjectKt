package com.github.windsekirun.daggerautoinjectkt.compiler.base

import com.github.windsekirun.daggerautoinjectkt.Constants
import com.github.windsekirun.daggerautoinjectkt.holder.AnnotationHolder
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.ProcessingEnvironment

/**
 * DaggerAutoInjectKt
 * Class: AnnotationCompiler
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */

open class AnnotationCompiler {

    open val className: String = ""

    fun construct(env: ProcessingEnvironment, set: MutableSet<AnnotationHolder>) {
        val funList = set.map {
            FunSpec.builder("contribute_${it.className}")
                .addAnnotation(injectorAnnotation)
                .addModifiers(KModifier.ABSTRACT)
                .returns(it.classNameComplete)
                .build()
        }.toList()

        val newType = TypeSpec.classBuilder(className)
            .addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)
            .addAnnotation(moduleAnnotation)
            .addFunctions(funList)
            .build()

        val fileSpec = FileSpec.builder(packageName, className)
            .addType(newType)
            .build()

        val generatedDir = env.options[Constants.KAPT_KOTLIN_GENERATED_OPTION_NAME]
        fileSpec.writeTo(File(generatedDir, "$className.kt"))
    }

    companion object {
        const val packageName = "com.github.windsekirun.daggerautoinjectkt"
        val moduleAnnotation = ClassName.bestGuess("dagger.Module")
        val injectorAnnotation = ClassName.bestGuess("dagger.android.ContributesAndroidInjector")
    }
}