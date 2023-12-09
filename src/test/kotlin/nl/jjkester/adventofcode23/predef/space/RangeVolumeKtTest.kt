package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class RangeVolumeKtTest {

    @Test
    fun volumeOf() {
        assertThat(volumeOf(10..15, 20..25, 30..35))
            .isEqualTo(RangeVolume(10..15, 20..25, 30..35))
    }
}
