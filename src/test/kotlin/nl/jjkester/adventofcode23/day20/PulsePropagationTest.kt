package nl.jjkester.adventofcode23.day20

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class PulsePropagationTest {

    @Test
    fun pulsesAfter1000Presses() {
        assertThat(PulsePropagation.pulsesAfter1000Presses(parsedTestInput))
            .isEqualTo(11687500)
    }

    companion object {
        private val testInput = """
            broadcaster -> a
            %a -> inv, con
            &inv -> b
            %b -> con
            &con -> output
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            PulsePropagation.parseInput(testInput)
        }
    }
}
