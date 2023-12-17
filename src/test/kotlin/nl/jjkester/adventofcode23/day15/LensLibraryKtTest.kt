package nl.jjkester.adventofcode23.day15

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class LensLibraryKtTest {

    @Test
    fun hashOfInitializationSequence() {
        assertThat(LensLibrary.hashOfInitializationSequence(testInput))
            .isEqualTo(1320)
    }

    @Test
    fun totalFocusingPower() {
        assertThat(LensLibrary.totalFocusingPower(testInput))
            .isEqualTo(145)
    }

    companion object {
        private const val testInput = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"
    }
}
