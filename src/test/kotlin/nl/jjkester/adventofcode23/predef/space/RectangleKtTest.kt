package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class RectangleKtTest {

    @Test
    fun `scale when amount is positive then result is larger`() {
        val rectangle = Rectangle(10, 20)

        assertThat(rectangle.scale(2))
            .isEqualTo(Rectangle(8..12, 18..22))
    }

    @Test
    fun `scale when amount is zero then result is same`() {
        val rectangle = Rectangle(10, 20)

        assertThat(rectangle.scale(0))
            .isSameAs(rectangle)
    }

    @Test
    fun `scale when amount is negative then result is smaller`() {
        val rectangle = Rectangle(2..18, 12..28)

        assertThat(rectangle.scale(-2))
            .isEqualTo(Rectangle(4..16, 14..26))
    }

    @Test
    fun `scale when amount is negative then result is empty`() {
        val rectangle = Rectangle(10, 20)

        assertThat(rectangle.scale(-1))
            .isEqualTo(Rectangle(IntRange.EMPTY, IntRange.EMPTY))
    }

    @ParameterizedTest
    @MethodSource("areasForCoerceIn")
    fun `coerceIn with area returns the adjusted rectangle`(coercion: Area, expectedResult: Rectangle) {
        assertThat(rectangleForCoerceIn.coerceIn(coercion))
            .isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @MethodSource("areasForCoerceIn")
    fun `coerceIn with distinct integers returns the adjusted rectangle`(coercion: Area, expectedResult: Rectangle) {
        assertThat(rectangleForCoerceIn.coerceIn(coercion.x, coercion.y))
            .isEqualTo(expectedResult)
    }

    companion object {
        val rectangleForCoerceIn = Rectangle(0..10, 10..20)

        @JvmStatic
        fun areasForCoerceIn() = arrayOf(
            Arguments.of(Rectangle(2..8, 15), Rectangle(2..8, 15)),
            Arguments.of(Rectangle(2..8, 0..15), Rectangle(2..8, 10..15)),
            Arguments.of(Rectangle(-10..0, 2..5), Rectangle(0, IntRange.EMPTY)),
            Arguments.of(Rectangle(IntRange.EMPTY, IntRange.EMPTY), Rectangle(IntRange.EMPTY, IntRange.EMPTY))
        )
    }
}
