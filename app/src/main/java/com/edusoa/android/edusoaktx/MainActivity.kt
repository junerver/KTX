package com.edusoa.android.edusoaktx

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import android.content.res.AssetManager
import android.content.res.XmlResourceParser
import android.os.Bundle
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.edusoa.android.kotlin.SingletonHolder
import com.edusoa.android.kotlin.fullScreen
import com.edusoa.android.kotlin.getMetaData
import com.edusoa.android.kotlin.getPackageInfo
import com.edusoa.android.kotlin.getRealPackageName
import com.edusoa.android.kotlin.hideBottomUIMenu
import com.edusoa.android.kotlin.hideKeyboard
import com.edusoa.android.kotlin.runIf
import com.edusoa.android.kotlin.setSingleClickListener
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runIf {

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
                append("真实包名：$packageName")
                append("\n")
                append("真实包名：${getPackageNameFromManifest(this@MainActivity)}")
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun getPackageNameFromManifest(context: Context): String? {
        return try {
            val ai = context.packageManager.getApplicationInfo(context.packageName, GET_META_DATA)
            println(ai)
            val parser: XmlResourceParser = ai.loadXmlMetaData(context.packageManager, "AndroidManifest.xml")

            while (parser.eventType != XmlResourceParser.END_DOCUMENT) {
                if (parser.eventType == XmlResourceParser.START_TAG && parser.name == "manifest") {
                    for (i in 0 until parser.attributeCount) {
                        if (parser.getAttributeName(i) == "package") {
                            return parser.getAttributeValue(i)
                        }
                    }
                }
                parser.next()
            }

            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

class TestSingleton private constructor(ctx: Context) {
    companion object : SingletonHolder<TestSingleton, Context>(::TestSingleton)

    init {
        ctx
    }
}
