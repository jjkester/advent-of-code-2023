package nl.jjkester.adventofcode23.day03

import nl.jjkester.adventofcode23.day03.model.Schematic
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/3
 */
object GearRatios {

    /**
     * Part one.
     */
    fun sumOfPartNumbers(schematic: Schematic): Int = schematic.findPartNumbers().sum()

    /**
     * Part two.
     */
    fun sumOfGearRatios(schematic: Schematic): Long = schematic.findGears().sumOf { it.first.toLong() * it.second }
}

fun main() {
    GearRatios.isDay(3) {
        parsedWith { Schematic.parse(it) } first { sumOfPartNumbers(it) } then { sumOfGearRatios(it) }
    }
}
