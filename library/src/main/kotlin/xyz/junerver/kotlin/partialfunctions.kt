@file: JvmName("-partialfunctions")

package xyz.junerver.kotlin

/*
 * Copyright 2013 - 2016 Mario Arias
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 源码来自：https://github.com/MarioAriasC/funKTionale/blob/master/funktionale-utils/src/main/kotlin/org/funktionale/utils/partialfunctions.kt
 * 虽然他称之为偏函数，实际不是，看起来更像是一个约定，可以用于实现责任链，他的实现思想参考了偏函数
 */
class PartialFunction<in P1, out R>(
    /*用于判断入参[p1]是否会被偏函数的函数体执行*/
    private val definedAt: (P1) -> Boolean,
    /*满足条件时，真实执行的函数体[f]*/
    private val f: (P1) -> R
) : (P1) -> R {
    /*偏函数实际是一个类，通过从写 [invoke] 函数，伪装成方法*/
    override fun invoke(p1: P1): R {
        if (definedAt(p1)) {
            return f(p1)
        } else {
            throw IllegalArgumentException("Value: ($p1) isn't supported by this function")
        }
    }
    /*偏函数本身对外暴露，判断函数*/
    fun isDefinedAt(p1: P1) = definedAt(p1)
}

/**
 * 给当前的偏函数一个入参[p1]，当 [p1] 满足函数 [isDefinedAt]，执行偏函数的函数体
 * 不满足时使用 [default] 作为返回值
 */
fun <P1, R> PartialFunction<P1, R>.invokeOrElse(p1: P1, default: R): R {
    return if (this.isDefinedAt(p1)) {
        this(p1)
    } else {
        default
    }
}

/**
 * 用于连接多个偏函数，形成函数链条
 */
infix fun <P1, R> PartialFunction<P1, R>.orElse(that: PartialFunction<P1, R>): PartialFunction<P1, R> {
    return PartialFunction({ this.isDefinedAt(it) || that.isDefinedAt(it) }) {
        when {
            this.isDefinedAt(it) -> this(it)
            that.isDefinedAt(it) -> that(it)
            else -> throw IllegalArgumentException("function not definet for parameter ($it)")
        }
    }
}

/**
 * 将一个普通函数转换成偏函数(类)
 */
fun <P1, R> ((P1) -> R).toPartialFunction(definedAt: (P1) -> Boolean): PartialFunction<P1, R> {
    return PartialFunction(definedAt, this)
}