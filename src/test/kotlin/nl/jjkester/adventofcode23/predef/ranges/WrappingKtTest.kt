package nl.jjkester.adventofcode23.predef.ranges

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class WrappingKtTest {

    private val intRange = -2..2
    private val longRange = -2L..2L

    @ParameterizedTest
    @ValueSource(ints = [-2, -1, 0, 1, 2])
    fun `wrap integer in range`(number: Int) {
        assertThat(intRange.wrap(number))
            .isEqualTo(number)
    }

    @ParameterizedTest
    @MethodSource("above")
    fun `wrap integer above range`(number: Int, expected: Int) {
        assertThat(intRange.wrap(number))
            .isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("below")
    fun `wrap integer below range`(number: Int, expected: Int) {
        assertThat(intRange.wrap(number))
            .isEqualTo(expected)
    }

@ParameterizedTest
    @ValueSource(longs = [-2L, -1L, 0L, 1L, 2L])
    fun `wrap long in range`(number: Long) {
        assertThat(longRange.wrap(number))
            .isEqualTo(number)
    }

    @ParameterizedTest
    @MethodSource("above")
    fun `wrap long above range`(number: Long, expected: Long) {
        assertThat(longRange.wrap(number))
            .isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("below")
    fun `wrap long below range`(number: Long, expected: Long) {
        assertThat(longRange.wrap(number))
            .isEqualTo(expected)
    }

    companion object {

        @JvmStatic
        fun above() = arrayOf(
            Arguments.of(3, -2),
            Arguments.of(4, -1),
            Arguments.of(5, -0),
            Arguments.of(6, 1),
            Arguments.of(7, 2),
            Arguments.of(8, -2),
            Arguments.of(55, 0)
        )

        @JvmStatic
        fun below() = arrayOf(
            Arguments.of(-3, 2),
            Arguments.of(-4, 1),
            Arguments.of(-5, 0),
            Arguments.of(-6, -1),
            Arguments.of(-7, -2),
            Arguments.of(-8, 2),
            Arguments.of(-55, 0)
        )
    }
}
