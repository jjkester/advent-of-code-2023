package nl.jjkester.adventofcode23.predef.algorithms

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.predef.space.Coordinate2D
import nl.jjkester.adventofcode23.predef.space.by
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ShoelaceKtTest {

    @ParameterizedTest
    @MethodSource("trueAreas")
    fun coordinatesAsPoints(shape: List<Coordinate2D>, expected: Double) {
        assertThat(shoelace(shape, false))
            .isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("innerAreas")
    fun coordinatesAsAreasInner(shape: List<Coordinate2D>, expected: Double) {
        assertThat(shoelace(shape, true, false))
            .isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("outerAreas")
    fun coordinatesAsAreasOuter(shape: List<Coordinate2D>, expected: Double) {
        assertThat(shoelace(shape, true, true))
            .isEqualTo(expected)
    }

    companion object {
        private val straightTriangle = listOf(10 by 10, 15 by 10, 15 by 15)
        private val square = listOf(0 by 0, 0 by 2, 2 by 2, 2 by 0)
        private val rectangle = listOf(0 by 0, 2 by 0, 2 by 3, 0 by 3)
        private val rectangleMinusCorner = listOf(0 by 0, 0 by 4, 4 by 4, 4 by 1, 3 by 1, 3 by 0)

        @JvmStatic
        fun trueAreas() = arrayOf(
            Arguments.of(straightTriangle, 12.5),
            Arguments.of(square, 4.0),
            Arguments.of(rectangle, 6.0),
            Arguments.of(rectangleMinusCorner, 15.0)
        )

        @JvmStatic
        fun innerAreas() = arrayOf(
            Arguments.of(straightTriangle, 6.0),
            Arguments.of(square, 1.0),
            Arguments.of(rectangle, 2.0),
            Arguments.of(rectangleMinusCorner, 8.0)
        )

        @JvmStatic
        fun outerAreas() = arrayOf(
            Arguments.of(straightTriangle, 21.0),
            Arguments.of(square, 9.0),
            Arguments.of(rectangle, 12.0),
            Arguments.of(rectangleMinusCorner, 24.0)
        )
    }
}
