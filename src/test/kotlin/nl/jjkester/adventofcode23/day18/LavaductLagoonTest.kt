package nl.jjkester.adventofcode23.day18

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day18.model.DigPlan
import org.junit.jupiter.api.Test

class LavaductLagoonTest {

    @Test
    fun cubicMetersOfLava() {
        assertThat(LavaductLagoon.cubicMetersOfLava(parsedTestInput))
            .isEqualTo(62)
    }

    @Test
    fun cubicMetersOfLavaSwapped() {
        assertThat(LavaductLagoon.cubicMetersOfLavaSwapped(parsedTestInput))
            .isEqualTo(952408144115L)
    }

    companion object {
        private val testInput = """
            R 6 (#70c710)
            D 5 (#0dc571)
            L 2 (#5713f0)
            D 2 (#d2c081)
            R 2 (#59c680)
            D 2 (#411b91)
            L 5 (#8ceee2)
            U 2 (#caa173)
            L 1 (#1b58a2)
            U 2 (#caa171)
            R 2 (#7807d2)
            U 3 (#a77fa3)
            L 2 (#015232)
            U 2 (#7a21e3)
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            DigPlan.parse(testInput)
        }
    }
}
