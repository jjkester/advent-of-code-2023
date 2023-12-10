package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.junit.jupiter.api.Test

class MultikAreaMapTest {

    private val systemUnderTest = object : MultikAreaMap<String, Int>(
        Multik.d2arrayIndices(5, 3, Int::times)
    ) {
        override fun serialize(element: String): Int = element.toInt()
        override fun deserialize(value: Int): String = value.toString()
    }

    @Test
    fun getX() {
        assertThat(systemUnderTest.x)
            .isEqualTo(0..4)
    }

    @Test
    fun getY() {
        assertThat(systemUnderTest.y)
            .isEqualTo(0..2)
    }

    @Test
    fun get() {
        assertThat(systemUnderTest[3, 2])
            .isEqualTo("6")
    }

    @Test
    fun contains() {
        assertThat("8" in systemUnderTest)
            .isTrue()
        assertThat("-1" in systemUnderTest)
            .isFalse()
    }

    @Test
    fun iterator() {
        assertThat(systemUnderTest.iterator().asSequence().toList())
            .containsExactly(
                coordinateOf(0, 0) to "0",
                coordinateOf(1, 0) to "0",
                coordinateOf(2, 0) to "0",
                coordinateOf(3, 0) to "0",
                coordinateOf(4, 0) to "0",
                coordinateOf(0, 1) to "0",
                coordinateOf(1, 1) to "1",
                coordinateOf(2, 1) to "2",
                coordinateOf(3, 1) to "3",
                coordinateOf(4, 1) to "4",
                coordinateOf(0, 2) to "0",
                coordinateOf(1, 2) to "2",
                coordinateOf(2, 2) to "4",
                coordinateOf(3, 2) to "6",
                coordinateOf(4, 2) to "8"
            )
    }
}
