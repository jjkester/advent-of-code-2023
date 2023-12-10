package nl.jjkester.adventofcode23.day10

import nl.jjkester.adventofcode23.day10.model.PipeGrid
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/10
 */
object PipeMaze {

    /**
     * Part one.
     */
    fun stepsToFarthestPoint(grid: PipeGrid): Int = grid.findLoops().maxOf { it.size } / 2

    /**
     * Part two.
     */
    fun pointsEnclosedByLoop(grid: PipeGrid): Int = grid.findContainedAreas().maxOf { it.size }
}

fun main() {
    PipeMaze.isDay(10) {
        parsedWith { PipeGrid.parse(it) } first { stepsToFarthestPoint(it) } then { pointsEnclosedByLoop(it) }
    }
}
