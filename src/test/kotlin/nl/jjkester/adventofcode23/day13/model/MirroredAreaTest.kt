package nl.jjkester.adventofcode23.day13.model

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class MirroredAreaTest {

    @Test
    fun findHorizontalReflection() {
        assertThat(parsedTestInputs.last().findHorizontalReflections())
            .containsExactly(4)
    }

    @Test
    fun findVerticalReflection() {
        assertThat(parsedTestInputs.first().findVerticalReflections())
            .containsExactly(5)
    }

    @Test
    fun findNoHorizontalReflection() {
        assertThat(parsedTestInputs.first().findHorizontalReflections())
            .isEmpty()
    }

    @Test
    fun findNoVerticalReflection() {
        assertThat(parsedTestInputs.last().findVerticalReflections())
            .isEmpty()
    }

    @ParameterizedTest
    @MethodSource("horizontal")
    fun findAlternativeHorizontalReflection(area: MirroredArea, expected: Int) {
        assertThat(area.findAlternativeHorizontalReflections())
            .containsExactly(expected)
    }

    @ParameterizedTest
    @MethodSource("vertical")
    fun findAlternativeVerticalReflection(area: MirroredArea) {
        assertThat(area.findAlternativeVerticalReflections())
            .isEmpty()
    }

    companion object {
        private val testInputs = listOf(
            """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
            """.trimIndent(),
            """
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
            """.trimIndent()
        )

        private val parsedTestInputs by lazy(LazyThreadSafetyMode.NONE) {
            testInputs.map(MirroredArea::parse)
        }

        @JvmStatic
        fun horizontal() = arrayOf(
            Arguments.of(parsedTestInputs.first(), 3),
            Arguments.of(parsedTestInputs.last(), 1)
        )

        @JvmStatic
        fun vertical() = parsedTestInputs.toTypedArray()
    }
}
