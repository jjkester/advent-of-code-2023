package nl.jjkester.adventofcode23.day04.model

/**
 * A scratchcard in a game.
 *
 * @property id The id of the scratchcard.
 * @property winningNumbers The set of numbers that result in a win for this card.
 * @property scratchedNumbers The set of numbers that were revealed after scratching open the card.
 */
data class Scratchcard(val id: Int, val winningNumbers: Set<Int>, val scratchedNumbers: Set<Int>) {

    /**
     * The number of points this card is worth.
     */
    val points: Int
        get() = matchingNumbers.fold(0) { acc, _ -> if (acc == 0) 1 else acc * 2 }

    /**
     * The subset of scratched numbers that are present in the winning numbers.
     */
    val matchingNumbers get() = winningNumbers.intersect(scratchedNumbers)

    companion object {
        private val regex = Regex(
            """Card\s*(?<id>\d+):(?<winning>(\s*\d+)+)\s*\|(?<scratched>(\s*\d+)+)"""
        )

        /**
         * Parses the [input] string to a [Scratchcard] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Scratchcard] that is represented by the [input] string.
         * @throws IllegalArgumentException The [input] string is not a valid scratchcard.
         */
        fun parse(input: String): Scratchcard {
            val result = requireNotNull(regex.matchEntire(input)) { "Input is not a valid scratchcard" }

            val id = requireNotNull(result.groups["id"]?.value?.toIntOrNull()) { "Card does not have a valid id" }
            val winning = result.groups["winning"]?.value.orEmpty()
                .split(Regex("\\s"))
                .mapNotNull { it.toIntOrNull() }
            val scratched = result.groups["scratched"]?.value.orEmpty()
                .split(Regex("\\s"))
                .mapNotNull { it.toIntOrNull() }

            return Scratchcard(id, winning.toSet(), scratched.toSet())
        }
    }
}
