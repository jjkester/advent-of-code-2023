package nl.jjkester.adventofcode23.day14

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day14.model.Platform
import org.junit.jupiter.api.Test

class ReflectorDishTest {

    @Test
    fun loadOnNorthBeams() {
        assertThat(ReflectorDish.loadOnNorthBeams(parsedTestInput))
            .isEqualTo(136)
    }

    @Test
    fun finalLoadOnNorthBeams() {
        assertThat(ReflectorDish.finalLoadOnNorthBeams(parsedTestInput))
            .isEqualTo(64)
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
    }
}
