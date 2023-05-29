package com.edusoa.android.edusoaktx

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edusoa.android.kotlin.SingletonHolder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class TestSingleton private constructor(ctx: Context){
    companion object: SingletonHolder<TestSingleton, Context>(::TestSingleton)
    init {
        ctx
    }
}
