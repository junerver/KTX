package com.edusoa.android.kotlin

import java.lang.reflect.Method

/**
 * Description:
 * @author Junerver
 * date: 2023/11/16-8:36
 * Email: junerver@gmail.com
 * Version: v1.0
 */

/**
 * 通过反射获取 BuildConfig 中的指定字段
 * @param fieldName
 * @return
 */
inline fun <reified T> getBuildConfigValue(packageName: String, fieldName: String, def: T): T =
    runCatching {
        val clazz = Class.forName("$packageName.BuildConfig")
        val field = clazz.getField(fieldName)
        field[null] as T
    }.getOrElse { def }