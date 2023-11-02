@file: JvmName("-either")

package com.edusoa.android.kotlin.arrow

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.edusoa.android.kotlin.switches

/**
 * Description:
 * @author Junerver
 * date: 2023/11/1-15:38
 * Email: junerver@gmail.com
 * Version: v1.0
 */

/**
 * 将一个 Boolean 转成 Either 方便进行 fold 函数调用。
 * 作用类似于 [switches]，但是转成Either包装更方便
 */
fun Boolean.toEither(): Either<Boolean, Boolean> = if (this) this.right() else this.left()