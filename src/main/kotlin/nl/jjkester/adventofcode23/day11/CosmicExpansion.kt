package nl.jjkester.adventofcode23.day11

import nl.jjkester.adventofcode23.day11.model.Image
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/11
 */
object CosmicExpansion {

    /**
     * Part one.
     */
    fun sumOfShortestPaths(image: Image): Long = image.findShortestPaths().sumOf { it.distance }

    /**
     * Part two.
     */
    fun sumOfShortestPathsOlderGalaxy(image: Image, galaxyAge: Long = 1_000_000): Long =
        image.expand(galaxyAge).findShortestPaths().sumOf { it.distance }
}

fun main() {
    CosmicExpansion.isDay(11) {
        parsedWith { Image.parse(it) } first { sumOfShortestPaths(it) } then { sumOfShortestPathsOlderGalaxy(it) }
    }
}
