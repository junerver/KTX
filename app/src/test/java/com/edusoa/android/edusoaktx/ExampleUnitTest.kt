package com.edusoa.android.edusoaktx

import com.edusoa.android.kotlin.lazy.ManagedResettableLazy
import com.edusoa.android.kotlin.lazy.managedLazy
import com.edusoa.android.kotlin.lazy.resettableLazy
import com.edusoa.android.kotlin.lazy.resettableManager
import com.edusoa.android.kotlin.orElse
import com.edusoa.android.kotlin.runIf
import com.edusoa.android.kotlin.runUnless
import com.edusoa.android.kotlin.toPartialFunction
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val handler1: (e: Event) -> Event = {
            println("handler1,${it.msg}")
            Event(it.code, "handler by 1,${it.msg}")
        }


        val handler2: (e: Event) -> Unit = {
            println("handler2,${it.msg}")
        }
        val p1 = handler1.toPartialFunction { it.code == 10 }
        val p2 = handler2.toPartialFunction { it.code <= 100 }
        val p23 = { it: Event ->
            println("handler23,${it.msg}")
        }.toPartialFunction { it.code == 99 }

        val applyChain = p1 orElse p2 orElse p23
        p1(Event(10, "code 10"))
        applyChain(Event(100, "code 100"))
        applyChain(Event(99, "code 99"))
//        applyChain(Event(101,"code 101"))

    }

    @Test
    fun testLazy() {
//        val resettableDelegate = resettableLazy {
//            Event(1,"2"+ Random(1))
//        }
//        val readOnly by resettableDelegate
//        println(readOnly)
//        //通过delegate对象实现reset，从而再次调用初始化函数
//        resettableDelegate.reset()
//        println(readOnly)
//
//        val bean by managedLazy { Event(1, "2" + Random(1)) }
//        println(bean)
//        println(bean)
//        //默认管理器时，通过单例函数重置
//        ManagedResettableLazy.reset()
//        println(bean)

        val m = resettableManager()
        val bean1 by managedLazy(m){Event(1, "2" + Random(1)) }
        val bean2 by managedLazy(m){Event(1, "2" + Random(1)) }
        println(bean1)
        println(bean1)
        println(bean2)
        println(bean2)
        m.reset()
        ManagedResettableLazy.reset()
        println(bean1)
        println(bean2)

    }

    @Test
    fun testRunIf() {
        val a = runIf { "xxxx" }
        val b = runUnless { "aaaaa" }
        println(a)
        println(b)

    }

    data class Event(val code: Int, val msg: String)
    open class Data {
        protected open val value: Int = 0
    }

    class OwData : Data() {
        public override val value: Int = 1
    }
}