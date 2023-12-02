package nl.jjkester.adventofcode23.predef

/**
 * Map of names of numbers to their values.
 */
private val numbersMap = mapOf(
    "zero" to 0,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

/**
 * A map from the names of numbers from the specified range to their integer values.
 */
fun numbersByName(
    range: IntRange = numbersMap.values.min()..numbersMap.values.max()
): Map<String, Int> = numbersMap.filter { it.value in range }.also {
    require(it.size == range.last - range.first + 1) {
        "Not all names of numbers in the given range are known"
    }
}
