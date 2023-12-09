package nl.jjkester.adventofcode23.day08

import nl.jjkester.adventofcode23.day08.model.PouchMap
import nl.jjkester.adventofcode23.execution.isDay
import nl.jjkester.adventofcode23.predef.lcm

/**
 * https://adventofcode.com/2023/day/8
 */
object HauntedWasteland {

    /**
     * Part one.
     */
    fun steps(map: PouchMap): Int = map.walk().takeWhile { !it }.count()

    /**
     * Part two.
     */
    fun stepsWithGhosts(map: PouchMap): Long = map.withGhosts()
            .map { ghostMap ->
                ghostMap.walk()
                .dropWhile { !it }
                .drop(1)
                .takeWhile { !it }
                .count()
                .toLong()
                .inc()
            }
            .reduce { first, second -> lcm(first, second) }
}

fun main() {
    HauntedWasteland.isDay(8) {
        parsedWith { PouchMap.parse(it) } first { steps(it) } then { stepsWithGhosts(it) }
    }
}
