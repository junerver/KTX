package com.edusoa.android.kotlin

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayDeque
import java.util.Deque
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@ExperimentalContracts
fun String?.isNotNullOrEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrEmpty != null)
    }
    return this != null && !this.trim().equals("null", true) && this.trim().isNotEmpty()
}

fun EditText.trimString() = this.text.toString().trim()

@OptIn(ExperimentalEncodingApi::class)
fun String.decodeBase64(): String = String(Base64.decode(this))

fun Context.getDrawableRes(@DrawableRes id: Int): Drawable {
    return AppCompatResources.getDrawable(this, id)!!
}

fun Context.getColorRes(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.getStringRes(@StringRes id: Int): String {
    return this.resources.getString(id)
}

fun Context.getDimenRes(@DimenRes id: Int): Int {
    return this.resources.getDimensionPixelSize(id)
}

fun json(build: JsonObjectBuilder.() -> Unit): JSONObject {
    return JsonObjectBuilder().json(build)
}

class JsonObjectBuilder {
    private val deque: Deque<JSONObject> = ArrayDeque()

    fun json(build: JsonObjectBuilder.() -> Unit): JSONObject {
        deque.push(JSONObject())
        this.build()
        return deque.pop()
    }

    infix fun <T> String.to(value: T) {
        // wrap value into json block if it is a lambda
        val wrapped = when (value) {
            is Function0<*> -> json { value.invoke() }
            //只有array or list 会被转换成jsonArray
            is List<*> -> JSONArray().apply { value.forEach { put(it) } }
            is Array<*> -> JSONArray().apply { value.forEach { put(it) } }
            else -> value
        }

        deque.peek().put(this, wrapped)
    }
}

/**
 * 一个防抖的点击事件
 */
inline fun View.setSingleClickListener(wait: Int = 500, crossinline block: (View) -> Unit) {
    var lastTime = 0L
    setOnClickListener {
        try {
            if (System.currentTimeMillis() - lastTime < wait) return@setOnClickListener
            block(it)
        } finally {
            lastTime = System.currentTimeMillis()
        }
    }
}

//region 可见性扩展函数
fun View.gone() {
    this.visibility = View.GONE
}

fun gones(vararg views: View?) {
    views.forEach {
        it?.gone()
    }
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun invisibles(vararg views: View) {
    views.forEach {
        it.invisible()
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun visibles(vararg views: View) {
    views.forEach {
        it.visible()
    }
}