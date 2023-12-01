package nl.jjkester.adventofcode23.day01

import nl.jjkester.adventofcode23.execution.isDay
import nl.jjkester.adventofcode23.predef.numbersByName

/**
 * https://adventofcode.com/2023/day/1
 */
object TrebuchetCalibration {

    /**
     * Part one.
     */
    fun sumOfCalibrationValues(input: String): Int = calibrationValues(input, includeWords = false).sum()

    /**
     * Part two.
     */
    fun sumOfCalibrationValuesIncludingWords(input: String): Int = calibrationValues(input, includeWords = true).sum()

    /**
     * Computes the calibration values for the given input.
     *
     * @param includeWords Whether the names of digits should be taken into consideration.
     */
    private fun calibrationValues(input: String, includeWords: Boolean) = input.lineSequence()
        .map { it.extractDigits(includeWords) }
        .map { it.first() * 10 + it.last() }

    /**
     * Extracts digits from the [String].
     *
     * @param includeWords Whether the names of digits should be taken into consideration.
     */
    private fun String.extractDigits(includeWords: Boolean): List<Int> = if (includeWords) {
        extractDigitsIncludingWords().toList()
    } else {
        mapNotNull { it.digitToIntOrNull() }
    }

    /**
     * Extracts digits from the [String], including names of digits. When names overlap, all possible digits are
     * included in the result. For example, 'eighthree' (where the letter `t` overlaps) will yield both '1' and '3'.
     */
    private fun String.extractDigitsIncludingWords() = sequence {
        var searchIndex = 0

        while (searchIndex < length) {
            var lookaheadIndex = 0
            var word = ""

            while (lookaheadIndex < maxNumberNameLength && searchIndex + lookaheadIndex < length) {
                val char = this@extractDigitsIncludingWords[searchIndex + lookaheadIndex]
                word += char

                val result = when {
                    word.length == 1 && char.isDigit() -> char.digitToInt()
                    word in numbers -> numbers[word]
                    else -> null
                }

                if (result != null) {
                    yield(result)
                    break
                } else {
                    lookaheadIndex++
                }
            }

            searchIndex++
        }
    }

    private val numbers = numbersByName(1..9)
    private val maxNumberNameLength = numbers.maxOf { it.key.length }
}

fun main() {
    TrebuchetCalibration.isDay(1) {
        withoutParsing first { sumOfCalibrationValues(it) } then { sumOfCalibrationValuesIncludingWords(it) }
    }
}
