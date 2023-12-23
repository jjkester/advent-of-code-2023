package nl.jjkester.adventofcode23.day19

import nl.jjkester.adventofcode23.day19.model.PartSystem
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/19
 */
object Aplenty {

    /**
     * Part one.
     */
    fun sumOfAcceptedRatings(system: PartSystem): Long = system.acceptedParts().sumOf { it.rating }

    /**
     * Part two.
     */
    fun acceptableCombinations(system: PartSystem): Long = system.acceptableParts().sumOf { it.count() }
}

fun main() {
    Aplenty.isDay(19) {
        parsedWith { PartSystem.parse(it) } first { sumOfAcceptedRatings(it) } then { acceptableCombinations(it) }
    }
}
