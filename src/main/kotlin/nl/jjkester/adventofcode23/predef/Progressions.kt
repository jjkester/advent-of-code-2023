package nl.jjkester.adventofcode23.predef

import kotlin.math.absoluteValue

/**
 * Amount of numbers in the [IntProgression].
 */
val IntProgression.size: Int
    get() = if (isEmpty()) 0 else (last - first) / step + 1

/**
 * Amount of numbers in the [UIntProgression].
 */
val UIntProgression.size: UInt
    get() = if (isEmpty()) 0u else (if (step < 0) first - last else last - first) / step.absoluteValue.toUInt() + 1u

/**
 * Amount of numbers in the [LongProgression].
 */
val LongProgression.size: Long
    get() = if (isEmpty()) 0L else (last - first) / step + 1

/**
 * Amount of numbers in the [ULongProgression].
 */
val ULongProgression.size: ULong
    get() = if (isEmpty()) 0uL else (if (step < 0) first - last else last - first) / step.absoluteValue.toULong() + 1uL
