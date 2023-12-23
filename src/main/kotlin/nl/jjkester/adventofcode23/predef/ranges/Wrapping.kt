package nl.jjkester.adventofcode23.predef.ranges

/**
 * Wraps the range around and returns the number in the range corresponding to the [number] as if the range is repeated
 * indefinitely.
 */
fun IntRange.wrap(number: Int): Int = ((number - first) % size).let { if (it < 0) size + it else it } + first

/**
 * Wraps the range around and returns the number in the range corresponding to the [number] as if the range is repeated
 * indefinitely.
 */
fun LongRange.wrap(number: Long): Long = ((number - first) % size).let { if (it < 0L) size + it else it } + first
