package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class RangeVolumeKtTest {

    @Test
    fun `volumeOf with ranges`() {
        assertThat(volumeOf(10..15, 20..25, 30..35))
            .isEqualTo(RangeVolume(10..15, 20..25, 30..35))
    }

    @ParameterizedTest
    @MethodSource("cornersForVolumeOf")
    fun `volumeOf with coordinates`(first: Coordinate3D, second: Coordinate3D) {
        assertThat(volumeOf(first, second))
            .isEqualTo(RangeVolume(10..15, 20..25, 30..35))
    }

    companion object {

        @JvmStatic
        fun cornersForVolumeOf() = arrayOf(
            Arguments.of(coordinateOf(10, 20, 30), coordinateOf(15, 25, 35)),
            Arguments.of(coordinateOf(15, 20, 30), coordinateOf(10, 25, 35)),
            Arguments.of(coordinateOf(10, 25, 30), coordinateOf(15, 20, 35)),
            Arguments.of(coordinateOf(10, 20, 35), coordinateOf(15, 25, 30)),
            Arguments.of(coordinateOf(15, 25, 30), coordinateOf(10, 20, 35)),
            Arguments.of(coordinateOf(10, 25, 35), coordinateOf(15, 20, 30)),
            Arguments.of(coordinateOf(15, 20, 35), coordinateOf(10, 25, 30)),
            Arguments.of(coordinateOf(15, 25, 35), coordinateOf(10, 20, 30))
        )
    }
}
