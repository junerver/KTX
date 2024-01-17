package xyz.junerver.android.edusoaktx

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import xyz.junerver.android.edusoaktx.R
import xyz.junerver.kotlin.fullScreen
import xyz.junerver.kotlin.SingletonHolder
import xyz.junerver.kotlin.debounce
import xyz.junerver.kotlin.debounce2
import xyz.junerver.kotlin.getMetaData
import xyz.junerver.kotlin.runIf
import xyz.junerver.kotlin.setSingleClickListener
import xyz.junerver.kotlin.toColor
import xyz.junerver.kotlin.trimString
import xyz.junerver.kotlin.visibleOrInIf


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runIf {
            TestSingleton.getInstance(this@MainActivity).print()
        }
        val testBtn = findViewById<Button>(R.id.btn_test)
        val debounceBtn = findViewById<Button>(R.id.btn_debounce)
        val text = findViewById<TextView>(R.id.tv_content)
        val etText = findViewById<EditText>(R.id.et_edit)
        text.setTextColor("#078910".toColor())

        etText.trimString()
        var t = false

        testBtn.setSingleClickListener {
            fullScreen()
            text visibleOrInIf  t
            t = !t
        }
        val action = {
            Log.d(TAG, "onCreate: 我执行了")
            throw RuntimeException("test")
            Unit
        }
        val debounced = action.debounce(1000)
        val debounced2 = action.debounce2(1000)
        debounceBtn.setOnClickListener {
            Log.d(TAG, "onCreate: 按钮点击了")
            debounced2()
        }





        runCatching {
            text.apply {
                append("\n")
                append(getMetaData("strValue", "1"))
                append("\n")
                append(getMetaData("intValue", 1).toString())

                append("\n")

                append("\n")
                append("真实包名：$packageName")
                append("\n")
                append("真实包名：${getName()}")

            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun getName(): String {
        val clazz = Class.forName("android.app.ActivityThread")
        val method = clazz.getDeclaredMethod("currentPackageName", null)
        val appPackageName = method.invoke(clazz, null) as String
        return appPackageName
    }
}

class TestSingleton private constructor(private val ctx: Context) {
    companion object : SingletonHolder<TestSingleton, Context>(::TestSingleton)


    fun print() {
        Log.d(TAG, "print: $ctx")
    }
}
