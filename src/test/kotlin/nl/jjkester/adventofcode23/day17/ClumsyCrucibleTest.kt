package nl.jjkester.adventofcode23.day17

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day17.model.CityMap
import org.junit.jupiter.api.Test

class ClumsyCrucibleTest {

    @Test
    fun leastHeatLoss() {
        assertThat(ClumsyCrucible.leastHeatLoss(parsedTestInput))
            .isEqualTo(102)
    }

    @Test
    fun leastHeatLossUltra() {
        assertThat(ClumsyCrucible.leastHeatLossUltra(parsedTestInput))
            .isEqualTo(94)
    }

    @Test
    fun leastHeatLossUltraAlternative() {
        val testInput = """
            111111111111
            999999999991
            999999999991
            999999999991
            999999999991
        """.trimIndent()

        val parsedTestInput = CityMap.parse(testInput)

        assertThat(ClumsyCrucible.leastHeatLossUltra(parsedTestInput))
            .isEqualTo(71)
    }

    companion object {
        private val testInput = """
            2413432311323
            3215453535623
            3255245654254
            3446585845452
            4546657867536
            1438598798454
            4457876987766
            3637877979653
            4654967986887
            4564679986453
            1224686865563
            2546548887735
            4322674655533
        """.trimIndent()

        private val parsedTestInput  by lazy(LazyThreadSafetyMode.NONE) {
            CityMap.parse(testInput)
        }
    }
}
