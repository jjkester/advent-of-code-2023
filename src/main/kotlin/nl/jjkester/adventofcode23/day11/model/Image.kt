package nl.jjkester.adventofcode23.day11.model

import nl.jjkester.adventofcode23.predef.ranges.size
import nl.jjkester.adventofcode23.predef.space.*

/**
 * An image of galaxies.
 *
 * @property x The width of the image.
 * @property y The height of the image.
 * @property galaxies The coordinates on this image where galaxies are located.
 * @property galaxyAge The age factor of the galaxy. This factor models the expansion of the universe.
 */
class Image(
    private val x: IntRange,
    private val y: IntRange,
    private val galaxies: Set<Coordinate2D>,
    private val galaxyAge: Long
) {

    private val doubleColumns: Set<Int> by lazy(LazyThreadSafetyMode.NONE) {
        x.asSequence().filter { col -> galaxies.none { it.x == col } }.toSet()
    }

    private val doubleRows: Set<Int> by lazy(LazyThreadSafetyMode.NONE) {
        y.asSequence().filter { row -> galaxies.none { it.y == row } }.toSet()
    }

    init {
        require(galaxyAge >= 1) { "Galaxy age must be at least one" }
    }

    /**
     * Finds and returns the shortest paths between all galaxies, taking the [galaxyAge] into account.
     *
     * @return A sequence of shortest paths between galaxies.
     */
    fun findShortestPaths(): Sequence<Path> = sequence {
        val seen = mutableListOf<Coordinate2D>()

        galaxies.forEach { first ->
            seen.add(first)

            galaxies.forEach { second ->
                if (second !in seen) {
                    val xRange = minOf(first.x, second.x)..<maxOf(first.x, second.x)
                    val yRange = minOf(first.y, second.y)..<maxOf(first.y, second.y)
                    val doubleColumns = xRange.count { it in doubleColumns } * (galaxyAge - 1)
                    val doubleRows = yRange.count { it in doubleRows } * (galaxyAge - 1)

                    yield(Path(first, second, xRange.size + yRange.size + doubleColumns + doubleRows))
                }
            }
        }
    }

    /**
     * Interprets this image with a different [galaxyAge].
     *
     * @param galaxyAge The new age of the galaxy to use.
     * @return A new image with the same data and a different interpretation of the [galaxyAge].
     */
    fun expand(galaxyAge: Long): Image = Image(x, y, galaxies, galaxyAge)

    companion object {

        /**
         * Parses the [input] string to an [Image] and returns it.
         *
         * @param input The input string to parse.
         * @return The image modeled by the input.
         */
        fun parse(input: String): Image {
            val coordinates = input.lineSequence()
                .withIndex()
                .flatMap { (y, line) ->
                    line.asSequence()
                        .withIndex()
                        .filter { it.value == '#' }
                        .map { (x, _) -> coordinateOf(x, y) }
                }
                .toSet()

            val x = coordinates.minOf { it.x }..coordinates.maxOf { it.x }
            val y = coordinates.minOf { it.y }..coordinates.maxOf { it.y }

            return Image(x, y, coordinates, 2L)
        }
    }

    data class Path(val first: Coordinate2D, val second: Coordinate2D, val distance: Long)
}
