package nl.jjkester.adventofcode23.day23.model

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import nl.jjkester.adventofcode23.predef.graph.directionalEdgeOf
import nl.jjkester.adventofcode23.predef.graph.edgeOf
import nl.jjkester.adventofcode23.predef.graph.vertexOf
import nl.jjkester.adventofcode23.predef.space.by
import org.junit.jupiter.api.Test

class TrailMapTest {

    @Test
    fun hikesWithSlopes() {
        assertThat(parsedTestInput.hikes(true))
            .extracting { it.size }
            .containsExactlyInAnyOrder(95, 91, 87, 83, 83, 75)
    }

    @Test
    fun hikesWithoutSlopes() {
        assertThat(parsedTestInput.hikes(false))
            .transform { it.maxOf(Collection<*>::size) }
            .isEqualTo(155)
    }

    @Test
    fun toGraphWithSlopes() {
        assertThat(parsedTestInput.toGraph(true)).all {
            transform { it.vertices }
                .containsExactlyInAnyOrder(
                    vertexOf(1 by 0),
                    vertexOf(3 by 5),
                    vertexOf(5 by 13),
                    vertexOf(11 by 3),
                    vertexOf(13 by 13),
                    vertexOf(13 by 19),
                    vertexOf(19 by 19),
                    vertexOf(21 by 11),
                    vertexOf(21 by 22)
                )
            transform { it.edges }.all {
                matchesPredicate { it.all { it.directional } }
                containsAll(
                    directionalEdgeOf(vertexOf(1 by 0), vertexOf(3 by 5), 15),
                    directionalEdgeOf(vertexOf(5 by 13), vertexOf(13 by 13), 12),
                    directionalEdgeOf(vertexOf(13 by 19), vertexOf(19 by 19), 10),
                    directionalEdgeOf(vertexOf(19 by 19), vertexOf(21 by 22), 5)
                )
            }
        }
    }

    @Test
    fun toGraphWithoutSlopes() {
        assertThat(parsedTestInput.toGraph(false)).all {
            transform { it.vertices }
                .containsExactlyInAnyOrder(
                    vertexOf(1 by 0),
                    vertexOf(3 by 5),
                    vertexOf(5 by 13),
                    vertexOf(11 by 3),
                    vertexOf(13 by 13),
                    vertexOf(13 by 19),
                    vertexOf(19 by 19),
                    vertexOf(21 by 11),
                    vertexOf(21 by 22)
                )
            transform { it.edges }.all {
                matchesPredicate { it.none { it.directional } }
                containsAll(
                    edgeOf(vertexOf(1 by 0), vertexOf(3 by 5), 15),
                    edgeOf(vertexOf(5 by 13), vertexOf(13 by 13), 12),
                    edgeOf(vertexOf(13 by 19), vertexOf(19 by 19), 10),
                    edgeOf(vertexOf(19 by 19), vertexOf(21 by 22), 5)
                )
            }
        }
    }

    @Test
    fun hikesWithSlopesWithGraph() {
        assertThat(parsedTestInput.toGraph(true).hikes())
            .extracting { it.sumOf { it.weight } }
            .containsExactlyInAnyOrder(94, 90, 86, 82, 82, 74)
    }

    @Test
    fun hikesWithoutSlopesWithGraph() {
        assertThat(parsedTestInput.toGraph(false).hikes())
            .transform { it.maxOf { it.sumOf { it.weight } } }
            .isEqualTo(154)
    }

    companion object {
        private val testInput = """
            #.#####################
            #.......#########...###
            #######.#########.#.###
            ###.....#.>.>.###.#.###
            ###v#####.#v#.###.#.###
            ###.>...#.#.#.....#...#
            ###v###.#.#.#########.#
            ###...#.#.#.......#...#
            #####.#.#.#######.#.###
            #.....#.#.#.......#...#
            #.#####.#.#.#########v#
            #.#...#...#...###...>.#
            #.#.#v#######v###.###v#
            #...#.>.#...>.>.#.###.#
            #####v#.#.###v#.#.###.#
            #.....#...#...#.#.#...#
            #.#########.###.#.#.###
            #...###...#...#...#.###
            ###.###.#.###v#####v###
            #...#...#.#.>.>.#.>.###
            #.###.###.#.###.#.#v###
            #.....###...###...#...#
            #####################.#
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            TrailMap.parse(testInput)
        }
    }
}
