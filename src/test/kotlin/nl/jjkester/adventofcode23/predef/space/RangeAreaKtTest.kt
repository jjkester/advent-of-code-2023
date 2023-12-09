package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class RangeAreaKtTest {

    @Test
    fun `areaOf with ranges`() {
        assertThat(areaOf(10..15, 20..25))
            .isEqualTo(RangeArea(10..15, 20..25))
    }

    @Test
    fun `areaOf with integer and range`() {
        assertThat(areaOf(10, 20..25))
            .isEqualTo(RangeArea(10..10, 20..25))
    }

    @Test
    fun `areaOf with range and integer`() {
        assertThat(areaOf(10..15, 20))
            .isEqualTo(RangeArea(10..15, 20..20))
    }

    @Test
    fun `areaOf with integers`() {
        assertThat(areaOf(10, 20))
            .isEqualTo(RangeArea(10..10, 20..20))
    }

    @Test
    fun `scale when amount is positive then result is larger`() {
        val rangeArea = RangeArea(10..10, 20..20)

        assertThat(rangeArea.scale(2))
            .isEqualTo(RangeArea(8..12, 18..22))
    }

    @Test
    fun `scale when amount is zero then result is same`() {
        val rangeArea = RangeArea(10..10, 20..20)

        assertThat(rangeArea.scale(0))
            .isSameAs(rangeArea)
    }

    @Test
    fun `scale when amount is negative then result is smaller`() {
        val rangeArea = RangeArea(2..18, 12..28)

        assertThat(rangeArea.scale(-2))
            .isEqualTo(RangeArea(4..16, 14..26))
    }

    @Test
    fun `scale when amount is negative then result is empty`() {
        val rangeArea = RangeArea(10..10, 20..20)

        assertThat(rangeArea.scale(-1))
            .isEqualTo(RangeArea(IntRange.EMPTY, IntRange.EMPTY))
    }

    @ParameterizedTest
    @MethodSource("areasForCoerceIn")
    fun `coerceIn with area returns the adjusted rectangle`(coercion: Area, expectedResult: RangeArea) {
        assertThat(rangeAreaForCoerceIn.coerceIn(coercion))
            .isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @MethodSource("areasForCoerceIn")
    fun `coerceIn with distinct integers returns the adjusted rectangle`(coercion: Area, expectedResult: RangeArea) {
        assertThat(rangeAreaForCoerceIn.coerceIn(coercion.x, coercion.y))
            .isEqualTo(expectedResult)
    }

    companion object {
        val rangeAreaForCoerceIn = RangeArea(0..10, 10..20)

        @JvmStatic
        fun areasForCoerceIn() = arrayOf(
            Arguments.of(RangeArea(2..8, 15..15), RangeArea(2..8, 15..15)),
            Arguments.of(RangeArea(2..8, 0..15), RangeArea(2..8, 10..15)),
            Arguments.of(RangeArea(-10..0, 2..5), RangeArea(0..0, IntRange.EMPTY)),
            Arguments.of(RangeArea(IntRange.EMPTY, IntRange.EMPTY), RangeArea(IntRange.EMPTY, IntRange.EMPTY))
        )
    }
}
