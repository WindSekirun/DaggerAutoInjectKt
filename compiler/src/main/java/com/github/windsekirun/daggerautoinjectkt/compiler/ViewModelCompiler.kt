package com.github.windsekirun.daggerautoinjectkt.compiler

import com.github.windsekirun.daggerautoinjectkt.AutoInjectCompiler
import com.github.windsekirun.daggerautoinjectkt.ViewModelKey
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

open class ViewModelCompiler {

    open val className: String = "ViewModelModule"

    fun construct(env: ProcessingEnvironment, set: MutableSet<AnnotationHolder>) {
        val funList = set.map {
            val typeName = it.classNameComplete
            val parameterName = it.className[0].toLowerCase() + it.className.substring(1)
            val keyAnnotation = AnnotationSpec.builder(ViewModelKey::class)
                .addMember("value = %L", "${it.className}::class")
                .build()

            FunSpec.builder("bind_${it.className}")
                .addParameter(parameterName, typeName)
                .addAnnotation(bindAnnotation)
                .addAnnotation(intoMapAnnotation)
                .addAnnotation(keyAnnotation)
                .addModifiers(KModifier.ABSTRACT)
                .returns(viewModel)
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

        val generatedDir = env.options[AutoInjectCompiler.KAPT_KOTLIN_GENERATED_OPTION_NAME]
        fileSpec.writeTo(File(generatedDir, "$className.kt"))
    }

    companion object {
        const val packageName = "com.github.windsekirun.daggerautoinjectkt"
        val moduleAnnotation = ClassName.bestGuess("dagger.Module")
        val bindAnnotation = ClassName.bestGuess("dagger.Binds")
        val intoMapAnnotation = ClassName.bestGuess("dagger.multibindings.IntoMap")
        val viewModel = ClassName.bestGuess("androidx.lifecycle.ViewModel")
    }
}