package nl.jjkester.adventofcode23.predef.ranges

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class ShiftKtTest {

    @Test
    fun `shift an IntRange up`() {
        assertThat((10..20).shift(10))
            .isEqualTo(20..30)
    }

    @Test
    fun `shift an IntRange zero`() {
        assertThat((10..20).shift(0))
            .isEqualTo(10..20)
    }

    @Test
    fun `shift an IntRange down`() {
        assertThat((10..20).shift(-10))
            .isEqualTo(0..10)
    }

    @Test
    fun `shift an LongRange up`() {
        assertThat((10L..20L).shift(10L))
            .isEqualTo(20L..30L)
    }

    @Test
    fun `shift an LongRange zero`() {
        assertThat((10L..20L).shift(0L))
            .isEqualTo(10L..20L)
    }

    @Test
    fun `shift an LongRange down`() {
        assertThat((10L..20L).shift(-10L))
            .isEqualTo(0L..10L)
    }
}
