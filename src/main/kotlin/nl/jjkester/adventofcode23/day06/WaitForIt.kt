package nl.jjkester.adventofcode23.day06

import nl.jjkester.adventofcode23.day06.model.Race
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/6
 */
object WaitForIt {

    /**
     * Part one.
     */
    fun totalMargin(races: Iterable<Race>): Long = races.asSequence()
        .map { it.competitiveOptions().count().toLong() }
        .reduce(Long::times)

    /**
     * Part two.
     */
    fun competitiveOptions(races: List<Race>): Int = fixKerning(races).competitiveOptions().count()

    /**
     * Returns the single race that is actually represented by the list of [races].
     *
     * @param races The races to re-interpret.
     * @return The single race actually represented by the [races].
     */
    fun fixKerning(races: List<Race>): Race = races
        .fold(Race(0 , 0)) { acc, race ->
            Race(
                "${acc.winningTime}${race.winningTime}".toLong(),
                "${acc.winningDistance}${race.winningDistance}".toLong()
            )
        }
}

fun main() {
    WaitForIt.isDay(6) {
        parsedWith { Race.parse(it) } first { totalMargin(it) } then { competitiveOptions(it) }
    }
}
