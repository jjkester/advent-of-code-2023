package nl.jjkester.adventofcode23.day12

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class HotSpringsTest {

    @Test
    fun sumOfPossibilities() {
        assertThat(HotSprings.sumOfPossibilities(parsedTestInput))
            .isEqualTo(21L)
    }

    @Test
    fun sumOfPossibilitiesUnfolded() {
        assertThat(HotSprings.sumOfPossibilitiesUnfolded(parsedTestInput))
            .isEqualTo(525152L)
    }

    companion object {
        private val testInput = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            HotSprings.parseRecords(testInput)
        }
    }
}
