package nl.jjkester.adventofcode23.day07

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class CamelCardsTest {

    @Test
    fun totalWinnings() {
        assertThat(CamelCards.totalWinnings(parsedTestInput))
            .isEqualTo(6440L)
    }

    @Test
    fun totalWinningsWithJoker() {
        assertThat(CamelCards.totalWinningsWithJoker(parsedTestInput))
            .isEqualTo(5905L)
    }

    companion object {
        private val testInput = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            CamelCards.parseHands(testInput)
        }
    }
}
