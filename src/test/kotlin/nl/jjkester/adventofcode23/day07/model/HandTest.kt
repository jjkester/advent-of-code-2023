package nl.jjkester.adventofcode23.day07.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class HandTest {

    @ParameterizedTest
    @MethodSource("compareTo")
    fun compareTo(first: Hand, second: Hand, expectedResult: Int) {
        assertThat(first.compareTo(second))
            .isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @MethodSource("value")
    fun value(hand: Hand, expected: Hand.Value) {
        assertThat(hand.value)
            .isEqualTo(expected)
    }

    companion object {

        @JvmStatic
        fun compareTo() = arrayOf(
            Arguments.of(Hand.parse("33332 0"), Hand.parse("2AAAA 0"), 1),
            Arguments.of(Hand.parse("33332 0"), Hand.parse("33332 1"), 0),
            Arguments.of(Hand.parse("JAAAK 0").withJoker(), Hand.parse("22223 0"), -1)
        )

        @JvmStatic
        fun value() = arrayOf(
            Arguments.of(Hand.parse("33332 0"), Hand.Value.FOUR_OF_A_KIND),
            Arguments.of(Hand.parse("2AAAA 0"), Hand.Value.FOUR_OF_A_KIND),
            Arguments.of(Hand.parse("77788 0"), Hand.Value.FULL_HOUSE),
            Arguments.of(Hand.parse("32T3K 0"), Hand.Value.ONE_PAIR),
            Arguments.of(Hand.parse("KK677 0"), Hand.Value.TWO_PAIR),
            Arguments.of(Hand.parse("KTJJT 0"), Hand.Value.TWO_PAIR),
            Arguments.of(Hand.parse("T55J5 0"), Hand.Value.THREE_OF_A_KIND),
            Arguments.of(Hand.parse("QQQJA 0"), Hand.Value.THREE_OF_A_KIND),
            Arguments.of(Hand.parse("TJQKA 0"), Hand.Value.HIGH_CARD),
            Arguments.of(Hand.parse("77777 0"), Hand.Value.FIVE_OF_A_KIND),
            Arguments.of(Hand.parse("32T3K 0").withJoker(), Hand.Value.ONE_PAIR),
            Arguments.of(Hand.parse("KK677 0").withJoker(), Hand.Value.TWO_PAIR),
            Arguments.of(Hand.parse("KTJJT 0").withJoker(), Hand.Value.FOUR_OF_A_KIND),
            Arguments.of(Hand.parse("T55J5 0").withJoker(), Hand.Value.FOUR_OF_A_KIND),
            Arguments.of(Hand.parse("QQQJA 0").withJoker(), Hand.Value.FOUR_OF_A_KIND),
            Arguments.of(Hand.parse("JAJAJ 0").withJoker(), Hand.Value.FIVE_OF_A_KIND),
            Arguments.of(Hand.parse("234JJ 0").withJoker(), Hand.Value.THREE_OF_A_KIND),
            Arguments.of(Hand.parse("TJQKA 0").withJoker(), Hand.Value.ONE_PAIR)
        )
    }
}
