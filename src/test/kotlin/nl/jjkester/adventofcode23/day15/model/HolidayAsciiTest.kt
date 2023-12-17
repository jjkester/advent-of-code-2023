package nl.jjkester.adventofcode23.day15.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class HolidayAsciiTest {

    @ParameterizedTest
    @MethodSource("hashValues")
    fun hash(input: String, hash: Short) {
        assertThat(HolidayAscii.hash(input))
            .isEqualTo(hash)
    }

    companion object {

        @JvmStatic
        fun hashValues() = arrayOf(
            Arguments.of("HASH", 52.toShort()),
            Arguments.of("rn=1", 30.toShort()),
            Arguments.of("cm-", 253.toShort())
        )
    }
}
