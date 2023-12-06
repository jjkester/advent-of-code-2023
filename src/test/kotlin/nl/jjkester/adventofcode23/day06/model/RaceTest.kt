package nl.jjkester.adventofcode23.day06.model

import assertk.assertThat
import assertk.assertions.containsExactly
import org.junit.jupiter.api.Test

class RaceTest {

    @Test
    fun parse() {
        assertThat(Race.parse(testInput))
            .containsExactly(
                Race(7, 9),
                Race(15, 40),
                Race(30, 200)
            )
    }

    @Test
    fun competitiveOptions() {
        assertThat(Race(7, 9).competitiveOptions())
            .containsExactly(2, 3, 4, 5)
    }

    companion object {
        private val testInput = """
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent()
    }
}
