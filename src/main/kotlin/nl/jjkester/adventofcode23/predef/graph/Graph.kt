package nl.jjkester.adventofcode23.predef.graph

/**
 * A graph data structure.
 *
 * @param T Type of elements in this graph.
 * @param V Type of vertices in this graph.
 * @param E Type of edges in this graph.
 */
interface Graph<out T, out V : Vertex<T>, out E : Edge<V>> {

    /**
     * The vertices in this graph.
     */
    val vertices: Set<V>

    /**
     * The edges in this graph.
     */
    val edges: Collection<E>

    /**
     * Returns whether the [element] is referenced by any vertex in this graph.
     *
     * @param element The element to find.
     * @return Whether the element is referenced by any vertex in this graph.
     */
    operator fun contains(element: @UnsafeVariance T): Boolean

    /**
     * Returns whether this graph contains the [vertex].
     *
     * @param vertex Vertex that may be contained within this graph.
     * @return Whether the [vertex] is contained in this graph.
     */
    fun containsVertex(vertex: @UnsafeVariance V): Boolean

    /**
     * Returns whether this graph contains the [edge].
     *
     * @param edge Edge that may be contained within this graph.
     * @return Whether the [edge] is contained in this graph.
     */
    fun containsEdge(edge: @UnsafeVariance E): Boolean
}
