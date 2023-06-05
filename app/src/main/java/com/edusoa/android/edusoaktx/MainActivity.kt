package com.edusoa.android.edusoaktx

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.edusoa.android.kotlin.SingletonHolder
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
    }
}

class TestSingleton private constructor(ctx: Context) {
    companion object : SingletonHolder<TestSingleton, Context>(::TestSingleton)

    init {
        ctx
    }
}
