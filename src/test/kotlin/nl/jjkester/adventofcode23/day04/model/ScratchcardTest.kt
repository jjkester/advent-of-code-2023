package nl.jjkester.adventofcode23.day04.model

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.api.Test

class ScratchcardTest {

    @Test
    fun parse() {
        assertThat(Scratchcard.parse(testInput)).all {
            prop(Scratchcard::id).isEqualTo(3)
            prop(Scratchcard::winningNumbers).containsExactlyInAnyOrder(1, 21, 53, 59, 44)
            prop(Scratchcard::scratchedNumbers).containsExactlyInAnyOrder(69, 82, 63, 72, 16, 21, 14, 1)
        }
    }

    @Test
    fun points() {
        assertThat(parsedTestInput.points).isEqualTo(2)
    }

    companion object {
        private val testInput = """Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"""

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Scratchcard.parse(testInput)
        }
    }
}
