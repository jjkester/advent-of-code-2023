package nl.jjkester.adventofcode23.day13

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class IncidenceTest {

    @Test
    fun summarizedNumber() {
        assertThat(Incidence.summarizedNumber(parsedTestInput))
            .isEqualTo(405L)
    }

    @Test
    fun alternativeSummarizedNumber() {
        assertThat(Incidence.alternativeSummarizedNumber(parsedTestInput))
            .isEqualTo(400L)
    }

    companion object {
        private val testInput = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
            
            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Incidence.parseAreas(testInput)
        }
    }
}
