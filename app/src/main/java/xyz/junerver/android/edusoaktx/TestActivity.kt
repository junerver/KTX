package xyz.junerver.android.edusoaktx

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import xyz.junerver.kotlin.TAG
import xyz.junerver.kotlin.debounce

class TestActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val action = {
            Log.d(TAG, "onCreate: 我执行了")
            Unit
        }
        val debounced = action.debounce(this,10_000)
        findViewById<Button>(R.id.btn_debounce3).setOnClickListener {
            debounced()
        }
    }
}