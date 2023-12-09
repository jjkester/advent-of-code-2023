package nl.jjkester.adventofcode23.predef.space

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.api.Test

class RangeAreaTest {

    @Test
    fun `default constructor`() {
        assertThat(RangeArea(0..2, 3..5)).all {
            prop(RangeArea::x).isEqualTo(0..2)
            prop(RangeArea::y).isEqualTo(3..5)
        }
    }

    @Test
    fun `constructor with value for x`() {
        assertThat(RangeArea(0, 2..3)).all {
            prop(RangeArea::x).isEqualTo(0..0)
            prop(RangeArea::y).isEqualTo(2..3)
        }
    }

    @Test
    fun `constructor with value for y`() {
        assertThat(RangeArea(0..2, 3)).all {
            prop(RangeArea::x).isEqualTo(0..2)
            prop(RangeArea::y).isEqualTo(3..3)
        }
    }

    @Test
    fun `constructor with values for x and y`() {
        assertThat(RangeArea(0, 1)).all {
            prop(RangeArea::x).isEqualTo(0..0)
            prop(RangeArea::y).isEqualTo(1..1)
        }
    }
}
