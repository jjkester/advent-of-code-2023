package nl.jjkester.adventofcode23.day13

import nl.jjkester.adventofcode23.day13.model.MirroredArea
import nl.jjkester.adventofcode23.execution.isDay
import nl.jjkester.adventofcode23.predef.sectionSequence

/**
 * https://adventofcode.com/2023/day/13
 */
object Incidence {

    fun parseAreas(input: String): List<MirroredArea> = input.sectionSequence().map(MirroredArea::parse).toList()

    /**
     * Part one.
     */
    fun summarizedNumber(areas: List<MirroredArea>): Long = areas.sumOf { area ->
        summarizedNumber(area.findHorizontalReflections(), area.findVerticalReflections())
    }

    /**
     * Part two.
     */
    fun alternativeSummarizedNumber(areas: List<MirroredArea>): Long = areas.sumOf { area ->
        try {
            summarizedNumber(area.findAlternativeHorizontalReflections(), area.findAlternativeVerticalReflections())
        } catch (ex: IllegalArgumentException) {
            println("Oops")
            throw ex
        }
    }

    private fun summarizedNumber(horizontal: Sequence<Int>, vertical: Sequence<Int>): Long {
        val horizontalItems = horizontal.toList()
        val verticalItems = vertical.toList()

        require(horizontalItems.isEmpty() xor verticalItems.isEmpty()) { "No exclusive reflection found" }

        return horizontalItems.firstOrNull()?.let { it * 100L } ?: verticalItems.firstOrNull()?.toLong() ?: 0L
    }
}

fun main() {
    Incidence.isDay(13) {
        parsedWith { parseAreas(it) } first { summarizedNumber(it) } then { alternativeSummarizedNumber(it) }
    }
}
