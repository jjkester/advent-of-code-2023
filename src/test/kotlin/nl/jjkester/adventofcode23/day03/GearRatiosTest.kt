package nl.jjkester.adventofcode23.day03

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day03.model.Schematic
import org.junit.jupiter.api.Test

class GearRatiosTest {

    @Test
    fun sumOfPartNumbers() {
        assertThat(GearRatios.sumOfPartNumbers(parsedTestInput))
            .isEqualTo(4361)
    }

    @Test
    fun sumOfGearRatios() {
        assertThat(GearRatios.sumOfGearRatios(parsedTestInput))
            .isEqualTo(467835)
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
