package nl.jjkester.adventofcode23.day20.model

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsOnly
import assertk.assertions.extracting
import assertk.assertions.isEmpty
import org.junit.jupiter.api.Test

class ControlSystemTest {

    @Test
    fun `repeating button presses in infinite cycle`() {
        val testInput = """
                broadcaster -> a, b, c
                %a -> b
                %b -> c
                %c -> inv
                &inv -> a
            """.trimIndent()

        val systemUnderTest = ControlSystem.parse(testInput)

        assertThat(systemUnderTest.counts)
            .isEmpty()

        systemUnderTest.pressButton()

        assertThat(systemUnderTest.counts)
            .containsOnly(
                Pulse.Low to 8,
                Pulse.High to 4
            )

        repeat(99) {
            systemUnderTest.pressButton()
        }

        assertThat(systemUnderTest.counts)
            .containsOnly(
                Pulse.Low to 800,
                Pulse.High to 400
            )
    }

    @Test
    fun `multiple button presses with different outputs`() {
        val testInput = """
            broadcaster -> a
            %a -> inv, con
            &inv -> b
            %b -> con
            &con -> output
        """.trimIndent()

        val systemUnderTest = ControlSystem.parse(testInput)

        systemUnderTest.pressButton()

        assertThat(systemUnderTest.counts)
            .containsOnly(
                Pulse.Low to 4,
                Pulse.High to 4
            )

        systemUnderTest.pressButton()

        assertThat(systemUnderTest.counts)
            .containsOnly(
                Pulse.Low to 8,
                Pulse.High to 6
            )

        systemUnderTest.pressButton()

        assertThat(systemUnderTest.counts)
            .containsOnly(
                Pulse.Low to 13,
                Pulse.High to 9
            )

        systemUnderTest.pressButton()

        assertThat(systemUnderTest.counts)
            .containsOnly(
                Pulse.Low to 17,
                Pulse.High to 11
            )
    }

    @Test
    fun conjunctionsForRx() {
        val testInput = """
            broadcaster -> a, g
            &a -> b
            &b -> f, e
            &c -> f
            &d -> e
            &e -> d
            &f -> rx
            %g -> c
        """.trimIndent()

        val systemUnderTest = ControlSystem.parse(testInput)

        assertThat(systemUnderTest.conjunctionsForRx())
            .extracting { it.name.value }
            .containsExactly("b", "c")
    }

}
