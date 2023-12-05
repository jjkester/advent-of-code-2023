package nl.jjkester.adventofcode23.day05

import nl.jjkester.adventofcode23.day05.model.Almanac
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/5
 */
object Seeds {

    /**
     * Part one.
     */
    fun lowestLocationNumberSingleSeeds(almanac: Almanac): Long = almanac.findIndividualLocations().minOf { it.value }

    /**
     * Part two.
     */
    fun lowestLocationNumberSeedRanges(almanac: Almanac): Long = almanac.findRangeLocations().minOf { it.start.value }
}

fun main() {
    Seeds.isDay(5) {
        parsedWith { Almanac.parse(it) } first { lowestLocationNumberSingleSeeds(it) } then { lowestLocationNumberSeedRanges(it) }
    }
}
