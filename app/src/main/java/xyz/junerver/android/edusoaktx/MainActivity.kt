package xyz.junerver.android.edusoaktx

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import xyz.junerver.android.edusoaktx.databinding.ActivityMainBinding
import xyz.junerver.kotlin.SingletonHolder
import xyz.junerver.kotlin.debounce
import xyz.junerver.kotlin.debounce2
import xyz.junerver.kotlin.fullScreen
import xyz.junerver.kotlin.getMetaData
import xyz.junerver.kotlin.runIf
import xyz.junerver.kotlin.setSingleClickListener
import xyz.junerver.kotlin.throttle
import xyz.junerver.kotlin.toColor
import xyz.junerver.kotlin.trimString
import xyz.junerver.kotlin.visibleOrInIf
import kotlin.properties.Delegates


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding //by Delegates.notNull()
    private val binding = _binding
    private var context: Context by Delegates.notNull()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        context = this
        runIf {
            TestSingleton.getInstance(this@MainActivity).print()
        }
        with(binding) {
            var t = false
            btnTest.setSingleClickListener {
                fullScreen()
                tvContent visibleOrInIf t
                t = !t
            }

            val action = {
                Log.d(TAG, "onCreate: 我执行了")
                Unit
            }
            val debounced = action.debounce(delay = 1000)
            val debounced2 = action.debounce2(1000)
            val throttled = action.throttle(1000)

            btnDebounce.setOnClickListener {
                Log.d(TAG, "btnDebounce: 按钮点击了")
                debounced()
            }

            btnThrottle.setOnClickListener {
                Log.d(TAG, "btnThrottle: 按钮点击了")
                throttled()
            }

            tvContent.setTextColor("#078910".toColor())
            etEdit.trimString()

            btnJump.setOnClickListener {
                startActivity(Intent(context, TestActivity::class.java))
            }

            runCatching {
                tvContent.apply {
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
