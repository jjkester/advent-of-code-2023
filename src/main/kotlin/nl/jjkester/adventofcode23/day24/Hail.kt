package nl.jjkester.adventofcode23.day24

import nl.jjkester.adventofcode23.day24.model.Hailstone3D
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/24
 */
object Hail {

    /**
     * Parses the [input] string to a list of [Hailstone3D]s.
     */
    fun parseHailstones(input: String): List<Hailstone3D> = input.lines().map(Hailstone3D::parse)

    /**
     * Part one.
     */
    fun intersections(
        hailstones: List<Hailstone3D>,
        range: ClosedRange<Double> = 200_000_000_000_000.0..400_000_000_000_000.0
    ): Int = hailstones.asSequence()
        .flatMapIndexed { index, first ->
            hailstones.asSequence()
                .drop(index)
                .mapNotNull { second ->
                    first.to2D().futureIntersectionWith(second.to2D())
                }
        }
        .count { it.x in range && it.y in range }
}

fun main() {
    Hail.isDay(24) {
        parsedWith { parseHailstones(it) } first { intersections(it) }
    }
}
