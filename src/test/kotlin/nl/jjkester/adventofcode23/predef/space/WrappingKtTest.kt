package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class WrappingKtTest {

    private val area = RangeArea(0..9, 5..9)
    private val volume = RangeVolume(0..9, 5..9, -1..-1)

    @ParameterizedTest
    @MethodSource("noWrap2d")
    fun `wrapIn when coordinate is already in area`(coordinate: Coordinate2D) {
        assertThat(area.wrap(coordinate))
            .isEqualTo(coordinate)
    }

    @ParameterizedTest
    @MethodSource("wrap2d")
    fun `wrapIn when coordinate is outside the area`(coordinate: Coordinate2D, wrapped: Coordinate2D) {
        assertThat(area.wrap(coordinate))
            .isEqualTo(wrapped)
    }

    @ParameterizedTest
    @MethodSource("noWrap3d")
    fun `wrapIn when coordinate is already in volume`(coordinate: Coordinate3D) {
        assertThat(volume.wrap(coordinate))
            .isEqualTo(coordinate)
    }

    @ParameterizedTest
    @MethodSource("wrap3d")
    fun `wrapIn when coordinate is outside the volume`(coordinate: Coordinate3D, wrapped: Coordinate3D) {
        assertThat(volume.wrap(coordinate))
            .isEqualTo(wrapped)
    }

    companion object {

        @JvmStatic
        fun noWrap2d() = arrayOf(
            0 by 5,
            0 by 9,
            9 by 5,
            9 by 9,
            4 by 7
        )

        @JvmStatic
        fun noWrap3d() = arrayOf(
            0 by 5 by -1,
            0 by 9 by -1,
            9 by 5 by -1,
            9 by 9 by -1,
            4 by 7 by -1
        )

        @JvmStatic
        fun wrap2d() = arrayOf(
            Arguments.of(10 by 10, 0 by 5),
            Arguments.of(-1 by 5, 9 by 5),
            Arguments.of(0 by 4, 0 by 9),
            Arguments.of(-1 by 4, 9 by 9),
            Arguments.of(-10 by 0, 0 by 5),
            Arguments.of(-5 by 2, 5 by 7)
        )

        @JvmStatic
        fun wrap3d() = arrayOf(
            Arguments.of(10 by 10 by 0, 0 by 5 by -1),
            Arguments.of(-1 by 5 by -2, 9 by 5 by -1),
            Arguments.of(0 by 4 by -3, 0 by 9 by -1),
            Arguments.of(-1 by 4 by 8, 9 by 9 by -1),
            Arguments.of(-10 by 0 by -1, 0 by 5 by -1),
            Arguments.of(-5 by 2 by -8, 5 by 7 by -1)
        )
    }
}
