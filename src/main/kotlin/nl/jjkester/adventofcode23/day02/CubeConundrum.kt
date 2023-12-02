package nl.jjkester.adventofcode23.day02

import nl.jjkester.adventofcode23.day02.model.Cubes
import nl.jjkester.adventofcode23.day02.model.Game
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/2
 */
object CubeConundrum {

    /**
     * Parses the raw [input] into the modeled problem domain.
     */
    fun parseGames(input: String): Sequence<Game> = input.lineSequence().map(Game.Companion::parse)

    /**
     * Part one.
     */
    fun sumOfPossibleGames(games: Sequence<Game>): Int {
        val available = Cubes(red = 12, green = 13, blue = 14)
        return games
            .filter { game ->
                game.reveals.none { used ->
                    used.red > available.red || used.green > available.green || used.blue > available.blue
                }
            }
            .sumOf { it.id }
    }

    /**
     * Part two.
     */
    fun sumOfPowersOfRequiredCubes(games: Sequence<Game>): Int = games.sumOf { it.required.power }
}

fun main() {
    CubeConundrum.isDay(2) {
        parsedWith { parseGames(it) } first { sumOfPossibleGames(it) } then { sumOfPowersOfRequiredCubes(it) }
    }
}
