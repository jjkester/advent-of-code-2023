package nl.jjkester.adventofcode23.predef

import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test

class NumbersKtTest {

    @Test
    fun `numbersByName without a filter`() {
        assertThat(numbersByName()).all {
            hasSize(10)
            transform { it.values }.containsExactlyInAnyOrder(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        }
    }

    @Test
    fun `numbersByName filtered`() {
        assertThat(numbersByName(2..5)).all {
            hasSize(4)
            key("two").isEqualTo(2)
            key("three").isEqualTo(3)
            key("four").isEqualTo(4)
            key("five").isEqualTo(5)
        }
    }

    @Test
    fun `numbersByName out of range`() {
        assertFailure { numbersByName(-1..10) }
            .isInstanceOf<IllegalArgumentException>()
    }
}
