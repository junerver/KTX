package com.edusoa.android.edusoaktx

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edusoa.android.kotlin.SingletonHolder
import com.edusoa.android.kotlin.runIf


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runIf {

        }
    }
}

class TestSingleton private constructor(ctx: Context) {
    companion object : SingletonHolder<TestSingleton, Context>(::TestSingleton)

    init {
        ctx
    }
}
