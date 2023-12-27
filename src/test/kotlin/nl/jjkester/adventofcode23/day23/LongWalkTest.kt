package nl.jjkester.adventofcode23.day23

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day23.model.TrailMap
import org.junit.jupiter.api.Test

class LongWalkTest {

    @Test
    fun longestHike() {
        assertThat(LongWalk.longestHike(parsedTestInput))
            .isEqualTo(94)
    }

    @Test
    fun longestHikeWithoutSlopes() {
        assertThat(LongWalk.longestHikeWithoutSlopes(parsedTestInput))
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
