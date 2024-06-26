package xyz.junerver.android.edusoaktx

import android.graphics.Color
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.memoize
import arrow.core.recover
import arrow.core.right
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import xyz.junerver.kotlin.toBoolean
import xyz.junerver.kotlin.BuildUtil
import xyz.junerver.kotlin.ColorX
import xyz.junerver.kotlin.Tuple
import xyz.junerver.kotlin.Tuple3
import xyz.junerver.kotlin.Tuple4
import xyz.junerver.kotlin.are
import xyz.junerver.kotlin.arrow.toEither
import xyz.junerver.kotlin.asBoolean
import xyz.junerver.kotlin.base64ToByteArray
import xyz.junerver.kotlin.decodeBase64
import xyz.junerver.kotlin.decryptRsa
import xyz.junerver.kotlin.encryptRsa
import xyz.junerver.kotlin.getBuildConfigValue
import xyz.junerver.kotlin.ifTrue
import xyz.junerver.kotlin.lazy.ManagedResettableLazy
import xyz.junerver.kotlin.lazy.managedLazy
import xyz.junerver.kotlin.lazy.resettableLazy
import xyz.junerver.kotlin.lazy.resettableManager
import xyz.junerver.kotlin.md5
import xyz.junerver.kotlin.padLeft
import xyz.junerver.kotlin.plus
import xyz.junerver.kotlin.printType
import xyz.junerver.kotlin.runIf
import xyz.junerver.kotlin.runUnless
import xyz.junerver.kotlin.switches
import xyz.junerver.kotlin.to
import xyz.junerver.kotlin.toBase64
import xyz.junerver.kotlin.toColorX
import xyz.junerver.kotlin.toHex
import xyz.junerver.kotlin.toPartialFunction
import xyz.junerver.kotlin.tp
import xyz.junerver.kotlin.tuple
import xyz.junerver.kotlin.`：`
import xyz.junerver.kotlin.`？`
import java.io.File
import kotlin.random.Random
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    @Test
    fun testBoolean() {
        var a: Boolean? = false
        println(a.asBoolean())
        a = null
        println(a.asBoolean())
        val al = listOf<Any?>("false", -1, null, "")
        al.map(::toBoolean).forEach(::println)
        if (toBoolean(a)) {
            println(a.hashCode())
        }
        val x: (() -> Unit)? = null
        if (x.asBoolean()) {
            x()
        }
        val xy = x.ifTrue()
        val srr = emptyArray<String>()
        println("数组检测：${srr.asBoolean()}  赋值：${srr.ifTrue()}")
        println(xy)
    }

    @Test
    fun testTuples() {
        val p = "p" + 2
        val t = ("t" to 3) + 5
        val d = ("d" to 3) + 5 + 9
        val f: Tuple = tuple("f", 2, 5, "6", "9")
        println(p)
        println(t)
        println(d)
        println(f)
        val (a, b, c) = with(d) { Tuple4(first, second, third, fourth) }
        println("$a,$b,$c")

        val s = tp[1,",",4,"asdas",5]
        println(s)
    }

    @Test
    fun testEvilCode() {
        val origin = 11
        var result: Int = -1
        result = origin shl 1 // *=2
        assertEquals(result, origin * 2)
        result = origin shr 1 // /=2
        assertEquals(result, origin / 2)
        result = (origin shl 1) + (origin shl 3) // *=10
        assertEquals(result, origin * 10)
    }

    @Test
    fun testTernary() {
        val r = true `？` { "r:true" } `：` { "r:false" }
        val r1 = false `？` "" `：` "r:false"
    }

    @Test
    fun testAlso() {
        fun t(): String {
            return "tttttt".also { println("lalalala") }
        }
        println("=====================s")
        println(t())
        println("=====================e")
    }

    @Test
    fun testColor() {
        val hex = ColorX("#ff0080")
        val rgb = Tuple3(255, 128, 0)
        println(rgb.toColorX().toColor())
        println(rgb.toColorX())
        assertEquals(rgb.toColorX().toColor(), Color.rgb(255, 128, 0))
        assertEquals(hex.toRGB().toColorX(), ColorX("#FF0080"))

        val aHexColor = ColorX("#80ff0080") //透明度88
        val argb = Tuple4(100, 255, 128, 0)
        println(argb.toColorX().toColor())
        println(argb.toColorX())
        println(0x64FF8000.toLong())
        println("------------------------")

        println(aHexColor.toColor())
        println(0x80ff0080)
        assertEquals(aHexColor.toColor(), Color.argb(128, 255, 0, 128))
        assertEquals(argb.toColorX().toColor(), Color.argb(100, 255, 128, 0))
        assertEquals(aHexColor.toARGB().toColorX(), ColorX("#80FF0080"))
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testFileEquals() {
        val f1 = File("D:\\ftp_root\\ios3.ipa")
        val f2 = File("D:\\ftp_root\\ios3.ipa")
        val t1 = measureNanoTime {
            repeat(10) {
                f1 == f2
            }
        }
        println("equales: $t1")
        println(f1.md5)

        val t2 = measureTimeMillis {
            repeat(10) {
                f1 are f2
            }
        }
        println("are: $t2")

        val t3 = measureTimeMillis {
            repeat(10000) {
                f1 are f2
            }
        }
        println("are: $t3")
    }

    @Test
    fun testBuildutil() {
        BuildUtil.initPackageName("xxx")
        getBuildConfigValue("test", "")

    }

    @Test
    fun testInline() {
        println("===================start")
        ablock({
            println("====================1")
            return@ablock
        }, {
            println("====================2")
            return@ablock
        })
        println("====================end")

    }

    inline fun ablock(noinline b1: () -> Unit, crossinline b2: () -> Unit) {
        b1()
        b2()
    }


    @Test
    fun testBase64() {
        // 原始数据
        val or = "lalala"
        // base64 编码
        val encodeStr = or.toBase64()
        // 解码后等同于原始数据
        assertEquals(encodeStr.decodeBase64(), or)
        //base64 还原成自己数组，等同于原始数据的字节数组
        assertEquals(encodeStr.base64ToByteArray().toHex(), or.toByteArray().toHex())
        //原始字符串base64编码后的ba
        val base64Ba = encodeStr.toByteArray()
        //解码后获得字节数组，然后还原成原始字符串
        val decode = base64Ba.decodeBase64().decodeToString()
        assertEquals(decode, or)
    }


    @Test
    fun testPad() {
        //左对齐
        val l = "wo".padLeft(8, '0')
        assertEquals(l, "wo000000")
    }


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
            listOf(p1, p2, p23).reduce { acc, partialFunction -> acc + partialFunction }
        p1(Event(10, "code 10"))
        applyChain(Event(100, "code 100"))
        applyChain(Event(99, "code 99"))
//        applyChain(Event(101,"code 101"))


    }

    @Test
    fun testLazy() {
        val resettableDelegate = resettableLazy {
            Event(1,"2"+ Random(1))
        }
        val readOnly by resettableDelegate
        println(readOnly)
        //通过delegate对象实现reset，从而再次调用初始化函数
        resettableDelegate.reset()
        println(readOnly)

        val bean by managedLazy { Event(1, "2" + Random(1)) }
        println(bean)
        println(bean)
        //默认管理器时，通过单例函数重置
        ManagedResettableLazy.reset()
        println(bean)

        val m = resettableManager()
        val bean1 by managedLazy(m) { Event(1, "2" + Random(1)) }
        val bean2 by managedLazy(m) { Event(1, "2" + Random(1)) }
        println(bean1)
        println(bean1)
        println(bean2)
        println(bean2)
        m.reset()
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