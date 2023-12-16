package nl.jjkester.adventofcode23.day14

import nl.jjkester.adventofcode23.day14.model.Platform
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/14
 */
object ReflectorDish {

    /**
     * Part one.
     */
    fun loadOnNorthBeams(platform: Platform): Int = platform.tiltNorth().northBeamLoad

    /**
     * Part two.
     */
    fun finalLoadOnNorthBeams(platform: Platform): Int = platform.spinCycles(1000000000L).northBeamLoad
}

fun main() {
    ReflectorDish.isDay(14) {
        parsedWith { Platform.parse(it) } first { loadOnNorthBeams(it) } then { finalLoadOnNorthBeams(it) }
    }
}
