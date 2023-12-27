package nl.jjkester.adventofcode23.day23

import nl.jjkester.adventofcode23.day23.model.TrailMap
import nl.jjkester.adventofcode23.execution.isDay
import nl.jjkester.adventofcode23.predef.space.Coordinate2D

/**
 * https://adventofcode.com/2023/day/23
 */
object LongWalk {

    /**
     * Part one.
     */
    fun longestHike(trailMap: TrailMap): Int = trailMap.hikes(true).maxOf { it.size } - 1

    /**
     * Part two.
     */
    fun longestHikeWithoutSlopes(trailMap: TrailMap): Int = trailMap.toGraph(false).hikes()
        .maxBy { hike -> hike.sumOf { it.weight } }
        .also { hike ->
            hike.flatMap { listOf(it.from.element, it.to.element) }
                .sortedWith(compareBy(Coordinate2D::y, Coordinate2D::x))
                .joinToString(System.lineSeparator())
                .also(::println)
        }
        .let { hike -> hike.sumOf { it.weight } }
}

fun main() {
    LongWalk.isDay(23) {
        parsedWith { TrailMap.parse(it) } first { longestHike(it) } then { longestHikeWithoutSlopes(it) }
    }
}
