package nl.jjkester.adventofcode23.day16

import nl.jjkester.adventofcode23.day16.model.Contraption
import nl.jjkester.adventofcode23.day16.model.Direction
import nl.jjkester.adventofcode23.execution.isDay
import nl.jjkester.adventofcode23.predef.space.by

/**
 * https://adventofcode.com/2023/day/16
 */
object LavaFloor {

    /**
     * Part one.
     */
    fun energizedTiles(contraption: Contraption): Int = contraption.energizedFrom(0 by 0, Direction.East)

    /**
     * Part two.
     */
    fun maxEnergizedTiles(contraption: Contraption): Int = contraption.edges
        .maxOf { contraption.energizedFrom(it.first, it.second) }
}

fun main() {
    LavaFloor.isDay(16) {
        parsedWith { Contraption.parse(it) } first { energizedTiles(it) } then { maxEnergizedTiles(it) }
    }
}
