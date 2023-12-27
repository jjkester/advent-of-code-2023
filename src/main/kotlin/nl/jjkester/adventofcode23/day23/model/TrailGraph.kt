package nl.jjkester.adventofcode23.day23.model

import nl.jjkester.adventofcode23.predef.graph.Edge
import nl.jjkester.adventofcode23.predef.graph.Graph
import nl.jjkester.adventofcode23.predef.graph.Vertex
import nl.jjkester.adventofcode23.predef.space.Coordinate2D

private typealias GraphType = Graph<Coordinate2D, Vertex<Coordinate2D>, Edge.Weighted<Vertex<Coordinate2D>, Int>>

/**
 * A graph representation of a [TrailMap].
 */
class TrailGraph(inner: GraphType, private val start: Vertex<Coordinate2D>, private val end: Vertex<Coordinate2D>) : GraphType by inner {

    init {
        require(start in vertices) { "Start vertex not in graph" }
    }

    /**
     * Finds and returns the possible hikes from the [start] to the [end] vertices. Only hikes that visit each vertex
     * once are considered.
     *
     * Each hike is returned as a list of weighted edges, where the weight of each edge represents the number of steps
     * between the vertices of the edge.
     */
    fun hikes(): Sequence<List<Edge.Weighted<Vertex<Coordinate2D>, Int>>> = hikes(start, emptyList(), emptySet())
        .filter { it.isNotEmpty() }

    private fun hikes(
        from: Vertex<Coordinate2D>,
        path: List<Edge.Weighted<Vertex<Coordinate2D>, Int>>,
        visited: Set<Vertex<Coordinate2D>>
    ): Sequence<List<Edge.Weighted<Vertex<Coordinate2D>, Int>>> = edgesFrom(from)
        .filter { (if (it.from == from) it.to else it.from) !in visited }
        .takeIf { it.isNotEmpty() }
        ?.asSequence()
        ?.flatMap { hikes(if (it.from == from) it.to else it.from, path + it, visited + from) }
        ?: sequenceOf(path).takeIf { from == end }
        ?: emptySequence()
}
