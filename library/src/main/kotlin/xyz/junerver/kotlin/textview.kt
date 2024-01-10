package xyz.junerver.kotlin

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Description:
 * @author Junerver
 * date: 2023/6/7-11:14
 * Email: junerver@gmail.com
 * Version: v1.0
 */

fun TextView.trimString() = this.text.toString().trim()

//region TextView drawable 相关扩展
fun TextView.drawableLeft(@DrawableRes id: Int) {
    val d = context.getDrawableRes(id)
    d.setBounds(0, 0, d.minimumWidth, d.minimumHeight)
    this.setCompoundDrawables(d, null, null, null)
}

fun TextView.drawableBottom(@DrawableRes id: Int) {
    val d = context.getDrawableRes(id)
    d.setBounds(0, 0, d.minimumWidth, d.minimumHeight)
    this.setCompoundDrawables(null, null, null, d)
}

fun TextView.drawableRight(@DrawableRes id: Int) {
    val d = context.getDrawableRes(id)
    d.setBounds(0, 0, d.minimumWidth, d.minimumHeight)
    this.setCompoundDrawables(null, null, d, null)
}

fun TextView.drawableTop(@DrawableRes id: Int) {
    val d = context.getDrawableRes(id)
    d.setBounds(0, 0, d.minimumWidth, d.minimumHeight)
    this.setCompoundDrawables(null, d, null, null)
}
//endregion

/**
 * 修改TextView文字颜色
 */
fun TextView.color(@ColorRes id: Int) {
    val ctx = this.context
    this.setTextColor(ContextCompat.getColor(ctx, id))
}

@Throws(IllegalArgumentException::class)
fun TextView.color(colorHex: String) {
    this.setTextColor(colorHex.toColor())
}


//region EditText 扩展

val EditText.trimTextStr: String
    get() = this.text.toString().trim()

fun EditText.isEmpty(): Boolean = this.text.toString().trim().isEmpty()

fun EditText.isNotEmpty(): Boolean = this.text.toString().trim().isNotEmpty()

/* 清除输入框数据 */
fun EditText.clear() {
    setText("")
}

/**
 * EditText设置只能输入数字和小数点，小数点只能1个且小数点后最多只能2位
 */
fun EditText.setOnlyDecimal() {
    this.inputType = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
    this.addTextChangedListener(object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //这部分是处理如果输入框内小数点后有俩位，那么舍弃最后一位赋值，光标移动到最后
            if (s.toString().contains(".")) {
                if (s.length - 1 - s.toString().indexOf(".") > 2) {
                    setText(s.toString().subSequence(0, s.toString().indexOf(".") + 3))
                    setSelection(s.toString().subSequence(0, s.toString().indexOf(".") + 3).length)
                }
            }

            if (s.toString().trim().substring(0) == ".") {
                setText("0$s")
                setSelection(2)
            }

            if (s.toString().startsWith("0") && s.toString().trim().length > 1) {
                if (s.toString().substring(1, 2) != ".") {
                    setText(s.subSequence(0, 1))
                    setSelection(1)
                    return
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable) {
        }
    })
}
//endregion

