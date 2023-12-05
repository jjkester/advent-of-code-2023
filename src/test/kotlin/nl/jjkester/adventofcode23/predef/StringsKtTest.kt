package nl.jjkester.adventofcode23.predef

import assertk.assertThat
import assertk.assertions.containsExactly
import org.junit.jupiter.api.Test

class StringsKtTest {

    @Test
    fun sections() {
        assertThat(testInput.sections())
            .containsExactly(
                "",
                "First section",
                "Second section",
                "Third section${System.lineSeparator()}"
            )
    }

    @Test
    fun sectionSequence() {
        assertThat(testInput.sectionSequence())
            .containsExactly(
                "",
                "First section",
                "Second section",
                "Third section${System.lineSeparator()}"
            )
    }

    companion object {
        private val testInput = """
            
            
            First section
            
            Second section
            
            
            Third section
            
        """.trimIndent()
    }
}
