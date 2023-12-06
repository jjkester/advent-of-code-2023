package nl.jjkester.adventofcode23.day06

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day06.model.Race
import org.junit.jupiter.api.Test

class WaitForItTest {

    @Test
    fun totalMargin() {
        assertThat(WaitForIt.totalMargin(parsedTestInput))
            .isEqualTo(288)
    }

    @Test
    fun fixKerning() {
        assertThat(WaitForIt.fixKerning(parsedTestInput))
            .isEqualTo(Race(71530, 940200))
    }

    @Test
    fun competitiveOptions() {
        assertThat(WaitForIt.competitiveOptions(parsedTestInput))
            .isEqualTo(71503)
    }

    companion object {
        private val testInput = """
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Race.parse(testInput)
        }
    }
}
