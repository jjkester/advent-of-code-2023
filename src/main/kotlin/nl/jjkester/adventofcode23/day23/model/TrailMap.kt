package nl.jjkester.adventofcode23.day23.model

import nl.jjkester.adventofcode23.predef.graph.*
import nl.jjkester.adventofcode23.predef.space.*
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray

/**
 * A trail map containing multiple paths from a single [start] point to a single [end] point.
 */
class TrailMap private constructor(data: NDArray<Int, D2>) : MultikAreaMap<MapTile, Int>(data) {

    private val start: Coordinate2D = requireNotNull(
        getRow(y.first)
            .withIndex()
            .singleOrNull { it.value == MapTile.Path }
            ?.let { it.index + x.first by y.first }
    ) { "Top row must have a single path tile" }

    private val end: Coordinate2D = requireNotNull(
        getRow(y.last)
            .withIndex()
            .singleOrNull { it.value == MapTile.Path }
            ?.let { it.index + x.first by y.last }
    ) { "Bottom row must have a single path tile" }

    @Deprecated(
        message = "The graph-based implementation performs better due to problem shrinking.",
        replaceWith = ReplaceWith("toGraph().hikes(limitSlopes)"),
        level = DeprecationLevel.WARNING
    )
    fun hikes(limitSlopes: Boolean): Sequence<List<Coordinate2D>> = hikes(start, listOf(start), limitSlopes)

    /**
     * Creates a graph representation of this map. While constructing the graph the problem is shrinked by only
     * representing crossroads as vertices in the graph.
     *
     * @param limitSlopes Whether to only descend slopes.
     * @return A graph representation of this map. The graph may contain directed edges when [limitSlopes] is `true`.
     */
    fun toGraph(limitSlopes: Boolean): TrailGraph {
        val queue = ArrayDeque(listOf(start))
        val queued = mutableSetOf(start)
        val edges = mutableListOf<Edge.Weighted<Vertex<Coordinate2D>, Int>>()

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            findEdges(current).forEach { edge ->
                edges += edge

                listOf(edge.from.element, edge.to.element).forEach { coordinate ->
                    if (coordinate !in queued) {
                        queue.add(coordinate)
                        queued.add(coordinate)
                    }
                }
            }
        }

        val correctedEdges = edges.map { edge ->
            if (!limitSlopes && edge.directional) {
                edgeOf(edge.from, edge.to, edge.weight)
            } else {
                edge
            }
        }

        return TrailGraph(HashGraph(correctedEdges), vertexOf(start), vertexOf(end))
    }

    private fun hikes(
        from: Coordinate2D,
        path: List<Coordinate2D>,
        limitSlopes: Boolean
    ): Sequence<List<Coordinate2D>> {
        require(path.isNotEmpty()) { "Path may not be empty" }
        return from.neighbours()
            .filter { (coordinate, _) -> coordinate !in path }
            .flatMap { (coordinate, direction) ->
                if (coordinate !in this@TrailMap) {
                    if (path.size < 2) {
                        emptySequence()
                    } else {
                        // Found an end of a path that is different from stopping at the start
                        sequenceOf(path)
                    }
                } else {
                    when (val tile = get(coordinate)) {
                        MapTile.Forest -> emptySequence()
                        MapTile.Path -> hikes(coordinate, path + coordinate, limitSlopes)
                        is MapTile.Slope -> if (tile.downTo == direction || !limitSlopes) {
                            hikes(coordinate, path + coordinate, limitSlopes)
                        } else {
                            // Cannot access the slope in any other direction than downhill
                            emptySequence()
                        }
                    }
                }
            }
    }

    private fun findEdges(start: Coordinate2D) = Direction.entries.asSequence()
        .mapNotNull { findEdge(start, it) }

    private fun findEdge(start: Coordinate2D, startDirection: Direction) = findLine(start, startDirection)
        .takeIf { it.size > 1 }
        ?.let { path ->
            val slopeDirections = path
                .mapNotNull { (coordinate, direction) ->
                    val value = get(coordinate)

                    if (value is MapTile.Slope) {
                        value.downTo == direction
                    } else {
                        null
                    }
                }
                .distinct()

            check(slopeDirections.size in 0..1) { "Wrong assumption about slopes!" }

            when (slopeDirections.singleOrNull()) {
                true -> directionalEdgeOf(vertexOf(path.first().first), vertexOf(path.last().first), path.size - 1)
                false -> directionalEdgeOf(vertexOf(path.last().first), vertexOf(path.first().first), path.size - 1)
                null -> edgeOf(vertexOf(path.first().first), vertexOf(path.last().first), path.size - 1)
            }
        }

    private fun findLine(start: Coordinate2D, startDirection: Direction) = buildList {
        var previous: Coordinate2D? = null
        var current = start to startDirection
        var end: Pair<Coordinate2D, Direction>? = null

        while (end == null) {
            add(current)

            val next = current.first.neighbours()
                .filter { (coordinate, _) -> coordinate in this@TrailMap }
                .filter { (coordinate, _) -> get(coordinate) !is MapTile.Forest }
                .filter { (_, direction) -> current.first != start || direction == startDirection }
                .filter { (coordinate, _) -> coordinate != previous }
                .singleOrNull()

            if (next != null) {
                previous = current.first
                current = next
            } else {
                end = current
            }
        }
    }

    override fun serialize(element: MapTile): Int = element.toChar().code

    override fun deserialize(value: Int): MapTile = MapTile.parse(value.toChar())

    private fun Coordinate2D.neighbours() = sequence {
        yield(copy(x = x - 1) to Direction.West)
        yield(copy(x = x + 1) to Direction.East)
        yield(copy(y = y - 1) to Direction.North)
        yield(copy(y = y + 1) to Direction.South)
    }

    companion object {

        /**
         * Parses the [input] string to a [TrailMap] and returns it.
         *
         * @param input The [input] string to parse.
         * @return The [TrailMap] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [TrailMap].
         */
        fun parse(input: String): TrailMap {
            val tiles = input.lineSequence()
                .map { line -> line.map { MapTile.parse(it).toChar().code } }
                .toList()

            val width = tiles.maxOf { it.size }
            val height = tiles.size

            require(tiles.all { it.size == width }) { "All input lines must have the same length" }

            return TrailMap(
                Multik.d2arrayIndices(width, height) { x, y -> tiles[y][x] }
            )
        }
    }
}
