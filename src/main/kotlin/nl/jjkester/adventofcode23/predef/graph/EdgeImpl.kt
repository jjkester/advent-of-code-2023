package nl.jjkester.adventofcode23.predef.graph

private data class EdgeImpl<out V : Vertex<*>, W : Comparable<W>>(
    override val from: V,
    override val to: V,
    override val directional: Boolean,
    override val weight: W
) : Edge.Weighted<V, W> {

    override fun contains(vertex: @UnsafeVariance V): Boolean = vertex == from || vertex == to

    override fun toString(): String =
        "Edge($from ${if (directional) "-" else "<"}-${if (weight != NoWeight) "[$weight]" else ""}-> $to)"

    object NoWeight : Comparable<NoWeight> {

        override fun compareTo(other: NoWeight): Int = 0
    }
}

/**
 * Creates and returns a new undirected and unweighted edge between the [first] and [second] vertices.
 *
 * @param first First vertex to connect the edge to.
 * @param second Second vertex to connect the edge to.
 * @return An undirected and unweighted edge between the [first] and [second] vertices.
 */
fun <V : Vertex<*>> edgeOf(first: V, second: V): Edge<V> =
    edgeOf(first, second, EdgeImpl.NoWeight)

/**
 * Creates and returns a new undirected edge between the [first] and [second] vertices with the specified [weight].
 *
 * @param first First vertex to connect the edge to.
 * @param second Second vertex to connect the edge to.
 * @param weight Weight of this edge.
 * @return An undirected and unweighted edge between the [first] and [second] vertices with [weight].
 */
fun <V : Vertex<*>, W : Comparable<W>> edgeOf(first: V, second: V, weight: W): Edge.Weighted<V, W> =
    EdgeImpl(first, second, false, weight)

/**
 * Creates and returns a new undirected and unweighted edge[from] the first [to] the second vertex.
 *
 * @param from Vertex to start the directed edge from.
 * @param to Vertex to end the directed edge at.
 * @return An undirected and unweighted edge [from] the first [to] the second vertex.
 */
fun <V : Vertex<*>> directionalEdgeOf(from: V, to: V): Edge<V> =
    directionalEdgeOf(from, to, EdgeImpl.NoWeight)

/**
 * Creates and returns a new directed edge [from] the first [to] the second vertex with the specified [weight].
 *
 * @param from Vertex to start the directed edge from.
 * @param to Vertex to end the directed edge at.
 * @param weight Weight of this edge.
 * @return An undirected and unweighted edge [from] the first [to] the second vertex with [weight].
 */
fun <V : Vertex<*>, W : Comparable<W>> directionalEdgeOf(from: V, to: V, weight: W): Edge.Weighted<V, W> =
    EdgeImpl(from, to, true, weight)
