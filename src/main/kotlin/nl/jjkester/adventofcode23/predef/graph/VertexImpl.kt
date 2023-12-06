package nl.jjkester.adventofcode23.predef.graph

private data class VertexImpl<out T>(override val element: T) : Vertex<T> {

    override fun toString(): String = "Vertex(element=$element)"
}

/**
 * Creates and returns a new [Vertex] containing the [element].
 *
 * @param element Element represented by the new [Vertex].
 * @return A new [Vertex] representing the [element].
 */
fun <T> vertexOf(element: T): Vertex<T> = VertexImpl(element)
