package nl.jjkester.adventofcode23.day09

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class MirageMaintenanceTest {

    @Test
    fun sumOfNextValues() {
        assertThat(MirageMaintenance.sumOfNextValues(parsedTestInput))
            .isEqualTo(114)
    }

    @Test
    fun sumOfPreviousValues() {
        assertThat(MirageMaintenance.sumOfPreviousValues(parsedTestInput))
            .isEqualTo(2)
    }

    companion object {
        private val testInput = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            MirageMaintenance.parseSequences(testInput)
        }
    }
}
