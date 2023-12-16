package nl.jjkester.adventofcode23.day14.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class PlatformTest {

    @Test
    fun tiltNorth() {
        val expected = """
            OOOO.#.O..
            OO..#....#
            OO..O##..O
            O..#.OO...
            ........#.
            ..#....#.#
            ..O..#.O.O
            ..O.......
            #....###..
            #....#....
        """.trimIndent()

        assertThat(parsedTestInput.tiltNorth())
            .isEqualTo(Platform.parse(expected))
    }

    @Test
    fun northBeamLoad() {
        assertThat(parsedTestInput.tiltNorth().northBeamLoad)
            .isEqualTo(136)
    }

    @ParameterizedTest
    @MethodSource("cycles")
    fun spinCycle(cycles: Int, expected: Platform) {
        val result = List(cycles) { null }.fold(parsedTestInput) { previous, _ -> previous.spinCycle() }

        assertThat(result)
            .isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(longs = [13L, 20L, 1000000000L])
    fun spinCycles(number: Long) {
        val expected = """
            .....#....
            ....#...O#
            .....##...
            ...#......
            .....OOO#.
            .O#...O#.#
            ....O#...O
            ......OOOO
            #....###.O
            #.OOO#..OO
        """.trimIndent()

        assertThat(parsedTestInput.spinCycles(number))
            .isEqualTo(Platform.parse(expected))
    }

    @Test
    fun oneSpinCycles() {
        assertThat(parsedTestInput.spinCycles(1))
            .isEqualTo(parsedTestInput.spinCycle())
    }

    companion object {
        private val testInput = """
            O....#....
            O.OO#....#
            .....##...
            OO.#O....O
            .O.....O#.
            O.#..O.#.#
            ..O..#O..O
            .......O..
            #....###..
            #OO..#....
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Platform.parse(testInput)
        }

        @JvmStatic
        fun cycles() = arrayOf(
            Arguments.of(
                1,
                Platform.parse(
                    """
                        .....#....
                        ....#...O#
                        ...OO##...
                        .OO#......
                        .....OOO#.
                        .O#...O#.#
                        ....O#....
                        ......OOOO
                        #...O###..
                        #..OO#....
                    """.trimIndent()
                ),
                2,
                Platform.parse(
                    """
                        .....#....
                        ....#...O#
                        .....##...
                        ..O#......
                        .....OOO#.
                        .O#...O#.#
                        ....O#...O
                        .......OOO
                        #..OO###..
                        #.OOO#...O
                    """.trimIndent()
                ),
                3,
                Platform.parse(
                    """
                        .....#....
                        ....#...O#
                        .....##...
                        ..O#......
                        .....OOO#.
                        .O#...O#.#
                        ....O#...O
                        .......OOO
                        #...O###.O
                        #.OOO#...O
                    """.trimIndent()
                )
            )
        )
    }
}
