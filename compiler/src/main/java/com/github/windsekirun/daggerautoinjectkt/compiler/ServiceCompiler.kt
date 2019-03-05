package com.github.windsekirun.daggerautoinjectkt.compiler

import com.github.windsekirun.daggerautoinjectkt.compiler.base.AnnotationCompiler

/**
 * DaggerAutoInjectKt
 * Class: ServiceCompiler
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */

class ServiceCompiler : AnnotationCompiler() {
    override val className: String
        get() = "ServiceModule"
}