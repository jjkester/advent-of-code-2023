package nl.jjkester.adventofcode23.day06.model

/**
 * A race with a [winningTime] and [winningDistance].
 *
 * @param winningTime Time it took the winner to win.
 * @param winningDistance Distance traveled by the winner.
 */
data class Race(val winningTime: Long, val winningDistance: Long) {

    /**
     * Returns the competitive options that there are for beating the winner of this race. A competitive option is
     * represented by a time to hold the button on the race car.
     */
    fun competitiveOptions(): Sequence<Long> = sequence {
        for (t in 0..winningTime) {
            (t * (winningTime - t)).takeIf { it > winningDistance }?.also { yield(t) }
        }
    }

    companion object {

        /**
         * Parses the [input] string to a list of [Race]s and returns it.
         *
         * @param input The input string to parse.
         * @return The list of [Race]s represented by the [input] string.
         * @throws IllegalArgumentException The [input] string is not a valid list of [Race]s.
         */
        fun parse(input: String): List<Race> {
            val lines = input.lines()

            val times = requireNotNull(
                lines.singleOrNull { it.startsWith("Time:") }
            ) { "Times are missing from the race" }
                .split(Regex("\\s+"))
                .mapNotNull { it.toLongOrNull() }
            val distances = requireNotNull(
                lines.singleOrNull { it.startsWith("Distance:") }
            ) { "Distances are missing from the race" }
                .split(Regex("\\s+"))
                .mapNotNull { it.toLongOrNull() }

            require(times.size == distances.size) { "Times and distances contain a different number of races"}

            return times.zip(distances).map { (time, distance) -> Race(time, distance) }
        }
    }
}
