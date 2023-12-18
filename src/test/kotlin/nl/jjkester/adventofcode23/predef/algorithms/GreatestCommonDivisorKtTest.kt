package nl.jjkester.adventofcode23.predef.algorithms

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class GreatestCommonDivisorKtTest {

    @ParameterizedTest
    @MethodSource("divisors")
    fun divisor(a: Int, b: Int, gcd: Int) {
        assertThat(gcd(a, b))
            .isEqualTo(gcd)
    }

    companion object {

        @JvmStatic
        fun divisors() = arrayOf(
            Arguments.of(2, 4, 2),
            Arguments.of(1, 5, 1),
            Arguments.of(3, 6, 3),
            Arguments.of(12, 4, 4),
            Arguments.of(6, 14, 2)
        )
    }
}
