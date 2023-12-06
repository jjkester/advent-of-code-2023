package nl.jjkester.adventofcode23.predef.graph

/**
 * Graph implementation using hashcode-based data structures.
 *
 * @param T Type of elements in this graph.
 * @param V Type of vertices in this graph.
 * @param E Type of edges in this graph.
 */
class HashGraph<T, V : Vertex<T>, E : Edge<V>>(
    edges: Iterable<E>? = null,
    vertices: Iterable<V>? = null
) : MutableGraph<T, V, E> {

    private val data = hashMapOf<V, HashSet<E>>()

    override val vertices: Set<V> by lazy(LazyThreadSafetyMode.NONE) { VertexSet() }

    override val edges: Collection<E> by lazy(LazyThreadSafetyMode.NONE) { EdgeSet() }

    init {
        edges?.forEach(::addEdge)
        vertices?.forEach(::addVertex)
    }

    override fun containsEdge(edge: E): Boolean = data.values.any { edge in it }

    override fun containsVertex(vertex: V): Boolean = data.containsKey(vertex)

    override fun contains(element: T): Boolean = data.keys.any { it.element == element }

    override fun addVertex(vertex: V): Boolean = data.putIfAbsent(vertex, hashSetOf()) == null

    override fun addEdge(edge: E): Boolean = data.computeIfAbsent(edge.from) { hashSetOf() }.add(edge) &&
            data.computeIfAbsent(edge.to) { hashSetOf() }.add(edge)

    override fun removeVertex(vertex: V): Boolean = data.remove(vertex).also {
        data.values.forEach { edges ->
            edges.removeIf { vertex in it }
        }
    } != null

    override fun removeEdge(edge: E): Boolean = data[edge.from]?.remove(edge) ?: false &&
            data[edge.to]?.remove(edge) ?: false

    private inner class VertexSet : Set<V> by data.keys

    private inner class EdgeSet : Set<E> {
        override val size: Int
            get() = data.values.sumOf { it.size } / 2

        override fun isEmpty(): Boolean = data.values.run { isEmpty() || all { it.isEmpty() } }

        override fun iterator(): Iterator<E> = iterator {
            val seen = mutableSetOf<Edge<V>>()

            data.values.forEach { edges ->
                edges.forEach { edge ->
                    if (seen.add(edge)) {
                        yield(edge)
                    }
                }
            }
        }

        override fun containsAll(elements: Collection<E>): Boolean = elements.all(::contains)

        override fun contains(element: E): Boolean = containsEdge(element)
    }
}

/**
 * Creates and returns a [HashGraph] containing the [edges] and vertices that the [edges] connect.
 *
 * @param edges Edges in the graph.
 * @return A [HashGraph] containing the [edges].
 */
fun <T> hashGraphOf(vararg edges: Edge<Vertex<T>>): HashGraph<T, Vertex<T>, Edge<Vertex<T>>> =
    HashGraph(edges.asIterable())
