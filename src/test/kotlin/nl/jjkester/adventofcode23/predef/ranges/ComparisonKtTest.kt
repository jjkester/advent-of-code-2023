package nl.jjkester.adventofcode23.predef.ranges

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ComparisonKtTest {

    private val baseRange = 13..42

    @ParameterizedTest
    @MethodSource("containedClosedRanges")
    fun `when a closed range is contained in another closed range, then contains returns true`(range: ClosedRange<Int>) {
        assertThat(range in baseRange).isTrue()
    }

    @ParameterizedTest
    @MethodSource("notContainedClosedRanges")
    fun `when a closed range is not contained in another closed range, then contains returns true`(range: ClosedRange<Int>) {
        assertThat(range in baseRange).isFalse()
    }

    @ParameterizedTest
    @MethodSource("overlappingClosedRanges")
    fun `when a closed range is overlapping another closed range, then overlaps returns true`(range: ClosedRange<Int>) {
        assertThat(range overlaps baseRange).isTrue()
    }

    @ParameterizedTest
    @MethodSource("notOverlappingClosedRanges")
    fun `when a closed range is not overlapping another closed range, then overlaps returns true`(range: ClosedRange<Int>) {
        assertThat(range overlaps baseRange).isFalse()
    }

    companion object {

        private val containedClosedRanges = arrayOf(
            20..40,
            33..33,
            14..41,
            13..42,
            13..13,
            42..42
        )

        private val oneSideOverlappingClosedRanges = arrayOf(
            7..33,
            7..13,
            33..69,
            42..69
        )

        private val containingClosedRanges = arrayOf(
            14..43,
            7..66
        )

        private val separateClosedRanges = arrayOf(
            0..7,
            69..99,
            7..12,
            43..69
        )

        @JvmStatic
        fun containedClosedRanges() = containedClosedRanges

        @JvmStatic
        fun notContainedClosedRanges() = oneSideOverlappingClosedRanges + containingClosedRanges + separateClosedRanges

        @JvmStatic
        fun overlappingClosedRanges() = containedClosedRanges + oneSideOverlappingClosedRanges + containingClosedRanges

        @JvmStatic
        fun notOverlappingClosedRanges() = separateClosedRanges
    }
}
