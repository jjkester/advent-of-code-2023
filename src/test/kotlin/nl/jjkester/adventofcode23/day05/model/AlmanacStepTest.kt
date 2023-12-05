package nl.jjkester.adventofcode23.day05.model

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class AlmanacStepTest {

    @ParameterizedTest
    @MethodSource("keysForGet")
    fun get(key: String, value: Int) {
        assertThat(systemUnderTest[key])
            .isEqualTo(value)
    }

    @Test
    fun map() {
        assertThat(systemUnderTest.map("10".."16"))
            .extracting { it.start to it.endInclusive }
            .containsExactlyInAnyOrder(
                10 to 11,
                22 to 23,
                14 to 14,
                5 to 5,
                16 to 16
            )
    }

    @Test
    fun mapAll() {
        assertThat(systemUnderTest.mapAll(listOf("1".."12", "15".."20")))
            .extracting { it.start to it.endInclusive }
            .containsExactlyInAnyOrder(
                1 to 11,
                22 to 22,
                5 to 5,
                16 to 20
            )
    }

    companion object {
        private val systemUnderTest = AlmanacStep(
            extractKey = String::toLong,
            transformValue = Long::toInt,
            ranges = listOf(
                12L..13L to 22L..23L,
                15L..15L to 5L..5L
            )
        )

        @JvmStatic
        fun keysForGet() = arrayOf(
            Arguments.of("10", 10),
            Arguments.of("11", 11),
            Arguments.of("12", 22),
            Arguments.of("13", 23),
            Arguments.of("14", 14),
            Arguments.of("15", 5),
            Arguments.of("16", 16)
        )
    }
}
