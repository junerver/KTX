package com.edusoa.android.edusoaktx

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.GET_META_DATA
import android.content.res.XmlResourceParser
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.edusoa.android.kotlin.BuildUtil
import com.edusoa.android.kotlin.SingletonHolder
import com.edusoa.android.kotlin.fullScreen
import com.edusoa.android.kotlin.getBuildConfigValue
import com.edusoa.android.kotlin.getMetaData
import com.edusoa.android.kotlin.runIf
import com.edusoa.android.kotlin.setSingleClickListener


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runIf {
            TestSingleton.getInstance(this@MainActivity).print()
        }
        findViewById<Button>(R.id.btn_test).setSingleClickListener {
            fullScreen()
        }


        runCatching{
            findViewById<TextView>(R.id.tv_content).apply {
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

    fun getName():String {
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
