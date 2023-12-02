package nl.jjkester.adventofcode23.day02.model

import kotlin.math.max

/**
 * A game in which subsequently draws of cubes are revealed.
 *
 * @property id The id of the game. Must be greater than zero.
 * @property reveals List of draws of cubes that are revealed in order. Must not be empty.
 */
data class Game(val id: Int, val reveals: List<Cubes>) {

    /**
     * The minimum amount of cubes required to play the game.
     */
    val required: Cubes by lazy(LazyThreadSafetyMode.NONE) {
        reveals.reduce { acc, cubes ->
            Cubes(max(acc.red, cubes.red), max(acc.green, cubes.green), max(acc.blue, cubes.blue))
        }
    }

    init {
        require(id > 0) { "Id must be greater than zero" }
        require(reveals.isNotEmpty()) { "Reveals must not be empty" }
    }

    companion object {
        private val regex = Regex(
            """Game\s*(?<id>\d+):\s*(?<reveals>(\d+\s*(blue|red|green)[,;]\s*)*\d+\s*(blue|red|green))"""
        )

        /**
         * Parses a game from the [input] string and returns it.
         *
         * @param input The input to parse.
         * @return The [Game] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid game.
         */
        fun parse(input: String): Game {
            val result = requireNotNull(regex.matchEntire(input)) { "Input is not a valid game" }

            val id = requireNotNull(result.groups["id"]?.value?.toIntOrNull()) { "Game id is not an integer" }
            val reveals = result.groups["reveals"]?.value.orEmpty().split(";").map(String::trim)

            return Game(id, reveals.map(Cubes.Companion::parse))
        }
    }
}
