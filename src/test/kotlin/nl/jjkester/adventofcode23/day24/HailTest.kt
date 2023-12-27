package nl.jjkester.adventofcode23.day24

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class HailTest {

    @Test
    fun intersections() {
        assertThat(Hail.intersections(parsedTestInput, testRange))
            .isEqualTo(2)
    }

    companion object {
        private val testInput = """
            19, 13, 30 @ -2,  1, -2
            18, 19, 22 @ -1, -1, -2
            20, 25, 34 @ -2, -2, -4
            12, 31, 28 @ -1, -2, -1
            20, 19, 15 @  1, -5, -3
        """.trimIndent()

        private val testRange = 7.0..27.0

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Hail.parseHailstones(testInput)
        }
    }
}
