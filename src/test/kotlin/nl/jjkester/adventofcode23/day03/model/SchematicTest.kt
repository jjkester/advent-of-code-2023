package nl.jjkester.adventofcode23.day03.model

import assertk.assertThat
import assertk.assertions.containsExactly
import nl.jjkester.adventofcode23.predef.space.D2Coordinate
import nl.jjkester.adventofcode23.predef.space.Rectangle
import org.junit.jupiter.api.Test

class SchematicTest {

    @Test
    fun parse() {
        val expectedChars = testInput.lines().joinToString("").toCharArray()

        assertThat(Schematic.parse(testInput))
            .transform {
                sequence {
                    for (y in 0..9) {
                        for (x in 0..9) {
                            yield(it[x, y])
                        }
                    }
                }
            }
            .containsExactly(*expectedChars.toTypedArray())
    }

    @Test
    fun findNumbers() {
        assertThat(parsedTestInput.findNumbers())
            .containsExactly(
                Rectangle(0..2, 0) to 467,
                Rectangle(5..7, 0) to 114,
                Rectangle(2..3, 2) to 35,
                Rectangle(6..8, 2) to 633,
                Rectangle(0..2, 4) to 617,
                Rectangle(7..8, 5) to 58,
                Rectangle(2..4, 6) to 592,
                Rectangle(6..8, 7) to 755,
                Rectangle(1..3, 9) to 664,
                Rectangle(5..7, 9) to 598
            )
    }

    @Test
    fun findPartNumbers() {
        assertThat(parsedTestInput.findPartNumbers())
            .containsExactly(467, 35, 633, 617, 592, 755, 664, 598)
    }

    @Test
    fun findStars() {
        assertThat(parsedTestInput.findStars())
            .containsExactly(
                D2Coordinate(3, 1),
                D2Coordinate(3, 4),
                D2Coordinate(5, 8)
            )
    }

    @Test
    fun findGears() {
        assertThat(parsedTestInput.findGears())
            .containsExactly(
                467 to 35,
                755 to 598
            )
    }

    companion object {
        private val testInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Schematic.parse(testInput)
        }
    }
}
