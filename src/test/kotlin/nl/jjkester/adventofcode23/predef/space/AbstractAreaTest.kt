package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class AbstractAreaTest {

    @ParameterizedTest
    @MethodSource("areasForSize")
    fun `area size is calculated based on the x and y ranges`(area: Area, expectedSize: Int) {
        assertThat(area.size)
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("areasForSize")
    fun `when area size is zero, then isEmpty is true`(area: Area, expectedSize: Int) {
        assertThat(area.isEmpty())
            .isEqualTo(expectedSize == 0)
    }

    @ParameterizedTest
    @MethodSource("coveredCoordinatesForContains")
    fun `when area covers coordinate, contains returns true`(x: Int, y: Int) {
        assertThat(areaForContains.contains(x, y))
            .isTrue()
    }

    @ParameterizedTest
    @MethodSource("notCoveredCoordinatesForContains")
    fun `when area does not cover coordinate, contains returns true`(x: Int, y: Int) {
        assertThat(areaForContains.contains(x, y))
            .isFalse()
    }

    companion object {
        private val areaForContains = RangeArea(2..6, 12..16)

        @JvmStatic
        fun areasForSize() = arrayOf(
            Arguments.of(TestArea(-2..0, 0..2), 9),
            Arguments.of(TestArea(-2..2, -2..2), 25),
            Arguments.of(TestArea(0..2, 0..2), 9),
            Arguments.of(TestArea(IntRange.EMPTY, 0..2), 0),
            Arguments.of(TestArea(0..2, IntRange.EMPTY), 0),
        )

        @JvmStatic
        fun coveredCoordinatesForContains() = arrayOf(
            Arguments.of(2, 12),
            Arguments.of(2, 16),
            Arguments.of(6, 12),
            Arguments.of(6, 16),
            Arguments.of(4, 14)
        )

        @JvmStatic
        fun notCoveredCoordinatesForContains() = arrayOf(
            Arguments.of(1, 12),
            Arguments.of(2, 11),
            Arguments.of(1, 11),
            Arguments.of(7, 16),
            Arguments.of(6, 17),
            Arguments.of(7, 17),
            Arguments.of(0, 0)
        )
    }

    private class TestArea(override val x: IntRange, override val y: IntRange) : AbstractArea()
}
