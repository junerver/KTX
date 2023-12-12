@file: JvmName("-colorx")

package com.edusoa.android.kotlin

import android.graphics.Color

/**
 * Description: 16进制颜色的值包装类，之所以使用 value class 是为了避免 String 的扩展函数爆炸。本质上他们还是 String 的扩展函数
 * @author Junerver
 * date: 2023/12/12-9:13
 * Email: junerver@gmail.com
 * Version: v1.0
 */
@JvmInline
value class ColorX(private val color: String) {
    init {
        require(color.startsWith("#") && color.length == 7) { "Not a legal hexadecimal color string" }
    }

    override fun toString(): String {
        return color
    }

    fun toRGB(): Triple<Int, Int, Int> {
        val color: Int = color.replace("#", "").toInt(16)
        val red = color and 0xff0000 shr 16
        val green = color and 0x00ff00 shr 8
        val blue = color and 0x0000ff
        return Triple(red, green, blue)
    }

    fun toColor(): Int {
        return Color.parseColor(color)
    }
}

fun Triple<Int, Int, Int>.toColorX(): ColorX {
    require(this.toList().all { it in 0..255 }) { "Not a legal RGB color Triple" }
    return ColorX("#${this.toList().joinToString("") { String.format("%02X", it); }}")
}