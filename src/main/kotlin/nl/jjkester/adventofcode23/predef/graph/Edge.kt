package nl.jjkester.adventofcode23.predef.graph

/**
 * An edge in a graph.
 *
 * @param V Type of vertices connected by this edge.
 */
interface Edge<out V : Vertex<*>> {

    /**
     * The vertex the edge is leaving from. This is significant when [directional] is `true`, otherwise, [from] and [to]
     * are interchangeable.
     */
    val from: V

    /**
     * The vertex the edge is connecting to. This is significant when [directional] is `true`, otherwise, [from] and
     * [to] are interchangeable.
     */
    val to: V

    /**
     * Whether this edge is directional. When this is `true`, [from] and [to] have a specific meaning.
     */
    val directional: Boolean

    /**
     * Returns whether this edge connects to the [vertex] without taking [directionality][directional] into account.
     *
     * @param vertex Vertex that may be connected by this edge.
     * @return Whether the [vertex] is connected by this edge.
     */
    operator fun contains(vertex: @UnsafeVariance V): Boolean

    /**
     * A weighted edge in a graph.
     *
     * @param V Type of vertices connected by this edge.
     * @param W Type of weight of this edge.
     */
    interface Weighted<out V : Vertex<*>, W : Comparable<W>> : Edge<V> {

        /**
         * The weight of this edge.
         */
        val weight: W
    }
}
