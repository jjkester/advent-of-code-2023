package nl.jjkester.adventofcode23.predef.graph

/**
 * A graph data structure that can be changed after creation.
 *
 * @param T Type of elements in this graph.
 * @param V Type of vertices in this graph.
 * @param E Type of edges in this graph.
 */
interface MutableGraph<T, V : Vertex<T>, E : Edge<V>> : Graph<T, V, E> {

    /**
     * Adds the [vertex] to the graph without connecting it.
     *
     * Returns `true` when the vertex was added or `false` when the vertex was already present in the graph.
     *
     * @param vertex The vertex to add.
     * @return Whether the [vertex] was added.
     */
    fun addVertex(vertex: V): Boolean

    /**
     * Adds the [edge] to the graph. Any vertices referenced by the edge that are not already present in the graph will
     * be added to the graph.
     *
     * Returns `true` when the edge was added or `false` when the edge was already present.
     *
     * @param edge The edge to add.
     * @return Whether the [edge] was added.
     */
    fun addEdge(edge: E): Boolean

    /**
     * Removes the [vertex] from the graph. Also removes the edges connected to the [vertex].
     *
     * Returns `true` when the vertex was removed or `false` when the vertex was not present in the graph.
     *
     * @param vertex The vertex to remove.
     * @return Whether the [vertex] was removed.
     */
    fun removeVertex(vertex: V): Boolean

    /**
     * Removes the [edge] from the graph. The vertices connected by the edge are not removed.
     *
     * Returns `true` when the edge was removed or `false` when the edge was not present in the graph.
     *
     * @param edge The edge to remove.
     * @return Whether the edge was removed.
     */
    fun removeEdge(edge: E): Boolean
}
