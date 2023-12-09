package nl.jjkester.adventofcode23.predef.graph

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

class HashGraphTest {

    @ParameterizedTest
    @MethodSource("vertices")
    fun getVertices(
        systemUnderTest: HashGraph<Int?, Vertex<Int?>, Edge<Vertex<Int?>>>,
        expectedElements: Array<Vertex<Int?>>
    ) {
        assertThat(systemUnderTest.vertices)
            .containsExactlyInAnyOrder(*expectedElements)
    }

    @ParameterizedTest
    @MethodSource("edges")
    fun getEdges(
        systemUnderTest: HashGraph<Int?, Vertex<Int?>, Edge<Vertex<Int?>>>,
        expectedElements: Array<Edge<Vertex<Int?>>>
    ) {
        assertThat(systemUnderTest.edges).all {
            transform { it.size }.isEqualTo(expectedElements.size)
            transform { it.isEmpty() }.isEqualTo(expectedElements.isEmpty())
            transform { it.toSet() }.containsExactlyInAnyOrder(*expectedElements)
            transform { it.containsAll(expectedElements.toList()) }.isTrue()
            containsExactlyInAnyOrder(*expectedElements)
        }
    }

    @ParameterizedTest
    @MethodSource("edges")
    fun containsEdge(
        systemUnderTest: HashGraph<Int?, Vertex<Int?>, Edge<Vertex<Int?>>>,
        expectedElements: Array<Edge<Vertex<Int?>>>
    ) {
        assertThat(systemUnderTest)
            .transform { expectedElements.filter { expected -> it.containsEdge(expected) } }
            .containsExactlyInAnyOrder(*expectedElements)
    }

    @ParameterizedTest
    @MethodSource("vertices")
    fun containsVertex(
        systemUnderTest: HashGraph<Int?, Vertex<Int?>, Edge<Vertex<Int?>>>,
        expectedElements: Array<Vertex<Int?>>
    ) {
        assertThat(systemUnderTest)
            .transform { expectedElements.filter { expected -> it.containsVertex(expected) } }
            .containsExactlyInAnyOrder(*expectedElements)
    }

    @ParameterizedTest
    @MethodSource("elements")
    fun contains(
        systemUnderTest: HashGraph<Int?, Vertex<Int?>, Edge<Vertex<Int?>>>,
        expectedElements: Array<Int?>
    ) {
        assertThat(systemUnderTest)
            .transform { expectedElements.filter { expected -> it.contains(expected) } }
            .containsExactlyInAnyOrder(*expectedElements)
    }

    @Test
    fun addVertex() {
        val systemUnderTest = empty
        val element = Random.nextInt()

        assertThat(systemUnderTest.addVertex(vertexOf(element)))
            .isTrue()
        assertThat(systemUnderTest.addVertex(vertexOf(element)))
            .isFalse()

        assertThat(systemUnderTest.vertices)
            .containsExactlyInAnyOrder(vertexOf(element))
        assertThat(systemUnderTest.edges)
            .isEmpty()
    }

    @Test
    fun addEdge() {
        val systemUnderTest = empty
        val element = Random.nextInt()

        assertThat(systemUnderTest.addEdge(edgeOf(vertexOf(null), vertexOf(element))))
            .isTrue()
        assertThat(systemUnderTest.addEdge(edgeOf(vertexOf(null), vertexOf(element))))
            .isFalse()

        assertThat(systemUnderTest.vertices)
            .containsExactlyInAnyOrder(vertexOf(null), vertexOf(element))
        assertThat(systemUnderTest.edges)
            .containsExactlyInAnyOrder(edgeOf(vertexOf(null), vertexOf(element)))
    }

    @Test
    fun removeVertex() {
        val systemUnderTest = withNull

        assertThat(systemUnderTest.removeVertex(vertexOf(null)))
            .isTrue()

        assertThat(systemUnderTest.vertices)
            .doesNotContain(vertexOf(null))
        assertThat(systemUnderTest.edges).all {
            isNotEmpty()
            extracting { it.contains(vertexOf(null)) }
                .containsOnly(false)
        }

        assertThat(systemUnderTest.removeVertex(vertexOf(null)))
            .isFalse()
    }

    @Test
    fun removeEdge() {
        val systemUnderTest = withNull

        assertThat(systemUnderTest.removeEdge(edgeOf(vertexOf(1), vertexOf(null))))
            .isTrue()

        assertThat(systemUnderTest.vertices)
            .contains(vertexOf(null))
        assertThat(systemUnderTest.edges)
            .doesNotContain(edgeOf(vertexOf(1), vertexOf(null)))

        assertThat(systemUnderTest.removeEdge(edgeOf(vertexOf(1), vertexOf(null))))
            .isFalse()
    }

    @ParameterizedTest
    @MethodSource("edgesFrom")
    fun edgesFrom(
        systemUnderTest: HashGraph<Int?, Vertex<Int?>, Edge<Vertex<Int?>>>,
        vertex: Vertex<Int?>,
        expected: Set<Edge<*>>
    ) {
        assertThat(systemUnderTest.edgesFrom(vertex))
            .isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("edgesTo")
    fun edgesTo(
        systemUnderTest: HashGraph<Int?, Vertex<Int?>, Edge<Vertex<Int?>>>,
        vertex: Vertex<Int?>,
        expected: Set<Edge<*>>
    ) {
        assertThat(systemUnderTest.edgesTo(vertex))
            .isEqualTo(expected)
    }

    companion object {
        private val empty
            get() = hashGraphOf<Int?>()
        private val withNull
            get() = hashGraphOf(
                edgeOf(vertexOf(1), vertexOf(null)),
                edgeOf(vertexOf(null), vertexOf(2)),
                edgeOf(vertexOf(2), vertexOf(3))
            )
        private val directional
            get() = hashGraphOf(
                edgeOf(vertexOf(1), vertexOf(null)),
                directionalEdgeOf(vertexOf(null), vertexOf(2)),
                directionalEdgeOf(vertexOf(2), vertexOf(3)),
                directionalEdgeOf(vertexOf(3), vertexOf(2))
            )

        @JvmStatic
        fun vertices() = arrayOf(
            Arguments.of(empty, emptyArray<Vertex<*>>()),
            Arguments.of(withNull, arrayOf(vertexOf(1), vertexOf(2), vertexOf(3), vertexOf(null)))
        )

        @JvmStatic
        fun edges() = arrayOf(
            Arguments.of(empty, emptyArray<Edge<*>>()),
            Arguments.of(
                withNull,
                arrayOf(
                    edgeOf(vertexOf(1), vertexOf(null)),
                    edgeOf(vertexOf(null), vertexOf(2)),
                    edgeOf(vertexOf(2), vertexOf(3)),
                )
            )
        )

        @JvmStatic
        fun elements() = arrayOf(
            Arguments.of(empty, emptyArray<Int?>()),
            Arguments.of(withNull, arrayOf(1, 2, 3, null))
        )

        @JvmStatic
        fun edgesFrom() = arrayOf(
            Arguments.of(
                directional,
                vertexOf(1),
                setOf(edgeOf(vertexOf(1), vertexOf(null)))
            ),
            Arguments.of(
                directional,
                vertexOf(null),
                setOf(
                    edgeOf(vertexOf(1), vertexOf(null)),
                    directionalEdgeOf(vertexOf(null), vertexOf(2))
                )
            ),
            Arguments.of(
                directional,
                vertexOf(2),
                setOf(directionalEdgeOf(vertexOf(2), vertexOf(3)))
            ),
            Arguments.of(directional, vertexOf(4), emptySet<Edge<*>>())
        )

        @JvmStatic
        fun edgesTo() = arrayOf(
            Arguments.of(
                directional,
                vertexOf(1),
                setOf(edgeOf(vertexOf(1), vertexOf(null)))
            ),
            Arguments.of(
                directional,
                vertexOf(null),
                setOf(edgeOf(vertexOf(1), vertexOf(null)))
            ),
            Arguments.of(
                directional,
                vertexOf(2),
                setOf(
                    directionalEdgeOf(vertexOf(null), vertexOf(2)),
                    directionalEdgeOf(vertexOf(3), vertexOf(2))
                )
            ),
            Arguments.of(directional, vertexOf(4), emptySet<Edge<*>>())
        )
    }
}
