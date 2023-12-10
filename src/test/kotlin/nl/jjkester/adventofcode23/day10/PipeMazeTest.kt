package nl.jjkester.adventofcode23.day10

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day10.model.PipeGrid
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class PipeMazeTest {

    @ParameterizedTest
    @MethodSource("stepsToFarthestPoint")
    fun stepsToFarthestPoint(testInput: String, expectedResult: Int) {
        val parsedTestInput = PipeGrid.parse(testInput)

        assertThat(PipeMaze.stepsToFarthestPoint(parsedTestInput))
            .isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @MethodSource("pointsEnclosedByLoop")
    fun pointsEnclosedByLoop(testInput: String, expectedResult: Int) {
        val parsedTestInput = PipeGrid.parse(testInput)

        assertThat(PipeMaze.pointsEnclosedByLoop(parsedTestInput))
            .isEqualTo(expectedResult)
    }

    companion object {

        @JvmStatic
        fun stepsToFarthestPoint() = arrayOf(
            Arguments.of(
                """
                    .....
                    .S-7.
                    .|.|.
                    .L-J.
                    .....
                """.trimIndent(),
                4
            ),
            Arguments.of(
                """
                    7-F7-
                    .FJ|7
                    SJLL7
                    |F--J
                    LJ.LJ
                """.trimIndent(),
                8
            )
        )

        @JvmStatic
        fun pointsEnclosedByLoop() = arrayOf(
            Arguments.of(
                """
                    ...........
                    .S-------7.
                    .|F-----7|.
                    .||.....||.
                    .||.....||.
                    .|L-7.F-J|.
                    .|..|.|..|.
                    .L--J.L--J.
                    ...........
                """.trimIndent(),
                4
            ),
            Arguments.of(
                """
                    .F----7F7F7F7F-7....
                    .|F--7||||||||FJ....
                    .||.FJ||||||||L7....
                    FJL7L7LJLJ||LJ.L-7..
                    L--J.L7...LJS7F-7L7.
                    ....F-J..F7FJ|L7L7L7
                    ....L7.F7||L7|.L7L7|
                    .....|FJLJ|FJ|F7|.LJ
                    ....FJL-7.||.||||...
                    ....L---J.LJ.LJLJ...
                """.trimIndent(),
                8
            ),
            Arguments.of(
                """
                    FF7FSF7F7F7F7F7F---7
                    L|LJ||||||||||||F--J
                    FL-7LJLJ||||||LJL-77
                    F--JF--7||LJLJ7F7FJ-
                    L---JF-JLJ.||-FJLJJ7
                    |F|F-JF---7F7-L7L|7|
                    |FFJF7L7F-JF7|JL---7
                    7-L-JL7||F7|L7F-7F7|
                    L.L7LFJ|||||FJL7||LJ
                    L7JLJL-JLJLJL--JLJ.L
                """.trimIndent(),
                10
            )
        )
    }
}
