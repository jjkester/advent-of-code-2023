package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.containsExactly
import org.junit.jupiter.api.Test

class AbstractAreaMapTest {

    private val systemUnderTest = TestAreaMap(1..3, 4..5) { x, y -> x * 10 + y }

    @Test
    fun columns() {
        assertThat(systemUnderTest.columns)
            .containsExactly(
                listOf(14, 15),
                listOf(24, 25),
                listOf(34, 35)
            )
    }

    @Test
    fun rows() {
        assertThat(systemUnderTest.rows)
            .containsExactly(
                listOf(14, 24, 34),
                listOf(15, 25, 35)
            )
    }

    private class TestAreaMap(
        override val x: IntRange,
        override val y: IntRange,
        private val lookup: (x: Int, y: Int) -> Int
    ) : AbstractAreaMap<Int>() {

        override fun contains(element: Int): Boolean = error("Contains should not be called in test")

        override fun get(x: Int, y: Int): Int = lookup(x, y)
    }
}
