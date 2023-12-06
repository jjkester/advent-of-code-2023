package nl.jjkester.adventofcode23.predef.graph

private data class MutableVertexImpl<T>(override var element: T) : MutableVertex<T> {

    override fun toString(): String = "MutableVertex($element)"
}

/**
 * Creates and returns a new [Vertex] containing the [element].
 *
 * @param element Element represented by the new [Vertex].
 * @return A new [Vertex] representing the [element].
 */
fun <T> mutableVertexOf(element: T): MutableVertex<T> = MutableVertexImpl(element)
