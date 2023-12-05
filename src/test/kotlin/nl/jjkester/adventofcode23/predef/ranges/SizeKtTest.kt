package nl.jjkester.adventofcode23.predef.ranges

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SizeKtTest {

    @ParameterizedTest
    @MethodSource("intProgressions")
    fun `size of an IntProgression is calculated correctly`(progression: IntProgression, expectedSize: Int) {
        assertThat(progression.size)
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("uIntProgressions")
    fun `size of an UIntProgression is calculated correctly`(progression: UIntProgression, expectedSize: UInt) {
        assertThat(progression.size)
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("longProgressions")
    fun `size of a LongProgression is calculated correctly`(progression: LongProgression, expectedSize: Long) {
        assertThat(progression.size)
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("uLongProgressions")
    fun `size of an ULongProgression is calculated correctly`(progression: ULongProgression, expectedSize: ULong) {
        assertThat(progression.size)
            .isEqualTo(expectedSize)
    }

    companion object {

        @Suppress("EmptyRange")
        @JvmStatic
        fun intProgressions() = arrayOf(
            Arguments.of(1..8 step 3, 3),
            Arguments.of(10..100 step 10, 10),
            Arguments.of(1..0 step 10, 0),
            Arguments.of(10..-100 step 7, 0),
            Arguments.of(8 downTo 1, 8),
            Arguments.of(100 downTo 10 step 10, 10),
            Arguments.of(1..1, 1)
        )

        @Suppress("EmptyRange")
        @JvmStatic
        fun uIntProgressions() = arrayOf(
            Arguments.of(1u..8u step 3, 3),
            Arguments.of(10u..100u step 10, 10),
            Arguments.of(1u..0u step 10, 0),
            Arguments.of(100u..10u step 7, 0),
            Arguments.of(8u downTo 1u, 8),
            Arguments.of(100u downTo 10u step 10, 10),
            Arguments.of(1u..1u, 1)
        )

        @Suppress("EmptyRange")
        @JvmStatic
        fun longProgressions() = arrayOf(
            Arguments.of(1L..8L step 3, 3),
            Arguments.of(10L..100L step 10, 10),
            Arguments.of(1L..0L step 10, 0),
            Arguments.of(10L..-100L step 7, 0),
            Arguments.of(8L downTo 1L, 8),
            Arguments.of(100L downTo 10L step 10, 10),
            Arguments.of(1L..1L, 1)
        )

        @Suppress("EmptyRange")
        @JvmStatic
        fun uLongProgressions() = arrayOf(
            Arguments.of(1uL..8uL step 3, 3),
            Arguments.of(10uL..100uL step 10, 10),
            Arguments.of(1uL..0uL step 10, 0),
            Arguments.of(100uL..10uL step 7, 0),
            Arguments.of(8uL downTo 1uL, 8),
            Arguments.of(100uL downTo 10uL step 10, 10),
            Arguments.of(1uL..1uL, 1)
        )
    }
}
