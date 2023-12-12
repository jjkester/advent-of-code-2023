package nl.jjkester.adventofcode23.day12

import nl.jjkester.adventofcode23.day12.model.Record
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/12
 */
object HotSprings {

    /**
     * Parses the [input] string into a list of [Record]s.
     */
    fun parseRecords(input: String): List<Record> = input.lineSequence().map(Record.Companion::parse).toList()

    /**
     * Part one.
     */
    fun sumOfPossibilities(records: List<Record>): Long = records.sumOf { it.numberOfPossibilities() }

    /**
     * Part two.
     */
    fun sumOfPossibilitiesUnfolded(records: List<Record>): Long = records
        .map { it.unfold(5) }
        .sumOf { it.numberOfPossibilities() }
}

fun main() {
    HotSprings.isDay(12) {
        parsedWith { parseRecords(it) } first { sumOfPossibilities(it) } then { sumOfPossibilitiesUnfolded(it) }
    }
}
