package nl.jjkester.adventofcode23.day19.model

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class AcceptablePartsTest {

    @Test
    fun `count with non-empty ranges`() {
        val systemUnderTest = AcceptableParts(0..2, -2..4, 600..<700, 1..1)

        assertThat(systemUnderTest.count())
            .isEqualTo(2100)
    }

    @Test
    fun `count with one empty range`() {
        val systemUnderTest = AcceptableParts(0..2, -2..4, IntRange.EMPTY, 1..1)

        assertThat(systemUnderTest.count())
            .isEqualTo(0)
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun `coerceIn containing range`(category: Category) {
        val systemUnderTest = AcceptableParts(0..5, 0..5, 0..5, 0..5)

        assertThat(systemUnderTest.coerceIn(category, 1..3)).all {
            transform { it.x }.isEqualTo(if (category == Category.X) 1..3 else 0..5)
            transform { it.m }.isEqualTo(if (category == Category.M) 1..3 else 0..5)
            transform { it.a }.isEqualTo(if (category == Category.A) 1..3 else 0..5)
            transform { it.s }.isEqualTo(if (category == Category.S) 1..3 else 0..5)
        }
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun `coerceIn lower overlapping range`(category: Category) {
        val systemUnderTest = AcceptableParts(0..5, 0..5, 0..5, 0..5)

        assertThat(systemUnderTest.coerceIn(category, -1..3)).all {
            transform { it.x }.isEqualTo(if (category == Category.X) 0..3 else 0..5)
            transform { it.m }.isEqualTo(if (category == Category.M) 0..3 else 0..5)
            transform { it.a }.isEqualTo(if (category == Category.A) 0..3 else 0..5)
            transform { it.s }.isEqualTo(if (category == Category.S) 0..3 else 0..5)
        }
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun `coerceIn higher overlapping range`(category: Category) {
        val systemUnderTest = AcceptableParts(0..5, 0..5, 0..5, 0..5)

        assertThat(systemUnderTest.coerceIn(category, 5..7)).all {
            transform { it.x }.isEqualTo(if (category == Category.X) 5..5 else 0..5)
            transform { it.m }.isEqualTo(if (category == Category.M) 5..5 else 0..5)
            transform { it.a }.isEqualTo(if (category == Category.A) 5..5 else 0..5)
            transform { it.s }.isEqualTo(if (category == Category.S) 5..5 else 0..5)
        }
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun `coerceIn not overlapping range`(category: Category) {
        val systemUnderTest = AcceptableParts(0..5, 0..5, 0..5, 0..5)

        assertThat(systemUnderTest.coerceIn(category, 6..7)).all {
            transform { it.x }.isEqualTo(if (category == Category.X) IntRange.EMPTY else 0..5)
            transform { it.m }.isEqualTo(if (category == Category.M) IntRange.EMPTY else 0..5)
            transform { it.a }.isEqualTo(if (category == Category.A) IntRange.EMPTY else 0..5)
            transform { it.s }.isEqualTo(if (category == Category.S) IntRange.EMPTY else 0..5)
        }
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun `coerceIn empty range`(category: Category) {
        val systemUnderTest = AcceptableParts(0..5, 0..5, 0..5, 0..5)

        assertThat(systemUnderTest.coerceIn(category, IntRange.EMPTY)).all {
            transform { it.x }.isEqualTo(if (category == Category.X) IntRange.EMPTY else 0..5)
            transform { it.m }.isEqualTo(if (category == Category.M) IntRange.EMPTY else 0..5)
            transform { it.a }.isEqualTo(if (category == Category.A) IntRange.EMPTY else 0..5)
            transform { it.s }.isEqualTo(if (category == Category.S) IntRange.EMPTY else 0..5)
        }
    }
}
