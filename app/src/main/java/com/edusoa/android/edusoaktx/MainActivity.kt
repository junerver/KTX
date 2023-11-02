package com.edusoa.android.edusoaktx

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.edusoa.android.kotlin.SingletonHolder
import com.edusoa.android.kotlin.getMetaData
import com.edusoa.android.kotlin.runIf
import com.edusoa.android.kotlin.setSingleClickListener


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runIf {

        }
        findViewById<Button>(R.id.btn_test).setSingleClickListener {

        }

        runCatching{
            findViewById<TextView>(R.id.tv_content).apply {
                append("\n")
                append(getMetaData("strValue", "1"))
                append("\n")
                append(getMetaData("intValue", 1).toString())
                append("\n")
                append(getMetaData("floatValue", 1.0F).toString())
                append("\n")
                append(getMetaData("longValue", 1L).toString())
            }
        }
    }
}

class TestSingleton private constructor(ctx: Context) {
    companion object : SingletonHolder<TestSingleton, Context>(::TestSingleton)

    init {
        ctx
    }
}
