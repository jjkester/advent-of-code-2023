package nl.jjkester.adventofcode23.day18

import nl.jjkester.adventofcode23.day18.model.DigPlan
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/18
 */
object LavaductLagoon {

    /**
     * Part one.
     */
    fun cubicMetersOfLava(digPlan: DigPlan): Long = digPlan.size

    /**
     * Part one.
     */
    fun cubicMetersOfLavaSwapped(digPlan: DigPlan): Long = digPlan.alternative().size
}

fun main() {
    LavaductLagoon.isDay(18) {
        parsedWith { DigPlan.parse(it) } first { cubicMetersOfLava(it) } then { cubicMetersOfLavaSwapped(it) }
    }
}
