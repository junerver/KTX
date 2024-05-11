@file: JvmName("-tuple")

package xyz.junerver.kotlin

import java.io.Serializable


//region 类型别名，不应该用这个名称
typealias Tuple0 = None
typealias Tuple1<A> = Single<A>
typealias Tuple2<A, B> = Pair<A, B>
typealias Tuple3<A, B, C> = Triple<A, B, C>
typealias Tuple4<A, B, C, D> = Quadruple<A, B, C, D>
typealias Tuple5<A, B, C, D, E> = Quintuple<A, B, C, D, E>
typealias Tuple6<A, B, C, D, E, F> = Sextuple<A, B, C, D, E, F>
typealias Tuple7<A, B, C, D, E, F, G> = Septuple<A, B, C, D, E, F, G>
typealias Tuple8<A, B, C, D, E, F, G, H> = Octuple<A, B, C, D, E, F, G, H>
typealias Tuple9<A, B, C, D, E, F, G, H, I> = Nonuple<A, B, C, D, E, F, G, H, I>
typealias Tuple10<A, B, C, D, E, F, G, H, I, J> = Decuple<A, B, C, D, E, F, G, H, I, J>

//endregion
interface Tuple : Serializable

typealias tp = None

//region 元组类与扩展函数
object None : Tuple {
    operator fun get(vararg elements: Any): Tuple{
        return with(elements) {
            when (size) {
                0 -> None
                1-> tuple(get(0))
                2-> tuple(get(0),get(1))
                3 -> tuple(get(0), get(1), get(2))
                4 -> tuple(get(0), get(1), get(2), get(3))
                5 -> tuple(get(0), get(1), get(2), get(3), get(4))
                6 -> tuple(get(0), get(1), get(2), get(3), get(4), get(5))
                7 -> tuple(get(0), get(1), get(2), get(3), get(4), get(5), get(6))
                8 -> tuple(get(0), get(1), get(2), get(3), get(4), get(5), get(6), get(7))
                9 -> tuple(get(0), get(1), get(2), get(3), get(4), get(5), get(6), get(7), get(8))
                10 -> tuple(get(0), get(1), get(2), get(3), get(4), get(5), get(6), get(7), get(8),get(9))
                else -> {error("too much tuples")}
            }
        }
    }
}

public fun <T> None.toList(): List<T> = emptyList()


data class Single<out A>(
    val first: A
) : Tuple {
    public override fun toString(): String = "($first)"
}

public fun <T> Single<T>.toList(): List<T> = listOf(first)

public data class Pair<out A, out B>(
    public val first: A,
    public val second: B
) : Tuple {
    public override fun toString(): String = "($first, $second)"
}

public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)

public fun <T> kotlin.Pair<T, T>.toList(): List<T> = listOf(first, second)


public data class Triple<out A, out B, out C>(
    public val first: A,
    public val second: B,
    public val third: C
) : Tuple {
    public override fun toString(): String = "($first, $second, $third)"
}

/**
 * Converts this triple into a list.
 *
 * @sample samples.misc.Tuples.tripleToList
 */
public fun <T> Triple<T, T, T>.toList(): List<T> = listOf(first, second, third)


/** 四个数据的元组类型 */
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) : Tuple {
    public override fun toString(): String = "($first, $second, $third, $fourth)"
}

public fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

/** 五个数据的元组类型 */
data class Quintuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
) : Tuple {
    public override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

public fun <T> Quintuple<T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth)

/** 六个数据的元组类型 */
data class Sextuple<out A, out B, out C, out D, out E, out F>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F
) : Tuple {
    public override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"
}

public fun <T> Sextuple<T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth)

/** 七个数据的元组类型 */
data class Septuple<out A, out B, out C, out D, out E, out F, out G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G
) : Tuple {
    public override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

public fun <T> Septuple<T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh)

/** 八个数据的元组类型 */
data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H
) : Tuple {
    public override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth)"
}

public fun <T> Octuple<T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth)

/** 九个数据的元组类型 */
data class Nonuple<out A, out B, out C, out D, out E, out F, out G, out H, out I>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I
) : Tuple {
    public override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"
}

public fun <T> Nonuple<T, T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

/** 十个数据的元组类型 */
data class Decuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J
) : Tuple {
    public override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth)"
}

public fun <T> Decuple<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth)

/** 元组扩展，可以使用 `a to b to c` 这样的连续中缀函数创建更多元素的元组 */
infix fun <A, B> Single<A>.to(b: B): Pair<A, B> = Pair(this.first, b)

infix fun <A, B, C> Pair<A, B>.to(c: C): Triple<A, B, C> = Triple(this.first, this.second, c)

infix fun <A, B, C, D> Triple<A, B, C>.to(d: D): Quadruple<A, B, C, D> =
    Quadruple(this.first, this.second, this.third, d)

infix fun <A, B, C, D, E> Quadruple<A, B, C, D>.to(e: E): Quintuple<A, B, C, D, E> =
    Quintuple(this.first, this.second, this.third, this.fourth, e)

infix fun <A, B, C, D, E, F> Quintuple<A, B, C, D, E>.to(f: F): Sextuple<A, B, C, D, E, F> =
    Sextuple(this.first, this.second, this.third, this.fourth, this.fifth, f)

infix fun <A, B, C, D, E, F, G> Sextuple<A, B, C, D, E, F>.to(g: G): Septuple<A, B, C, D, E, F, G> =
    Septuple(this.first, this.second, this.third, this.fourth, this.fifth, this.sixth, g)

infix fun <A, B, C, D, E, F, G, H> Septuple<A, B, C, D, E, F, G>.to(h: H): Octuple<A, B, C, D, E, F, G, H> =
    Octuple(
        this.first,
        this.second,
        this.third,
        this.fourth,
        this.fifth,
        this.sixth,
        this.seventh,
        h
    )

infix fun <A, B, C, D, E, F, G, H, I> Octuple<A, B, C, D, E, F, G, H>.to(i: I): Nonuple<A, B, C, D, E, F, G, H, I> =
    Nonuple(
        this.first,
        this.second,
        this.third,
        this.fourth,
        this.fifth,
        this.sixth,
        this.seventh,
        this.eighth,
        i
    )

infix fun <A, B, C, D, E, F, G, H, I, J> Nonuple<A, B, C, D, E, F, G, H, I>.to(j: J): Decuple<A, B, C, D, E, F, G, H, I, J> =
    Decuple(
        this.first,
        this.second,
        this.third,
        this.fourth,
        this.fifth,
        this.sixth,
        this.seventh,
        this.eighth,
        this.ninth,
        j
    )

public operator fun <A, B, C> Pair<A, B>.plus(c: C): Triple<A, B, C> =
    Triple(this.first, this.second, c)

public operator fun <A, B, C, D> Triple<A, B, C>.plus(d: D): Tuple4<A, B, C, D> =
    Tuple4(this.first, this.second, this.third, d)

public operator fun <A, B, C, D, E> Tuple4<A, B, C, D>.plus(e: E): Tuple5<A, B, C, D, E> =
    Tuple5(this.first, this.second, this.third, this.fourth, e)

public operator fun <A, B, C, D, E, F> Tuple5<A, B, C, D, E>.plus(f: F): Tuple6<A, B, C, D, E, F> =
    Tuple6(this.first, this.second, this.third, this.fourth, this.fifth, f)

public operator fun <A, B, C, D, E, F, G> Tuple6<A, B, C, D, E, F>.plus(g: G): Tuple7<A, B, C, D, E, F, G> =
    Tuple7(this.first, this.second, this.third, this.fourth, this.fifth, this.sixth, g)

public operator fun <A, B, C, D, E, F, G, H> Tuple7<A, B, C, D, E, F, G>.plus(h: H): Tuple8<A, B, C, D, E, F, G, H> =
    Tuple8(
        this.first,
        this.second,
        this.third,
        this.fourth,
        this.fifth,
        this.sixth,
        this.seventh,
        h
    )

public operator fun <A, B, C, D, E, F, G, H, I> Tuple8<A, B, C, D, E, F, G, H>.plus(i: I): Tuple9<A, B, C, D, E, F, G, H, I> =
    Tuple9(
        this.first,
        this.second,
        this.third,
        this.fourth,
        this.fifth,
        this.sixth,
        this.seventh,
        this.eighth,
        i
    )

public operator fun <A, B, C, D, E, F, G, H, I, J> Tuple9<A, B, C, D, E, F, G, H, I>.plus(j: J): Tuple10<A, B, C, D, E, F, G, H, I, J> =
    Tuple10(
        this.first,
        this.second,
        this.third,
        this.fourth,
        this.fifth,
        this.sixth,
        this.seventh,
        this.eighth,
        this.ninth,
        j
    )
//endregion


//region tuple函数
fun tuple() = None

fun <A> tuple(first: A): Tuple1<A> =
    Tuple1(first)

fun <A, B> tuple(first: A, second: B): Tuple2<A, B> =
    Tuple2(first, second)

fun <A, B, C> tuple(first: A, second: B, third: C): Tuple3<A, B, C> =
    Tuple3(first, second, third)

fun <A, B, C, D> tuple(
    first: A,
    second: B,
    third: C,
    fourth: D
): Tuple4<A, B, C, D> =
    Tuple4(first, second, third, fourth)

fun <A, B, C, D, E> tuple(
    first: A,
    second: B,
    third: C,
    fourth: D,
    fifth: E
): Tuple5<A, B, C, D, E> = Tuple5(first, second, third, fourth, fifth)

fun <A, B, C, D, E, F> tuple(
    first: A,
    second: B,
    third: C,
    fourth: D,
    fifth: E,
    sixth: F
): Tuple6<A, B, C, D, E, F> = Tuple6(first, second, third, fourth, fifth, sixth)

fun <A, B, C, D, E, F, G> tuple(
    first: A,
    second: B,
    third: C,
    fourth: D,
    fifth: E,
    sixth: F,
    seventh: G
): Tuple7<A, B, C, D, E, F, G> = Tuple7(first, second, third, fourth, fifth, sixth, seventh)

fun <A, B, C, D, E, F, G, H> tuple(
    first: A,
    second: B,
    third: C,
    fourth: D,
    fifth: E,
    sixth: F,
    seventh: G,
    eighth: H
): Tuple8<A, B, C, D, E, F, G, H> =
    Tuple8(first, second, third, fourth, fifth, sixth, seventh, eighth)

fun <A, B, C, D, E, F, G, H, I> tuple(
    first: A,
    second: B,
    third: C,
    fourth: D,
    fifth: E,
    sixth: F,
    seventh: G,
    eighth: H,
    ninth: I
): Tuple9<A, B, C, D, E, F, G, H, I> =
    Tuple9(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

fun <A, B, C, D, E, F, G, H, I, J> tuple(
    first: A,
    second: B,
    third: C,
    fourth: D,
    fifth: E,
    sixth: F,
    seventh: G,
    eighth: H,
    ninth: I,
    tenth: J
): Tuple10<A, B, C, D, E, F, G, H, I, J> =
    Tuple10(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth)
//endregion