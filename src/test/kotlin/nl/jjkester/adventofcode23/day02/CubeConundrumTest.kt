package nl.jjkester.adventofcode23.day02

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import nl.jjkester.adventofcode23.day02.model.Cubes
import nl.jjkester.adventofcode23.day02.model.Game
import org.junit.jupiter.api.Test

class CubeConundrumTest {

    @Test
    fun parseGames() {
        val input = testInput.lines()[2]

        assertThat(CubeConundrum.parseGames(input))
            .containsExactly(
                Game(
                    id = 3,
                    reveals = listOf(
                        Cubes(20, 8, 6),
                        Cubes(4, 13, 5),
                        Cubes(1, 5, 0)
                    )
                )
            )
    }

    @Test
    fun sumOfPossibleGames() {
        assertThat(CubeConundrum.sumOfPossibleGames(parsedTestInput))
            .isEqualTo(8)
    }

    @Test
    fun sumOfPowersOfRequiredCubes() {
        assertThat(CubeConundrum.sumOfPowersOfRequiredCubes(parsedTestInput))
            .isEqualTo(2286)
    }

    companion object {
        private val testInput = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            CubeConundrum.parseGames(testInput)
        }
    }
}
