package nl.jjkester.adventofcode23.predef

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ProgressionsKtTest {

    @ParameterizedTest
    @MethodSource("intProgressions")
    fun `size of an IntProgression is calculated correctly`(progression: IntProgression, expectedSize: Int) {
        assertThat(progression.size)
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("uIntProgressions")
    fun `size of an UIntProgression is calculated correctly`(progression: UIntProgression, expectedSize: UInt) {
        assertThat(progression.size)
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("longProgressions")
    fun `size of a LongProgression is calculated correctly`(progression: LongProgression, expectedSize: Long) {
        assertThat(progression.size)
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("uLongProgressions")
    fun `size of an ULongProgression is calculated correctly`(progression: ULongProgression, expectedSize: ULong) {
        assertThat(progression.size)
            .isEqualTo(expectedSize)
    }

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

        @Suppress("EmptyRange")
        @JvmStatic
        fun intProgressions() = arrayOf(
            Arguments.of(1..8 step 3, 3),
            Arguments.of(10..100 step 10, 10),
            Arguments.of(1..0 step 10, 0),
            Arguments.of(10..-100 step 7, 0),
            Arguments.of(8 downTo 1, 8),
            Arguments.of(100 downTo 10 step 10, 10),
            Arguments.of(1..1, 1)
        )

        @Suppress("EmptyRange")
        @JvmStatic
        fun uIntProgressions() = arrayOf(
            Arguments.of(1u..8u step 3, 3),
            Arguments.of(10u..100u step 10, 10),
            Arguments.of(1u..0u step 10, 0),
            Arguments.of(100u..10u step 7, 0),
            Arguments.of(8u downTo 1u, 8),
            Arguments.of(100u downTo 10u step 10, 10),
            Arguments.of(1u..1u, 1)
        )

        @Suppress("EmptyRange")
        @JvmStatic
        fun longProgressions() = arrayOf(
            Arguments.of(1L..8L step 3, 3),
            Arguments.of(10L..100L step 10, 10),
            Arguments.of(1L..0L step 10, 0),
            Arguments.of(10L..-100L step 7, 0),
            Arguments.of(8L downTo 1L, 8),
            Arguments.of(100L downTo 10L step 10, 10),
            Arguments.of(1L..1L, 1)
        )

        @Suppress("EmptyRange")
        @JvmStatic
        fun uLongProgressions() = arrayOf(
            Arguments.of(1uL..8uL step 3, 3),
            Arguments.of(10uL..100uL step 10, 10),
            Arguments.of(1uL..0uL step 10, 0),
            Arguments.of(100uL..10uL step 7, 0),
            Arguments.of(8uL downTo 1uL, 8),
            Arguments.of(100uL downTo 10uL step 10, 10),
            Arguments.of(1uL..1uL, 1)
        )

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
