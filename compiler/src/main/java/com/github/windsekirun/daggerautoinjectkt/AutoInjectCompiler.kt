package com.github.windsekirun.daggerautoinjectkt

import com.github.windsekirun.daggerautoinjectkt.compiler.*
import com.github.windsekirun.daggerautoinjectkt.compiler.data.InvestTarget
import com.google.auto.service.AutoService
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * DaggerAutoInjectKt
 * Class: AutoInjectCompiler
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(AutoInjectCompiler.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class AutoInjectCompiler : AbstractProcessor() {
    private val investTarget = InvestTarget()

    override fun process(annotations: MutableSet<out TypeElement>?, env: RoundEnvironment?): Boolean {
        if (env != null && !env.errorRaised() && !env.processingOver()) {
            investAnnotations(env)
            if (investTarget.isInitialized()) {
                constructClass()
            }
        }
        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            InjectActivity::class.javaName(), InjectApplication::class.javaName(),
            InjectBroadcastReceiver::class.javaName(), InjectContentProvider::class.javaName(),
            InjectFragment::class.javaName(), InjectService::class.javaName(), InjectViewModel::class.javaName()
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    private fun investAnnotations(env: RoundEnvironment?) {
        if (env != null) investTarget.invest(env)
    }

    private fun constructClass() {
        ActivityCompiler().construct(processingEnv, investTarget.activityHolder)
        ServiceCompiler().construct(processingEnv, investTarget.serviceHolder)
        FragmentCompiler().construct(processingEnv, investTarget.fragmentHolder)
        BroadcastReceiverCompiler().construct(processingEnv, investTarget.broadcastHolder)
        ContentProviderCompiler().construct(processingEnv, investTarget.contentHolder)
        ViewModelCompiler().construct(processingEnv, investTarget.viewModelHolder)
        ApplicationCompiler().construct(processingEnv, investTarget.applicationHolder)

        investTarget.clear()
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}