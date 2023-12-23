package nl.jjkester.adventofcode23.day21.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.predef.space.Coordinate2D
import nl.jjkester.adventofcode23.predef.space.by
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class GardenTest {

    @ParameterizedTest
    @MethodSource("reachable")
    fun reachableInPrecise(steps: Int, coordinates: Set<Coordinate2D>) {
        assertThat(parsedTestInput.reachableInPrecise(steps))
            .isEqualTo(coordinates.size.toLong())
    }

    @ParameterizedTest
    @MethodSource("reachable")
    fun reachableAfterSteps(steps: Int, coordinates: Set<Coordinate2D>) {
        assertThat(parsedTestInput.reachableAfterSteps().drop(steps).first())
            .isEqualTo(coordinates)
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
            Arguments.of(0, setOf(5 by 5)),
            Arguments.of(1, setOf(4 by 5, 5 by 4)),
            Arguments.of(2, setOf(5 by 5, 3 by 5, 5 by 3, 4 by 6))
        )
    }
}
