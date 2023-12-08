package com.edusoa.android.kotlin

import android.util.Log
import com.edusoa.android.kotlin.BuildUtil.DEFAULT_PACKAGE_NAME

/**
 * Description:
 * @author Junerver
 * date: 2023/11/16-8:36
 * Email: junerver@gmail.com
 * Version: v1.0
 */

object BuildUtil {
    var DEFAULT_PACKAGE_NAME = ""

    fun initPackageName(packageName: String) {
        DEFAULT_PACKAGE_NAME = packageName
    }
}

/**
 * 通过反射获取 BuildConfig 中的指定字段
 * @param fieldName
 * @return
 */
inline fun <reified T> getBuildConfigValue(
    fieldName: String,
    def: T,
    packageName: String = DEFAULT_PACKAGE_NAME,
): T {
    require(packageName.isNotEmpty()) { "packageName cannot be empty,need to call 'initPackageName(string)' first" }
    return runCatching {
        val clazz = Class.forName("$packageName.BuildConfig")
        val field = clazz.getField(fieldName)
        field[null] as T
    }.onFailure { Log.e("BuildUtil", "getBuildConfigValue: ", it) }
        .getOrElse { def }
}
