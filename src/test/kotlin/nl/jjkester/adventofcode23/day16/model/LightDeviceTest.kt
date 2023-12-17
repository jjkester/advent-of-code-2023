package nl.jjkester.adventofcode23.day16.model

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class LightDeviceTest {

    @ParameterizedTest
    @MethodSource("mapping")
    fun parse(input: Char, output: LightDevice) {
        assertThat(LightDevice.parse(input))
            .isEqualTo(output)
    }

    @ParameterizedTest
    @ValueSource(chars = [' ', '*', '#', 'O'])
    fun parseError(input: Char) {
        assertFailure { LightDevice.parse(input) }
            .isInstanceOf<IllegalArgumentException>()
    }

    @ParameterizedTest
    @MethodSource("apply")
    fun apply(lightDevice: LightDevice, direction: Direction, expected: Array<Direction>) {
        assertThat(lightDevice.apply(direction))
            .containsExactlyInAnyOrder(*expected)
    }

    companion object {

        @JvmStatic
        fun mapping() = arrayOf(
            Arguments.of('.', LightDevice.None),
            Arguments.of('\\', LightDevice.LeftMirror),
            Arguments.of('/', LightDevice.RightMirror),
            Arguments.of('-', LightDevice.HorizontalSplitter),
            Arguments.of('|', LightDevice.VerticalSplitter)
        )

        @JvmStatic
        fun apply() = arrayOf(
            Arguments.of(LightDevice.None, Direction.North, arrayOf(Direction.North)),
            Arguments.of(LightDevice.None, Direction.East, arrayOf(Direction.East)),
            Arguments.of(LightDevice.None, Direction.South, arrayOf(Direction.South)),
            Arguments.of(LightDevice.None, Direction.West, arrayOf(Direction.West)),
            Arguments.of(LightDevice.LeftMirror, Direction.North, arrayOf(Direction.West)),
            Arguments.of(LightDevice.LeftMirror, Direction.East, arrayOf(Direction.South)),
            Arguments.of(LightDevice.LeftMirror, Direction.South, arrayOf(Direction.East)),
            Arguments.of(LightDevice.LeftMirror, Direction.West, arrayOf(Direction.North)),
            Arguments.of(LightDevice.RightMirror, Direction.North, arrayOf(Direction.East)),
            Arguments.of(LightDevice.RightMirror, Direction.East, arrayOf(Direction.North)),
            Arguments.of(LightDevice.RightMirror, Direction.South, arrayOf(Direction.West)),
            Arguments.of(LightDevice.RightMirror, Direction.West, arrayOf(Direction.South)),
            Arguments.of(LightDevice.HorizontalSplitter, Direction.North, arrayOf(Direction.East, Direction.West)),
            Arguments.of(LightDevice.HorizontalSplitter, Direction.East, arrayOf(Direction.East)),
            Arguments.of(LightDevice.HorizontalSplitter, Direction.South, arrayOf(Direction.East, Direction.West)),
            Arguments.of(LightDevice.HorizontalSplitter, Direction.West, arrayOf(Direction.West)),
            Arguments.of(LightDevice.VerticalSplitter, Direction.North, arrayOf(Direction.North)),
            Arguments.of(LightDevice.VerticalSplitter, Direction.East, arrayOf(Direction.North, Direction.South)),
            Arguments.of(LightDevice.VerticalSplitter, Direction.South, arrayOf(Direction.South)),
            Arguments.of(LightDevice.VerticalSplitter, Direction.West, arrayOf(Direction.North, Direction.South))
        )
    }
}
