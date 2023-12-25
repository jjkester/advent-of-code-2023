package nl.jjkester.adventofcode23.predef.ranges

/**
 * Shifts the range up or down by a fixed [amount].
 *
 * @param amount The amount to shift the range up or down by.
 * @return A new range with the same size as this range, starting from the [amount] higher.
 */
infix fun IntRange.shift(amount: Int): IntRange = (first + amount)..(last + amount)

/**
 * Shifts the range up or down by a fixed [amount].
 *
 * @param amount The amount to shift the range up or down by.
 * @return A new range with the same size as this range, starting from the [amount] higher.
 */
infix fun LongRange.shift(amount: Long): LongRange = (first + amount)..(last + amount)
