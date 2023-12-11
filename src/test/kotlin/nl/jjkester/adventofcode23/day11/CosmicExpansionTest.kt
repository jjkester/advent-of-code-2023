package nl.jjkester.adventofcode23.day11

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day11.model.Image
import org.junit.jupiter.api.Test

class CosmicExpansionTest {

    @Test
    fun sumOfShortestPaths() {
        assertThat(CosmicExpansion.sumOfShortestPaths(parsedTestInput))
            .isEqualTo(374L)
    }

    @Test
    fun sumOfShortestPathsOlderGalaxy() {
        assertThat(CosmicExpansion.sumOfShortestPathsOlderGalaxy(parsedTestInput, 100))
            .isEqualTo(8410L)
    }

    companion object {
        private val testInput = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Image.parse(testInput)
        }
    }
}
