package nl.jjkester.adventofcode23.day19.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class PartTest {

    private val systemUnderTest = Part(3, 5, 7, 11)

    @Test
    fun parse() {
        assertThat(Part.parse("{x = 3, m=5,a  =  7  ,s=11 }"))
            .isEqualTo(systemUnderTest)
    }

    @Test
    fun rating() {

        assertThat(systemUnderTest.rating)
            .isEqualTo(26)
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun get(category: Category) {
        val expected = when (category) {
            Category.X -> 3
            Category.M -> 5
            Category.A -> 7
            Category.S -> 11
        }

        assertThat(systemUnderTest[category])
            .isEqualTo(expected)
    }
}
