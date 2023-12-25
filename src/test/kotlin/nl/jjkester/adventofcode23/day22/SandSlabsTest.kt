package nl.jjkester.adventofcode23.day22

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day22.model.Snapshot
import org.junit.jupiter.api.Test

class SandSlabsTest {

    @Test
    fun safeToDisintegrate() {
        assertThat(SandSlabs.safeToDisintegrate(parsedTestInput))
            .isEqualTo(5)
    }

    @Test
    fun sumOfFallingBricks() {
        assertThat(SandSlabs.sumOfFallingBricks(parsedTestInput))
            .isEqualTo(7)
    }

    companion object {
        private val testInput = """
            1,0,1~1,2,1
            0,0,2~2,0,2
            0,2,3~2,2,3
            0,0,4~0,2,4
            2,0,5~2,2,5
            0,1,6~2,1,6
            1,1,8~1,1,9
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Snapshot.parse(testInput)
        }
    }
}
