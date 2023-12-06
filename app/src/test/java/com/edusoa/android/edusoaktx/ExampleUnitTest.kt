package com.edusoa.android.edusoaktx

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.memoize
import arrow.core.recover
import arrow.core.right
import com.edusoa.android.kotlin.EncryptUtils
import com.edusoa.android.kotlin.EncryptUtils.decryptRsa
import com.edusoa.android.kotlin.EncryptUtils.encryptRsa
import com.edusoa.android.kotlin.arrow.toEither
import com.edusoa.android.kotlin.lazy.ManagedResettableLazy
import com.edusoa.android.kotlin.lazy.managedLazy
import com.edusoa.android.kotlin.lazy.resettableManager
import com.edusoa.android.kotlin.orElse
import com.edusoa.android.kotlin.printType
import com.edusoa.android.kotlin.runIf
import com.edusoa.android.kotlin.runUnless
import com.edusoa.android.kotlin.switches
import com.edusoa.android.kotlin.toHex
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
    fun testByA() {
        val a = "a".toByteArray()
        val b = "b".toByteArray()
        val x = a + b
        println(a.toHex())
        println(b.toHex())
        println(x.toHex())
    }

    @Test
    fun testByte() {
        val byteArray = "wo".toByteArray()
        //参照：https://www.utf8-chartable.de/unicode-utf8-table.pl?utf8=dec
        assertEquals(byteArray.toHex(), "77, 6f")
    }

    @Test
    fun testEncrypt() {
//        EncryptUtils.privateKey =
//            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKS8d/UKp4c+vR6MgGacwmKAJYHIowHLbny70VSKES7H3FMYOr3tazsyYugnBuvr8GYEU4oEqxexTizfqu5iIwqXHL2gsd6fs2SeJdMD0osvuuyNdB5xklKBocClXPs9Od786LIF4BH4BZwm67wcqdFpJmE65lVUFmQh0Wnumm/fAgMBAAECgYAH3Nb84x1L3zq3ko0uWJ0Ohn9Dyoe9NjB3058SIeTgDrn9XVKwbfyIPsdpvTMfX4uB0wMJu19PKi9JBQPrjNOPQjZq4X4uQ98GwiHXdzyFkSEflQt/ECgTzojr0QtR64u9RjpRRQ06WYHZr7+o/hKzN4k7aXW0vDp9yUEdHZt7gQJBANffrkCONY3ARimdPN/w/qpZyMVFPKR6Js1QHDuA24me1R/Dpb30ixQBNqE0sxot6DKlJp6rIbHDdemtyO/aNrcCQQDDW2pj+xY+CdHBviTIjApr873LNHA70HsKW0VRDPxwXB3/4P886ZyZsFReUiVZQtgBwx2U8A4n89Rg0pNZtagZAkBil/qR6WF0OFjTMMlYzkzBqPgVgSXNSSznoKsEUjnyhOR5+XV9aG8M1/EHd3ZFgqoGV39oAGkHM2prK8AWK+fJAkBCQjsvdfXxTLtMDwXCz1ypiJ4S0dJPN0LEHHjCKLlmEphKNTHcow9uQRQceZgZUkaZMmhSH6lte5HI1SMNBkPhAkBupa0r4eLTlCIJqsMv2JqtBdDaQzBF8MYMa2HrrVFmcoclUMLNkAmPfry+0BNhJmFZc4NryE9rGSLA9b/xTb9Y"
//        EncryptUtils.publicKey =
//            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkvHf1CqeHPr0ejIBmnMJigCWByKMBy258u9FUihEux9xTGDq97Ws7MmLoJwbr6/BmBFOKBKsXsU4s36ruYiMKlxy9oLHen7NkniXTA9KLL7rsjXQecZJSgaHApVz7PTne/OiyBeAR+AWcJuu8HKnRaSZhOuZVVBZkIdFp7ppv3wIDAQAB"

        val pass = "sss1893434"
        val encrypt = pass.encryptRsa()
        println(encrypt)
        println(encrypt.decryptRsa())
        assertEquals(pass, encrypt.decryptRsa())
    }

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
        val e1 = true.toEither()
        val e2 = false.toEither()
        assertEquals(e1.isRight(), true)
        assertEquals(e2.isRight(), false)

        //被除数为0 left
        fun divide(a: Int, b: Int): Either<String, Int> =
            if (b != 0) (a / b).right() else "Division by zero".left()

        //入参为10时 left
        fun f1(n: Int): Either<String, Int> = if (n == 10) "error = 10".left() else n.right()

        //入参不为3时 left
        fun f2(n: Int): Either<String, Double> =
            if (n == 3) (n * Math.PI).right() else "error != 3__".left()

        val result2 = divide(10, 1)
            .flatMap(::f1) //此时将会变轨为left("error = 10")
            .recover<String, Double, Int> {
                //触发变轨，使用raise变轨到新的left错误类型 string -> double
                raise((it.length / 3).toDouble()) //变轨后结果为 left(3.0)
            }.recover<Double, String, Int> {
                it.toInt() //普通变轨，不使用raise，将会变轨成right正确类型
            }.flatMap(::f2) //入参为3，最终结果为右值 3*PI


        assertEquals(result2.isRight(), true)
        printType(result2)
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