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

/**
 * Ensures that this range lies in the [other] range.
 *
 * @param other The range to fit this range in.
 * @return This range if it is in the [other] range, or a new range that is in both this and the [other] range.
 */
fun IntRange.coerceIn(other: IntRange): IntRange = if (first < other.first || last > other.last) {
    first.coerceAtLeast(other.first)..last.coerceAtMost(other.last)
} else {
    this
}

/**
 * Ensures that this progression lies in the [other] range.
 *
 * @param other The progression to fit this progression in.
 * @return This progression if it is in the [other] progression, or a new progression that is in both this and the
 * [other] progression.
 */
fun IntProgression.coerceIn(other: IntProgression): IntProgression = if (first < other.first || last > other.last) {
    first.coerceAtLeast(other.first)..last.coerceAtMost(other.last)
} else {
    this
}

/**
 * Ensures that this range lies in the [other] range.
 *
 * @param other The range to fit this range in.
 * @return This range if it is in the [other] range, or a new range that is in both this and the [other] range.
 */
fun LongRange.coerceIn(other: LongRange): LongRange = if (first < other.first || last > other.last) {
    first.coerceAtLeast(other.first)..last.coerceAtMost(other.last)
} else {
    this
}

/**
 * Ensures that this progression lies in the [other] range.
 *
 * @param other The progression to fit this progression in.
 * @return This progression if it is in the [other] progression, or a new progression that is in both this and the
 * [other] progression.
 */
fun LongProgression.coerceIn(other: LongProgression): LongProgression = if (first < other.first || last > other.last) {
    first.coerceAtLeast(other.first)..last.coerceAtMost(other.last)
} else {
    this
}
