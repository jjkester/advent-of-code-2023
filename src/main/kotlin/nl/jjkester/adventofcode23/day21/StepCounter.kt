package nl.jjkester.adventofcode23.day21

import nl.jjkester.adventofcode23.day21.model.Garden
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/21
 */
object StepCounter {

    /**
     * Part one.
     */
    fun reachableGardenPlots(garden: Garden, steps: Int = 64): Int = garden.reachableInPrecise(steps)

    /**
     * Part two.
     */
    fun reachableGardenPlotsRepeating(garden: Garden, steps: Int = 26501365): Long = garden.reachableInEstimated(steps)
}

fun main() {
    StepCounter.isDay(21) {
        parsedWith { Garden.parse(it) } first { reachableGardenPlots(it) } then { reachableGardenPlotsRepeating(it) }
    }
}
