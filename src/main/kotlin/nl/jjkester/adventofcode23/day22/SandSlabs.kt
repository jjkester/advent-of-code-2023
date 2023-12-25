package nl.jjkester.adventofcode23.day22

import nl.jjkester.adventofcode23.day22.model.Snapshot
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/22
 */
object SandSlabs {

    /**
     * Part one.
     */
    fun safeToDisintegrate(snapshot: Snapshot): Int = snapshot.toGround().disintegrationCandidates()
        .count { it.second == 0 }

    /**
     * Part two.
     */
    fun sumOfFallingBricks(snapshot: Snapshot): Long = snapshot.toGround().disintegrationCandidates()
        .sumOf { it.second.toLong() }
}

fun main() {
    SandSlabs.isDay(22) {
        parsedWith { Snapshot.parse(it) } first { safeToDisintegrate(it) } then { sumOfFallingBricks(it) }
    }
}
