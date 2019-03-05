package com.github.windsekirun.daggerautoinjectkt.compiler

import com.github.windsekirun.daggerautoinjectkt.compiler.base.AnnotationCompiler

/**
 * DaggerAutoInjectKt
 * Class: ActivityCompiler
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */

class ActivityCompiler : AnnotationCompiler() {
    override val className: String
        get() = "ActivityModule"
}