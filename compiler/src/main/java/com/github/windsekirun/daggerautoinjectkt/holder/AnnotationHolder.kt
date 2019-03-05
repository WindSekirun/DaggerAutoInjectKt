package com.github.windsekirun.daggerautoinjectkt.holder

import com.squareup.kotlinpoet.ClassName
import javax.lang.model.element.Element

/**
 * DaggerAutoInjectKt
 * Class: AnnotationHolder
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */
data class AnnotationHolder(val element: Element, val classNameComplete: ClassName, val className: String)