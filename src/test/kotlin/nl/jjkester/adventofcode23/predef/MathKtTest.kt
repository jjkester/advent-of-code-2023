package nl.jjkester.adventofcode23.predef

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class MathKtTest {

    @ParameterizedTest
    @MethodSource("intLcms")
    fun f(a: Long, b: Long, lcm: Long) {
        assertThat(lcm(a, b))
            .isEqualTo(lcm)
    }

    companion object {

        @JvmStatic
        fun intLcms() = arrayOf(
            Arguments.of(1L, 1L, 1L),
            Arguments.of(1L, 3L, 3L),
            Arguments.of(2L, 3L, 6L),
            Arguments.of(2L, 4L, 4L),
            Arguments.of(7L, 2L, 14L)
        )
    }
}
