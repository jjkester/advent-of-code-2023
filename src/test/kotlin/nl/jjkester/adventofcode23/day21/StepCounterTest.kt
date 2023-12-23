package nl.jjkester.adventofcode23.day21

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day21.model.Garden
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class StepCounterTest {

    @ParameterizedTest
    @MethodSource("reachable")
    fun reachableGardenPlots(steps: Int, expected: Int) {
        assertThat(StepCounter.reachableGardenPlots(parsedTestInput, steps))
            .isEqualTo(expected)
    }

    companion object {
        private val testInput = """
            ...........
            .....###.#.
            .###.##..#.
            ..#.#...#..
            ....#.#....
            .##..S####.
            .##..#...#.
            .......##..
            .##.#.####.
            .##..##.##.
            ...........
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Garden.parse(testInput)
        }

        @JvmStatic
        fun reachable() = arrayOf(
            Arguments.of(6, 16),
            Arguments.of(10, 50),
            Arguments.of(50, 1594),
            Arguments.of(100, 6536)
        )
    }
}
