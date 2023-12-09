package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class RangeVolumeTest {

    @ParameterizedTest
    @MethodSource("facings")
    fun getFacing(facing: Volume.Facing, expectedArea: RangeArea) {
        assertThat(rangeVolumeForFacings[facing])
            .isEqualTo(expectedArea)
    }

    companion object {
        private val rangeVolumeForFacings = RangeVolume(0..2, -2..0, -1..1)

        @JvmStatic
        fun facings() = arrayOf(
            Arguments.of(Volume.Facing.Top, RangeArea(0..2, -1..1)),
            Arguments.of(Volume.Facing.Front, RangeArea(0..2, -2..0)),
            Arguments.of(Volume.Facing.Left, RangeArea(-1..1, -2..0)),
            Arguments.of(Volume.Facing.Right, RangeArea(-1..1, -2..0)),
            Arguments.of(Volume.Facing.Back, RangeArea(0..2, -2..0)),
            Arguments.of(Volume.Facing.Bottom, RangeArea(0..2, -1..1))
        )
    }
}
