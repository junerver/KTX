package com.edusoa.android.edusoaktx

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.memoize
import arrow.core.recover
import arrow.core.right
import com.edusoa.android.kotlin.lazy.ManagedResettableLazy
import com.edusoa.android.kotlin.lazy.managedLazy
import com.edusoa.android.kotlin.lazy.resettableManager
import com.edusoa.android.kotlin.orElse
import com.edusoa.android.kotlin.runIf
import com.edusoa.android.kotlin.runUnless
import com.edusoa.android.kotlin.switches
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
    fun testArrowMemoized() {
        fun convert(a: Int): String {
            println("call convert$a")
            return "$a"
        }

        val memo = ::convert.memoize()
        val a1 = memo(10)
        val a2 = memo(10) // 第二次执行不会真正的回调函数，而是从记忆中取出纯函数的结果
        val a3 = memo(11)
    }

    @Test
    fun testResult() {
        val r = Result.success("很成功")
        val f = r.mapCatching {
            if (it.length < 5) throw RuntimeException("太短了") else it
        }.mapCatching {
            it.split(",")
        }.recoverCatching {
            throw RuntimeException(it.message + " 通过传递异常进行左值变形")
        }.onFailure {
            println(it.message)
        }.getOrNull()
        val l = Result.failure<RuntimeException>(RuntimeException("这事个错误")).mapCatching {
            println("不会执行")
        }.fold(
            onSuccess = {
                println("成功")
            },
            onFailure = {
                println("失败")
            }
        )
    }

    @Test
    fun testRuncatching() {
        runCatching {
            throw RuntimeException("error")
        }.getOrElse {
            //异常处理
            println("getOrElse 处理异常+ $it")
        }.also {
            //finnal处理
            println("这里相当于finally")
        }
        println("--------------------------------1")
        println(tryT(false))
        println("--------------------------------2")
        println(tryT(true))

        println("--------------------------------3")
        println(tryB(false))
        println("--------------------------------4")
        println(tryB(true))
    }

    fun tryT(tr: Boolean): String {
        try {
            if (tr) return "正确结果"
            throw RuntimeException("error")
        } catch (e: Exception) {
            println("捕获异常")
        } finally {
            println("终止时执行")
        }
        return "异常返回"
    }

    fun tryB(tr: Boolean): String {
        runCatching {
            if (tr) return "正确结果"
            throw RuntimeException("error")
        }.getOrElse {
            println("捕获异常")
        }.also {
            println("终止时执行")
        }
        return "异常返回"
    }


    @Test
    fun testIf() {
        var isTrue: Boolean? = null
        val func = {
            isTrue?.switches(
                ifTrue = { "true" },
                ifFalse = { it }
            ) ?: "null"
        }
        assertEquals("null", func())
        isTrue = true
        assertEquals("true", func())
        isTrue = false
        assertEquals(false, func())

        var string: String? = "nu"
        if (string == null || string.length < 3) {
            println(string)
        }

        string?.takeUnless { it.length < 3 } ?: run { println(string) }

    }

    @Test
    fun testEither() {
//        val result = "left".left().mapLeft {
//            println(it)
//        }.getOrNull()
//        println(result)
//
//        val x = "hello".left()
//        val value = x.getOrElse { "$it world!" }
//        println("value = $value")

        //被除数为0 left
        fun divide(a: Int, b: Int): Either<String, Int> =
            if (b != 0) (a / b).right() else "Division by zero".left()

        //入参为10时 left
        fun f1(n: Int): Either<String, Int> = if (n == 10) "error = 10".left() else n.right()

        //入参不为3时 left
        fun f2(n: Int): Either<String, Double> =
            if (n == 3) (n * Math.PI).right() else "error != 3__".left()

        val result2 = divide(10, 1)
            .flatMap(::f1) //此时将会变轨为left(10)
            .recover<String, Double, Int> {
                //触发变轨，变轨后结果为 right(3)
                println(it)
                raise((it.length / 3).toDouble())
            }.flatMap(::f2)

        println(result2)

        val transformedResult: Either<String, Any> = result2.mapLeft { error ->
            "An error occurred: $error"
        }

        when (transformedResult) {
            is Either.Right -> println("Result: ${transformedResult.value}")
            is Either.Left -> println("Error: ${transformedResult.value}")
        }
    }


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
        val p2 = handler2.toPartialFunction { it.code == 100 }
        val p23 = { it: Event ->
            println("handler23,${it.msg}")
        }.toPartialFunction { it.code == 99 }

        val applyChain =
            listOf(p1, p2, p23).reduce { acc, partialFunction -> acc orElse partialFunction }
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
        val bean1 by managedLazy(m) { Event(1, "2" + Random(1)) }
        val bean2 by managedLazy(m) { Event(1, "2" + Random(1)) }
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