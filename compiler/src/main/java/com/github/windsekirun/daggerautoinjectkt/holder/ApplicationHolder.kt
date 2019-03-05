package com.github.windsekirun.daggerautoinjectkt.holder

import com.squareup.kotlinpoet.ClassName
import javax.lang.model.element.Element
import javax.lang.model.type.TypeMirror

/**
 * DaggerAutoInjectKt
 * Class: AnnotationHolder
 * Created by Pyxis on 2019-03-05.
 *
 * Description:
 */
data class ApplicationHolder(val element: Element, val classNameComplete: ClassName, val className: String) {
    var componentClass: TypeMirror? = null
}