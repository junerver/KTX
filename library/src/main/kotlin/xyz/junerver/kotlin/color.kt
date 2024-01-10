@file: JvmName("-colorx")

package xyz.junerver.kotlin

import android.graphics.Color
import android.widget.TextView
import androidx.core.graphics.toColorInt

/**
 * Description: 16进制颜色的值包装类，之所以使用 value class 是为了避免 String 的扩展函数爆炸。本质上他们还是 String 的扩展函数
 * 使用方法：
 * ```
 * // ColorX内部带校验，会检查是否是合法的argb/rgb颜色数据
 * val color  = ColorX("#00ff00ff").toColor()
 * val color2 = "#8080ff00".toColor()
 * ```
 * @author Junerver
 * date: 2023/12/12-9:13
 * Email: junerver@gmail.com
 * Version: v1.0
 * @update: 更新兼容透明度的argb颜色。
 *
 */
@JvmInline
value class ColorX(private val _color: String) {
    init {
        require(_color.startsWith("#") &&
                (_color.length == 7 || _color.length == 9) &&
                toARGB().toList().all { it in 0..255 }
        ) { "Not a legal hexadecimal color string" }
    }

    override fun toString(): String {
        return _color
    }

    fun isArgb(): Boolean = _color.length == 9
    fun isRgb(): Boolean = _color.length == 7

    fun toARGB(): Quadruple<Int, Int, Int, Int> {
        val color = _color.substring(1).toLong(16)
        val alpha = if (isRgb()) 255 else (color and 0xff000000 shr 24).toInt()
        val red = (color and 0x00ff0000 shr 16).toInt()
        val green = (color and 0x0000ff00 shr 8).toInt()
        val blue = (color and 0x000000ff).toInt()
        return Quadruple(alpha, red, green, blue)
    }

    fun toRGB(): Triple<Int, Int, Int> {
        require(isRgb()) { "Please confirm whether it is a legal hexadecimal RGB color" }
        val color: Int = _color.substring(1).toInt(16)
        val red = color and 0xff0000 shr 16
        val green = color and 0x00ff00 shr 8
        val blue = color and 0x0000ff
        return Triple(red, green, blue)
    }

    fun toColor(): Int {
        return Color.parseColor(_color)
    }
}

fun Quadruple<Int, Int, Int, Int>.toColorX(): ColorX {
    require(this.toList().all { it in 0..255 }) { "Not a legal ARGB color Quadruple" }
    return ColorX("#${this.toList().joinToString("") { String.format("%02X", it); }}")
}

fun Triple<Int, Int, Int>.toColorX(): ColorX {
    require(this.toList().all { it in 0..255 }) { "Not a legal RGB color Triple" }
    return ColorX("#${this.toList().joinToString("") { String.format("%02X", it); }}")
}

/**
 * 官方提供了类似的扩展函数：
 * @see [toColorInt]
 */
fun String.toColor(): Int = ColorX(this).toColor()

fun TextView.setTextColorX(colorx:String) = this.setTextColor(colorx.toColorInt())
fun TextView.setBackgroundColorX(colorx:String) = this.setBackgroundColor(colorx.toColorInt())