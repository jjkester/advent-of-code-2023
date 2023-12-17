package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import org.junit.jupiter.api.Test

class NumberMultikAreaMapTest {

    private val systemUnderTest = NumberMultikAreaMap(
        Multik.ndarray(listOf(Long.MIN_VALUE, 0, 0, Long.MAX_VALUE), 2, 2)
    )

    @Test
    fun serialize() {
        assertThat(systemUnderTest.contains(Long.MAX_VALUE))
            .isTrue()
    }

    @Test
    fun deserialize() {
        assertThat(systemUnderTest.get(0, 0))
            .isEqualTo(Long.MIN_VALUE)
    }
}
