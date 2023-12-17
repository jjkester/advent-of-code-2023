package nl.jjkester.adventofcode23.day17.model

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.predef.graph.directionalEdgeOf
import nl.jjkester.adventofcode23.predef.graph.vertexOf
import nl.jjkester.adventofcode23.predef.space.*
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.junit.jupiter.api.Test

class CityMapTest {

    private val systemUnderTest = CityMap(
        NumberMultikAreaMap(Multik.d2arrayIndices(3, 2) { x, y -> y * 3 + x + 1 })
    )

    @Test
    fun parse() {
        val testInput = """
            123
            456
        """.trimIndent()

        assertThat(CityMap.parse(testInput).data).all {
            transform { it.coordinates() }
                .containsExactlyInAnyOrder(0 by 0, 0 by 1, 1 by 0, 1 by 1, 2 by 0, 2 by 1)
            transform { it.values() }
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6)
            transform { it.coordinates().map { c -> c to it[c] } }
                .containsExactlyInAnyOrder(0 by 0 to 1, 1 by 0 to 2, 2 by 0 to 3, 0 by 1 to 4, 1 by 1 to 5, 2 by 1 to 6)
        }
    }

    @Test
    fun graph() {
        assertThat(systemUnderTest.graph).all {
            transform { it.vertices }
                .containsExactlyInAnyOrder(
                    vertexOf(CityMap.Block(0 by 0, 1)),
                    vertexOf(CityMap.Block(1 by 0, 2)),
                    vertexOf(CityMap.Block(2 by 0, 3)),
                    vertexOf(CityMap.Block(0 by 1, 4)),
                    vertexOf(CityMap.Block(1 by 1, 5)),
                    vertexOf(CityMap.Block(2 by 1, 6)),
                )
            transform { it.edges }
                .containsExactlyInAnyOrder(
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(0 by 0, 1)),
                        vertexOf(CityMap.Block(1 by 0, 2)),
                        Direction.East
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(1 by 0, 2)),
                        vertexOf(CityMap.Block(0 by 0, 1)),
                        Direction.West
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(1 by 0, 2)),
                        vertexOf(CityMap.Block(2 by 0, 3)),
                        Direction.East
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(2 by 0, 3)),
                        vertexOf(CityMap.Block(1 by 0, 2)),
                        Direction.West
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(0 by 1, 4)),
                        vertexOf(CityMap.Block(1 by 1, 5)),
                        Direction.East
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(1 by 1, 5)),
                        vertexOf(CityMap.Block(0 by 1, 4)),
                        Direction.West
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(1 by 1, 5)),
                        vertexOf(CityMap.Block(2 by 1, 6)),
                        Direction.East
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(2 by 1, 6)),
                        vertexOf(CityMap.Block(1 by 1, 5)),
                        Direction.West
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(0 by 0, 1)),
                        vertexOf(CityMap.Block(0 by 1, 4)),
                        Direction.South
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(0 by 1, 4)),
                        vertexOf(CityMap.Block(0 by 0, 1)),
                        Direction.North
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(1 by 0, 2)),
                        vertexOf(CityMap.Block(1 by 1, 5)),
                        Direction.South
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(1 by 1, 5)),
                        vertexOf(CityMap.Block(1 by 0, 2)),
                        Direction.North
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(2 by 0, 3)),
                        vertexOf(CityMap.Block(2 by 1, 6)),
                        Direction.South
                    ),
                    directionalEdgeOf(
                        vertexOf(CityMap.Block(2 by 1, 6)),
                        vertexOf(CityMap.Block(2 by 0, 3)),
                        Direction.North
                    )
                )
        }
    }

    @Test
    fun start() {
        assertThat(systemUnderTest.start)
            .isEqualTo(0 by 0)
    }

    @Test
    fun end() {
        assertThat(systemUnderTest.end)
            .isEqualTo(2 by 1)
    }
}
