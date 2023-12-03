package nl.jjkester.adventofcode23.predef.space

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.api.Test

class RectangleTest {

    @Test
    fun `default constructor`() {
        assertThat(Rectangle(0..2, 3..5)).all {
            prop(Rectangle::x).isEqualTo(0..2)
            prop(Rectangle::y).isEqualTo(3..5)
        }
    }

    @Test
    fun `constructor with value for x`() {
        assertThat(Rectangle(0, 2..3)).all {
            prop(Rectangle::x).isEqualTo(0..0)
            prop(Rectangle::y).isEqualTo(2..3)
        }
    }

    @Test
    fun `constructor with value for y`() {
        assertThat(Rectangle(0..2, 3)).all {
            prop(Rectangle::x).isEqualTo(0..2)
            prop(Rectangle::y).isEqualTo(3..3)
        }
    }

    @Test
    fun `constructor with values for x and y`() {
        assertThat(Rectangle(0, 1)).all {
            prop(Rectangle::x).isEqualTo(0..0)
            prop(Rectangle::y).isEqualTo(1..1)
        }
    }
}
