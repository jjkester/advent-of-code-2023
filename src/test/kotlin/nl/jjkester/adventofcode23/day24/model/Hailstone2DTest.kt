package nl.jjkester.adventofcode23.day24.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Hailstone2DTest {

    @ParameterizedTest
    @MethodSource("intersections")
    fun intersectionWith(first: Hailstone2D, second: Hailstone2D, intersection: Hailstone2D.Vector?) {
        assertThat(first.intersectionWith(second))
            .isEqualTo(intersection)
    }

    @ParameterizedTest
    @MethodSource("futureIntersections")
    fun futureIntersectionWith(first: Hailstone2D, second: Hailstone2D, intersection: Hailstone2D.Vector?) {
        assertThat(first.futureIntersectionWith(second))
            .isEqualTo(intersection)
    }

    companion object {
        private val a = Hailstone2D(Hailstone2D.Vector(19.0, 13.0), Hailstone2D.Vector(-2.0, 1.0))
        private val b = Hailstone2D(Hailstone2D.Vector(18.0, 19.0), Hailstone2D.Vector(-1.0, -1.0))
        private val c = Hailstone2D(Hailstone2D.Vector(20.0, 25.0), Hailstone2D.Vector(-2.0, -2.0))
        private val d = Hailstone2D(Hailstone2D.Vector(12.0, 31.0), Hailstone2D.Vector(-1.0, -2.0))
        private val e = Hailstone2D(Hailstone2D.Vector(20.0, 19.0), Hailstone2D.Vector(1.0, -5.0))

        @JvmStatic
        fun intersections() = arrayOf(
            Arguments.of(a, b, Hailstone2D.Vector(43/3.0, 46/3.0)),
            Arguments.of(a, c, Hailstone2D.Vector(35/3.0, 50/3.0)),
            Arguments.of(a, d, Hailstone2D.Vector(6.2, 19.4)),
            Arguments.of(b, c, null),
            Arguments.of(b, d, Hailstone2D.Vector(-6.0, -5.0)),
            Arguments.of(c, d, Hailstone2D.Vector(-2.0, 3.0)),
        )

        @JvmStatic
        fun futureIntersections() = arrayOf(
            Arguments.of(a, b, Hailstone2D.Vector(43/3.0, 46/3.0)),
            Arguments.of(a, c, Hailstone2D.Vector(35/3.0, 50/3.0)),
            Arguments.of(a, d, Hailstone2D.Vector(6.2, 19.4)),
            Arguments.of(a, e, null),
            Arguments.of(b, c, null),
            Arguments.of(b, d, Hailstone2D.Vector(-6.0, -5.0)),
            Arguments.of(b, e, null),
            Arguments.of(c, d, Hailstone2D.Vector(-2.0, 3.0)),
            Arguments.of(c, e, null),
            Arguments.of(d, e, null)
        )
    }
}
