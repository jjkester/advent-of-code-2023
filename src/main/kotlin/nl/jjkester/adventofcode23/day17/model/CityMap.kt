package nl.jjkester.adventofcode23.day17.model

import nl.jjkester.adventofcode23.predef.graph.*
import nl.jjkester.adventofcode23.predef.space.*
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices

typealias CityMapGraph = Graph<CityMap.Block, Vertex<CityMap.Block>, Edge.Weighted<Vertex<CityMap.Block>, Direction>>

/**
 * A map of the city.
 */
class CityMap(val data: AreaMap<Int>) {

    /**
     * The coordinate to start at.
     */
    val start: Coordinate2D = data.x.first by data.y.first

    /**
     * The coordinate to end at.
     */
    val end: Coordinate2D = data.x.last by data.y.last

    /**
     * The map as directed graph of city blocks with directions.
     */
    val graph: CityMapGraph by lazy {
        val vertical = data.x.flatMap { x ->
            data.y.asSequence().windowed(2).flatMap { (y1, y2) ->
                val lower = vertexOf(blockOf(x by y1))
                val upper = vertexOf(blockOf(x by y2))

                sequenceOf(
                    directionalEdgeOf(lower, upper, Direction.South),
                    directionalEdgeOf(upper, lower, Direction.North)
                )
            }
        }
        val horizontal = data.y.flatMap { y ->
            data.x.asSequence().windowed(2).flatMap { (x1, x2) ->
                val lower = vertexOf(blockOf(x1 by y))
                val upper = vertexOf(blockOf(x2 by y))

                sequenceOf(
                    directionalEdgeOf(lower, upper, Direction.East),
                    directionalEdgeOf(upper, lower, Direction.West)
                )
            }
        }

        HashGraph(vertical + horizontal)
    }

    private fun blockOf(coordinate: Coordinate2D): Block = Block(coordinate, data[coordinate])

    companion object {

        /**
         * Parses the [input] string to a [CityMap] and returns it.
         *
         * @param input The input string to parse.
         * @return The [CityMap] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [CityMap].
         */
        fun parse(input: String): CityMap {
            val contents = input.lineSequence()
                .map { it.map(Char::digitToInt) }
                .toList()

            val width = contents.maxOf { it.size }
            val height = contents.size

            require(contents.all { it.size == width }) { "All input lines must have the same length" }

            return CityMap(
                NumberMultikAreaMap(
                    Multik.d2arrayIndices(width, height) { x, y -> contents[y][x] }
                )
            )
        }
    }

    /**
     * A city block.
     *
     * @property coordinate Location of the city block on the map.
     * @property heatLoss Expected heat loss while moving through the city block.
     */
    data class Block(val coordinate: Coordinate2D, val heatLoss: Int)
}
