package nl.jjkester.adventofcode23.predef.ranges

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CoercionKtTest {

    @ParameterizedTest
    @MethodSource("intRangeCoercions")
    fun `coercion of an IntRange is done correctly`(coercion: IntRange, expectedOutcome: IntRange) {
        val range = 0..10
        assertThat(range.coerceIn(coercion))
            .isEqualTo(expectedOutcome)
    }

    @ParameterizedTest
    @MethodSource("intRangeCoercions")
    fun `coercion of an IntProgression is done correctly`(coercion: IntProgression, expectedOutcome: IntProgression) {
        val range = 0..10
        assertThat(range.coerceIn(coercion))
            .isEqualTo(expectedOutcome)
    }

    @ParameterizedTest
    @MethodSource("longRangeCoercions")
    fun `coercion of an LongRange is done correctly`(coercion: LongRange, expectedOutcome: LongRange) {
        val range = 0L..10L
        assertThat(range.coerceIn(coercion))
            .isEqualTo(expectedOutcome)
    }

    @ParameterizedTest
    @MethodSource("longRangeCoercions")
    fun `coercion of an LongProgression is done correctly`(
        coercion: LongProgression,
        expectedOutcome: LongProgression
    ) {
        val range = 0L..10L
        assertThat(range.coerceIn(coercion))
            .isEqualTo(expectedOutcome)
    }

    companion object {

        @JvmStatic
        fun intRangeCoercions() = arrayOf(
            Arguments.of(0..2, 0..2),
            Arguments.of(8..10, 8..10),
            Arguments.of(2..8, 2..8),
            Arguments.of(-10..5, 0..5),
            Arguments.of(5..20, 5..10),
            Arguments.of(-10..20, 0..10),
            Arguments.of(-10..0, 0..0),
            Arguments.of(10..20, 10..10),
            Arguments.of(20..100, IntRange.EMPTY)
        )

        @JvmStatic
        fun longRangeCoercions() = arrayOf(
            Arguments.of(0L..2L, 0L..2L),
            Arguments.of(8L..10L, 8L..10L),
            Arguments.of(2L..8L, 2L..8L),
            Arguments.of(-10L..5L, 0L..5L),
            Arguments.of(5L..20L, 5L..10L),
            Arguments.of(-10L..20L, 0L..10L),
            Arguments.of(-10L..0L, 0L..0L),
            Arguments.of(10L..20L, 10L..10L),
            Arguments.of(20L..100L, LongRange.EMPTY)
        )
    }
}
