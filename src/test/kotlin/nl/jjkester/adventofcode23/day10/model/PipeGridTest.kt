package nl.jjkester.adventofcode23.day10.model

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.single
import nl.jjkester.adventofcode23.predef.space.coordinateOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class PipeGridTest {

    @Test
    fun findLoops() {
        assertThat(parsedTestInput.findLoops().toList())
            .single()
            .containsExactly(
                PipeGrid.Step(coordinateOf(1, 1), Pipe.Start, Direction.East),
                PipeGrid.Step(coordinateOf(2, 1), Pipe.EastWest, Direction.East),
                PipeGrid.Step(coordinateOf(3, 1), Pipe.SouthWest, Direction.South),
                PipeGrid.Step(coordinateOf(3, 2), Pipe.NorthSouth, Direction.South),
                PipeGrid.Step(coordinateOf(3, 3), Pipe.NorthWest, Direction.West),
                PipeGrid.Step(coordinateOf(2, 3), Pipe.EastWest, Direction.West),
                PipeGrid.Step(coordinateOf(1, 3), Pipe.NorthEast, Direction.North),
                PipeGrid.Step(coordinateOf(1, 2), Pipe.NorthSouth, Direction.North),
                PipeGrid.Step(coordinateOf(1, 1), Pipe.Start, Direction.North)
            )
    }

    @Test
    fun findContainedAreas() {
        assertThat(parsedTestInput.findContainedAreas().toList())
            .single()
            .single()
            .isEqualTo(coordinateOf(2, 2))
    }

    @ParameterizedTest
    @MethodSource("walk")
    fun walk(direction: Direction, path: Array<PipeGrid.Step>) {
        assertThat(parsedTestInput.walk(direction))
            .containsExactly(*path)
    }

    companion object {
        private val testInput = """
            -L|F7
            7S-7|
            L|7||
            -L-J|
            L|-JF
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            PipeGrid.parse(testInput)
        }

        @JvmStatic
        fun walk() = arrayOf(
            Arguments.of(
                Direction.South,
                arrayOf(
                    PipeGrid.Step(coordinateOf(1, 1), Pipe.Start, Direction.South),
                    PipeGrid.Step(coordinateOf(1, 2), Pipe.NorthSouth, Direction.South),
                    PipeGrid.Step(coordinateOf(1, 3), Pipe.NorthEast, Direction.East),
                    PipeGrid.Step(coordinateOf(2, 3), Pipe.EastWest, Direction.East),
                    PipeGrid.Step(coordinateOf(3, 3), Pipe.NorthWest, Direction.North),
                    PipeGrid.Step(coordinateOf(3, 2), Pipe.NorthSouth, Direction.North),
                    PipeGrid.Step(coordinateOf(3, 1), Pipe.SouthWest, Direction.West),
                    PipeGrid.Step(coordinateOf(2, 1), Pipe.EastWest, Direction.West),
                    PipeGrid.Step(coordinateOf(1, 1), Pipe.Start, Direction.West)
                )
            ),
            Arguments.of(
                Direction.East,
                arrayOf(
                    PipeGrid.Step(coordinateOf(1, 1), Pipe.Start, Direction.East),
                    PipeGrid.Step(coordinateOf(2, 1), Pipe.EastWest, Direction.East),
                    PipeGrid.Step(coordinateOf(3, 1), Pipe.SouthWest, Direction.South),
                    PipeGrid.Step(coordinateOf(3, 2), Pipe.NorthSouth, Direction.South),
                    PipeGrid.Step(coordinateOf(3, 3), Pipe.NorthWest, Direction.West),
                    PipeGrid.Step(coordinateOf(2, 3), Pipe.EastWest, Direction.West),
                    PipeGrid.Step(coordinateOf(1, 3), Pipe.NorthEast, Direction.North),
                    PipeGrid.Step(coordinateOf(1, 2), Pipe.NorthSouth, Direction.North),
                    PipeGrid.Step(coordinateOf(1, 1), Pipe.Start, Direction.North)
                )
            ),
            Arguments.of(
                Direction.North,
                arrayOf(PipeGrid.Step(coordinateOf(1, 1), Pipe.Start, Direction.North))
            )
        )
    }
}
