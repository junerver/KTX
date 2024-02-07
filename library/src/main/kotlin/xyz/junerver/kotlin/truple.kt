package xyz.junerver.kotlin

import java.io.Serializable

/**
 * Description: 扩展元组
 * @author Junerver
 * date: 2024/2/7-13:23
 * Email: junerver@gmail.com
 * Version: v1.0
 */

/**
 * 四个数据的元组类型
 */
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) : Serializable {
    public override fun toString(): String = "($first, $second, $third, $fourth)"
}

public fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

/**
 * 五个数据的元组类型
 */
data class Quintuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
) : Serializable {
    public override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

public fun <T> Quintuple<T, T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth, fifth)

/**
 * 六个数据的元组类型
 */
data class Sextuple<out A, out B, out C, out D, out E, out F>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F
) : Serializable {
    public override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"
}

public fun <T> Sextuple<T, T, T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth, fifth, sixth)

/**
 * 七个数据的元组类型
 */
data class Septuple<out A, out B, out C, out D, out E, out F, out G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G
) : Serializable {
    public override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

public fun <T> Septuple<T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh)

/**
 * 八个数据的元组类型
 */
data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H
) : Serializable {
    public override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth)"
}

public fun <T> Octuple<T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth)

/**
 * 九个数据的元组类型
 */
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
) : Serializable {
    public override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"
}

public fun <T> Nonuple<T, T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

/**
 * 十个数据的元组类型
 */
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
) : Serializable {
    public override fun toString(): String =
        "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth)"
}

public fun <T> Decuple<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth)

/**
 * 元组扩展，可以使用 `a to b to c` 这样的连续中缀函数创建更多元素的元组
 */
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
    Octuple(this.first, this.second, this.third, this.fourth, this.fifth, this.sixth, this.seventh, h)

infix fun <A, B, C, D, E, F, G, H, I> Octuple<A, B, C, D, E, F, G, H>.to(i: I): Nonuple<A, B, C, D, E, F, G, H, I> =
    Nonuple(this.first, this.second, this.third, this.fourth, this.fifth, this.sixth, this.seventh, this.eighth, i)

infix fun <A, B, C, D, E, F, G, H, I, J> Nonuple<A, B, C, D, E, F, G, H, I>.to(j: J): Decuple<A, B, C, D, E, F, G, H, I, J> =
    Decuple(this.first, this.second, this.third, this.fourth, this.fifth, this.sixth, this.seventh, this.eighth, this.ninth, j)