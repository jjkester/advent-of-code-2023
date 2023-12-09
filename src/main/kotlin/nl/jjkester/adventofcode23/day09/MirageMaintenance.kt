package nl.jjkester.adventofcode23.day09

import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/9
 */
object MirageMaintenance {

    /**
     * Parses the [input] string to a list of integer sequences.
     */
    fun parseSequences(input: String): Sequence<Sequence<Int>> = input.lineSequence()
        .map { line -> line.splitToSequence(Regex("\\s+")).mapNotNull { it.toIntOrNull() } }

    /**
     * Part one.
     */
    fun sumOfNextValues(sequences: Sequence<Sequence<Int>>): Int = sequences.sumOf { extrapolate(it).second }

    /**
     * Part two.
     */
    fun sumOfPreviousValues(sequences: Sequence<Sequence<Int>>): Int = sequences.sumOf { extrapolate(it).first }

    private fun extrapolate(sequence: Sequence<Int>): Pair<Int, Int> = sequence.distinct().singleOrNull()
        ?.let { it to it }
        ?: sequence.toList().run {
            extrapolate(differences())
                .let { (first, last) -> first() - first to last() + last }
        }

    private fun Iterable<Int>.differences() = sequence {
        val iterator = iterator()

        if (iterator.hasNext()) {
            var previous = iterator.next()

            while (iterator.hasNext()) {
                iterator.next().also { current ->
                    yield(current - previous)
                    previous = current
                }
            }
        }
    }
}

fun main() {
    MirageMaintenance.isDay(9) {
        parsedWith { parseSequences(it) } first { sumOfNextValues(it) } then { sumOfPreviousValues(it) }
    }
}
