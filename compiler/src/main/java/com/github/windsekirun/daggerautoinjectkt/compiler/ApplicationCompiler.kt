package com.github.windsekirun.daggerautoinjectkt.compiler

import com.github.windsekirun.daggerautoinjectkt.AutoInjectCompiler
import com.github.windsekirun.daggerautoinjectkt.asClassName
import com.github.windsekirun.daggerautoinjectkt.holder.ApplicationHolder
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

open class ApplicationCompiler {

    open val className: String = "DaggerAutoInject"

    fun construct(env: ProcessingEnvironment, holder: ApplicationHolder) {
        val typeBuilder = TypeSpec.objectBuilder(className)
            .addModifiers(KModifier.PUBLIC)
            .addProperty(
                PropertySpec.builder("TAG", stringClass, KModifier.FINAL, KModifier.PUBLIC)
                    .initializer("\"$className\"")
                    .build()
            )

        val component =
            holder.componentClass?.asClassName() ?: throw IllegalStateException("Cannot find component class")

        val initSpec = FunSpec.builder("init")
            .addModifiers(KModifier.PUBLIC)
            .addAnnotation(JvmStatic::class)
            .addParameter("application", holder.classNameComplete)
            .addParameter("component", component)
            .addStatement("%N.inject(%N)", "component", "application")
            .beginControlFlow(
                " application.registerActivityLifecycleCallbacks(object : %T.ActivityLifecycleCallbacks",
                application
            )
            .beginControlFlow("override fun onActivityPaused(activity: %T?)", activity)
            .addStatement("if (activity != null) handleActivity(activity)")
            .endControlFlow()
            .addStatement("override fun onActivityResumed(p0: Activity?) {}")
            .addStatement("override fun onActivityStarted(p0: Activity?) {}")
            .addStatement("override fun onActivityDestroyed(p0: Activity?) {}")
            .addStatement("override fun onActivitySaveInstanceState(p0: Activity?, p1: %T?) {}", bundle)
            .addStatement("override fun onActivityStopped(p0: Activity?) {}")
            .addStatement("override fun onActivityCreated(p0: Activity?, p1: Bundle?) {}")
            .addStatement("})")

        typeBuilder.addFunction(initSpec.build())

        val handleSpec = FunSpec.builder("handleActivity")
            .addModifiers(KModifier.PRIVATE)
            .addAnnotation(JvmStatic::class.java)
            .addParameter("activity", activity)
            .beginControlFlow("try")
            .addStatement("%T.inject(activity)", androidInjection)
            .endControlFlow()
            .beginControlFlow("catch (e: Exception)")
            .addStatement("%T.d(TAG, \"\${activity.javaClass.toString()} non injected\")", log)
            .endControlFlow()
            .beginControlFlow("if (activity is %T)", fragmentActivity)
            .addStatement("val manager = activity.supportFragmentManager")
            .beginControlFlow(
                "manager.registerFragmentLifecycleCallbacks(object : %T.FragmentLifecycleCallbacks()",
                fragmentManager
            )
            .beginControlFlow(
                "override fun onFragmentCreated(fm: FragmentManager, f: %T, savedInstanceState: Bundle?)",
                fragment
            )
            .beginControlFlow("try")
            .addStatement("%T.inject(f)", supportInjection)
            .endControlFlow()
            .beginControlFlow("catch (e: Exception)")
            .addStatement("%T.d(TAG, \"\${f.javaClass.toString()} non injected\")", log)
            .endControlFlow()
            .endControlFlow()
            .addStatement("}, true)")
            .endControlFlow()

        typeBuilder.addFunction(handleSpec.build())

        val fileSpec = FileSpec.builder(packageName, className)
            .addType(typeBuilder.build())
            .build()

        val generatedDir = env.options[AutoInjectCompiler.KAPT_KOTLIN_GENERATED_OPTION_NAME]
        fileSpec.writeTo(File(generatedDir, "$className.kt"))
    }

    companion object {
        val stringClass = String::class.asClassName()
        val application = ClassName.bestGuess("android.app.Application")
        val activity = ClassName.bestGuess("android.app.Activity")
        val bundle = ClassName.bestGuess("android.os.Bundle")
        val log = ClassName.bestGuess("android.util.Log")
        val fragmentActivity = ClassName.bestGuess("androidx.fragment.app.FragmentActivity")
        val fragmentManager = ClassName.bestGuess("androidx.fragment.app.FragmentManager")
        val androidInjection = ClassName.bestGuess("dagger.android.AndroidInjection")
        val fragment = ClassName.bestGuess("androidx.fragment.app.Fragment")
        val supportInjection = ClassName.bestGuess("dagger.android.support.AndroidSupportInjection")
        const val packageName = "com.github.windsekirun.daggerautoinjectkt"
    }
}