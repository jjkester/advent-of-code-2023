package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class AbstractVolumeTest {

    @ParameterizedTest
    @MethodSource("volumesForSize")
    fun `volume size is calculated based on the x and y ranges`(volume: Volume, expectedSize: Int) {
        assertThat(volume.size)
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("volumesForSize")
    fun `when volume size is zero, then isEmpty is true`(volume: Volume, expectedSize: Int) {
        assertThat(volume.isEmpty())
            .isEqualTo(expectedSize == 0)
    }

    @ParameterizedTest
    @MethodSource("coveredCoordinatesForContains")
    fun `when volume covers coordinate, contains returns true`(x: Int, y: Int, z: Int) {
        assertThat(volumeForContains.contains(x, y, z))
            .isTrue()
    }

    @ParameterizedTest
    @MethodSource("notCoveredCoordinatesForContains")
    fun `when volume does not cover coordinate, contains returns true`(x: Int, y: Int, z: Int) {
        assertThat(volumeForContains.contains(x, y, z))
            .isFalse()
    }

    companion object {
        private val volumeForContains = RangeVolume(2..6, 12..16, -6..-2)

        @JvmStatic
        fun volumesForSize() = arrayOf(
            Arguments.of(TestVolume(-2..0, 0..2, -1..1), 27),
            Arguments.of(TestVolume(-2..2, -2..2, -2..2), 125),
            Arguments.of(TestVolume(0..2, 0..2, 0..2), 27),
            Arguments.of(TestVolume(IntRange.EMPTY, 0..2, 0..2), 0),
            Arguments.of(TestVolume(0..2, IntRange.EMPTY, 0..2), 0),
            Arguments.of(TestVolume(0..2, 0..2, IntRange.EMPTY), 0),
        )

        @JvmStatic
        fun coveredCoordinatesForContains() = arrayOf(
            Arguments.of(2, 12, -2),
            Arguments.of(2, 12, -6),
            Arguments.of(2, 16, -2),
            Arguments.of(2, 16, -6),
            Arguments.of(6, 12, -2),
            Arguments.of(6, 12, -6),
            Arguments.of(6, 16, -2),
            Arguments.of(6, 16, -6),
            Arguments.of(4, 14, -4)
        )

        @JvmStatic
        fun notCoveredCoordinatesForContains() = arrayOf(
            Arguments.of(1, 12, -2),
            Arguments.of(2, 11, -2),
            Arguments.of(2, 12, -1),
            Arguments.of(1, 11, -1),
            Arguments.of(7, 16, -6),
            Arguments.of(6, 17, -6),
            Arguments.of(6, 16, -7),
            Arguments.of(7, 17, -7),
            Arguments.of(0, 0, 0)
        )
    }

    private class TestVolume(
        override val x: IntRange,
        override val y: IntRange,
        override val z: IntRange
    ) : AbstractVolume() {

        override fun get(facing: Volume.Facing): Area {
            throw NotImplementedError()
        }
    }
}
