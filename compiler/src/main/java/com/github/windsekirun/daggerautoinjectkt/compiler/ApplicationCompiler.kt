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
        val typeBuilder = TypeSpec.classBuilder(className)
            .addModifiers(KModifier.PUBLIC)
            .addProperty(PropertySpec.builder("TAG", stringClass, KModifier.FINAL, KModifier.PUBLIC).build())
            .addFunction(FunSpec.constructorBuilder().addModifiers(KModifier.PRIVATE).build())

        val component =
            holder.componentClass?.asClassName() ?: throw IllegalStateException("Cannot find component class")

        val initSpec = FunSpec.builder("init")
            .addModifiers(KModifier.PUBLIC)
            .addAnnotation(JvmStatic::class)
            .addParameter("application", holder.classNameComplete)
            .addParameter("component", component)
            .addStatement("\$L.inject(\$L)", "component", "application")
            .addStatement(
                "application.registerActivityLifecycleCallbacks(new \$T.ActivityLifecycleCallbacks() {\n" +
                        "            @Override\n" +
                        "            public void onActivityCreated(\$T activity, \$T savedInstanceState) {\n" +
                        "                " + "handleActivity" + "(activity);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onActivityStarted(Activity activity) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onActivityResumed(Activity activity) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onActivityPaused(Activity activity) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onActivityStopped(Activity activity) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onActivityDestroyed(Activity activity) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });",

                application,
                activity,
                bundle
            )

        typeBuilder.addFunction(initSpec.build())

        val handleSpec = FunSpec.builder("handleActivity")
            .addModifiers(KModifier.PROTECTED)
            .addAnnotation(JvmStatic::class.java)
            .addParameter("activity", activity)
            .addCode(
                "try {\n" +
                        "            \$T.inject(activity);\n" +
                        "        } catch (Exception e){\n" +
                        "            \$T.d(TAG, activity.getClass().toString()+\" non injected\");\n" +
                        "        }\n" +
                        "        if (activity instanceof \$T) {\n" +
                        "            final \$T supportFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();\n" +
                        "            supportFragmentManager.registerFragmentLifecycleCallbacks(\n" +
                        "                    new FragmentManager.FragmentLifecycleCallbacks() {\n" +
                        "                        @Override\n" +
                        "                        public void onFragmentCreated(FragmentManager fm, \$T f, \$T savedInstanceState) {\n" +
                        "                            try {\n" +
                        "                                \$T.inject(f);\n" +
                        "                            } catch (Exception e){\n" +
                        "                                Log.d(TAG, f.getClass().toString()+\" non injected\");\n" +
                        "                            }\n" +
                        "                        }\n" +
                        "                    }, true);\n" +
                        "        }",

                androidInjection,
                log,
                fragmentActivity,
                fragmentManager,
                fragment,
                bundle,
                supportInjection
            )

        typeBuilder.addFunction(handleSpec.build())

        val fileSpec = FileSpec.builder(packageName, className)
            .addType(typeBuilder.build())
            .build()

        val generatedDir = env.options[AutoInjectCompiler.KAPT_KOTLIN_GENERATED_OPTION_NAME]
        fileSpec.writeTo(File(generatedDir, "$className.kt"))
    }

    companion object {
        val stringClass = String::class.java.asClassName()
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