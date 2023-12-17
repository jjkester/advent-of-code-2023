package nl.jjkester.adventofcode23.day16.model

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.predef.space.by
import org.junit.jupiter.api.Test

class ContraptionTest {

    @Test
    fun parseToString() {
        assertThat(Contraption.parse(testInput))
            .transform { contraption -> contraption.toString() }
            .isEqualTo("""
                ·│···╲····
                │·─·╲·····
                ·····│─···
                ········│·
                ··········
                ·········╲
                ····╱·╲╲··
                ·─·─╱··│··
                ·│····─│·╲
                ··╱╱·│····
            """.trimIndent())
    }

    @Test
    fun energizedFrom() {
        assertThat(parsedTestInput.energizedFrom(0 by 0, Direction.East))
            .isEqualTo(46)
    }

    @Test
    fun edges() {
        assertThat(parsedTestInput.edges)
            .containsExactlyInAnyOrder(
                0 by 0 to Direction.East,
                0 by 1 to Direction.East,
                0 by 2 to Direction.East,
                0 by 3 to Direction.East,
                0 by 4 to Direction.East,
                0 by 5 to Direction.East,
                0 by 6 to Direction.East,
                0 by 7 to Direction.East,
                0 by 8 to Direction.East,
                0 by 9 to Direction.East,
                0 by 9 to Direction.North,
                1 by 9 to Direction.North,
                2 by 9 to Direction.North,
                3 by 9 to Direction.North,
                4 by 9 to Direction.North,
                5 by 9 to Direction.North,
                6 by 9 to Direction.North,
                7 by 9 to Direction.North,
                8 by 9 to Direction.North,
                9 by 9 to Direction.North,
                9 by 0 to Direction.West,
                9 by 1 to Direction.West,
                9 by 2 to Direction.West,
                9 by 3 to Direction.West,
                9 by 4 to Direction.West,
                9 by 5 to Direction.West,
                9 by 6 to Direction.West,
                9 by 7 to Direction.West,
                9 by 8 to Direction.West,
                9 by 9 to Direction.West,
                0 by 0 to Direction.South,
                1 by 0 to Direction.South,
                2 by 0 to Direction.South,
                3 by 0 to Direction.South,
                4 by 0 to Direction.South,
                5 by 0 to Direction.South,
                6 by 0 to Direction.South,
                7 by 0 to Direction.South,
                8 by 0 to Direction.South,
                9 by 0 to Direction.South
            )
    }

    companion object {
        private val testInput = """
            .|...\....
            |.-.\.....
            .....|-...
            ........|.
            ..........
            .........\
            ..../.\\..
            .-.-/..|..
            .|....-|.\
            ..//.|....
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Contraption.parse(testInput)
        }
    }
}
