package nl.jjkester.adventofcode23.day15

import nl.jjkester.adventofcode23.day15.model.HolidayAscii
import nl.jjkester.adventofcode23.day15.model.InitializationStep
import nl.jjkester.adventofcode23.day15.model.LightBoxes
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/15
 */
object LensLibrary {

    /**
     * Part one.
     */
    fun hashOfInitializationSequence(string: String): Int = string
        .removeSurrounding(System.lineSeparator())
        .splitToSequence(',')
        .sumOf { HolidayAscii.hash(it).toInt() }

    /**
     * Part two.
     */
    fun totalFocusingPower(string: String): Long = LightBoxes()
        .apply { parseInstructions(string).forEach(::performStep) }
        .focusingPower

    private fun parseInstructions(input: String): Sequence<InitializationStep> = input
        .splitToSequence(',')
        .map(InitializationStep::parse)
}

fun main() {
    LensLibrary.isDay(15) {
        withoutParsing first { hashOfInitializationSequence(it) } then { totalFocusingPower(it) }
    }
}
