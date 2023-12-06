package nl.jjkester.adventofcode23.predef.graph

/**
 * A vertex in a graph.
 *
 * @param T The type of element represented by this vertex.
 */
interface Vertex<out T> {

    /**
     * The element represented by this vertex.
     */
    val element: T
}
