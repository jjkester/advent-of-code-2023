package nl.jjkester.adventofcode23.day08

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day08.model.PouchMap
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigInteger

class HauntedWastelandTest {

    @ParameterizedTest
    @MethodSource("steps")
    fun steps(parsedTestInput: PouchMap, expectedResult: Int) {
        assertThat(HauntedWasteland.steps(parsedTestInput))
            .isEqualTo(expectedResult)
    }

    @Test
    fun stepsWithGhosts() {
        val testInput = """
            LR

            AAA = (BBB, XXX)
            BBB = (XXX, ZZZ)
            ZZZ = (ZZZ, ZZZ)
            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent()
        val parsedTestInput = PouchMap.parse(testInput)

        assertThat(HauntedWasteland.stepsWithGhosts(parsedTestInput))
            .isEqualTo(6L)
    }

    companion object {
        private val testInputs = listOf(
            """
                RL
    
                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)
            """.trimIndent(),
            """
                LLR

                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)
            """.trimIndent()
        )

        private val parsedTestInputs by lazy(LazyThreadSafetyMode.NONE) {
            testInputs.map(PouchMap::parse)
        }

        @JvmStatic
        fun steps() = arrayOf(
            Arguments.of(parsedTestInputs[0], 2),
            Arguments.of(parsedTestInputs[1], 6)
        )
    }
}
