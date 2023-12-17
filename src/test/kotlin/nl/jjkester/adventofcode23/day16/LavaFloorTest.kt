package nl.jjkester.adventofcode23.day16

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day16.model.Contraption
import org.junit.jupiter.api.Test

class LavaFloorTest {

    @Test
    fun energizedTiles() {
        assertThat(LavaFloor.energizedTiles(parsedTestInput))
            .isEqualTo(46)
    }

    @Test
    fun maxEnergizedTiles() {
        assertThat(LavaFloor.maxEnergizedTiles(parsedTestInput))
            .isEqualTo(51)
    }

    companion object {
        private val testInput = """
            .|...\....
            |.-.\.....
            .....|-...
            ........|.
            ..........
            .........\
            ..../.\\..
            .-.-/..|..
            .|....-|.\
            ..//.|....
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Contraption.parse(testInput)
        }
    }
}
