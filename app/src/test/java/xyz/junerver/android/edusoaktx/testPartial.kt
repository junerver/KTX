package xyz.junerver.android.edusoaktx

import arrow.core.partially1
import org.junit.Test

/**
 * Description:
 * @author Junerver
 * date: 2023/11/3-10:18
 * Email: junerver@gmail.com
 * Version: v1.0
 */

typealias PartialFunc<P1, R> = (isDefinedAt: (P1) -> Boolean, func: (P1) -> R, p: P1) -> R

val chainPartialFuncV: PartialFunc<Event, Unit> = { d, f, p ->
    if (d(p)) {
        f(p)
    } else {
        throw IllegalArgumentException("Value: ($p) isn't supported by this function")
    }
}

fun ((Event) -> Unit).toPartialFunction1(definedAt: (Event) -> Boolean): (Event) -> Unit {
    return chainPartialFuncV.partially1 { definedAt(it) }.partially1 { this(it) }
}

infix fun ((Event) -> Unit).orElse(p2: (Event) -> Unit): (Event) -> Unit {
    return try {
        this
    } catch (e: IllegalArgumentException) {
        p2
    }
}


data class Event(val code: Int)

class ExampleUnitTest2 {
    @Test
    fun addition_isCorrect() {
        val handler1: (e: Event) -> Unit = {
            println("handler1,${it.code}")
        }

        val handler2: (e: Event) -> Unit = {
            println("handler2,${it.code}")
        }

        val p1 = handler1.toPartialFunction1 { it.code == 10 }
        val p2 = handler2.toPartialFunction1 { it.code == 100 }
        p1(Event(10))
        val pp = p1 orElse p2
        pp(Event(10))
        pp(Event(100))
    }

}