package com.github.windsekirun.daggerautoinjectkt.compiler

import com.github.windsekirun.daggerautoinjectkt.compiler.base.AnnotationCompiler

/**
 * DaggerAutoInjectKt
 * Class: ContentProviderCompiler
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */

class ContentProviderCompiler : AnnotationCompiler() {
    override val className: String
        get() = "ContentProviderModule"
}