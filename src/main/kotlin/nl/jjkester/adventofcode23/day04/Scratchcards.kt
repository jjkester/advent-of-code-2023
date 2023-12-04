package nl.jjkester.adventofcode23.day04

import nl.jjkester.adventofcode23.day04.model.Scratchcard
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/4
 */
object Scratchcards {

    /**
     * Parses the [input] to a list of [Scratchcard]s using [Scratchcard.parse] for each line.
     */
    fun parseScratchcards(input: String): List<Scratchcard> = input.lineSequence()
        .map { Scratchcard.parse(it) }
        .toList()

    /**
     * Part one.
     */
    fun totalNumberOfPoints(scratchcards: List<Scratchcard>): Int = scratchcards.sumOf { it.points }

    /**
     * Part two.
     */
    fun numberOfFinalScratchcards(scratchcards: List<Scratchcard>): Int {
        val copies = mutableMapOf<Int, Int>()

        scratchcards.forEachIndexed { index, card ->
            copies.computeIfAbsent(card.id) { 1 }
            scratchcards.subList(index + 1, index + 1 + card.matchingNumbers.size).forEach { wonCard ->
                copies.compute(wonCard.id) { _, current -> (current ?: 1) + (copies[card.id] ?: 1) }
            }
        }

        return copies.values.sum()
    }
}

fun main() {
    Scratchcards.isDay(4) {
        parsedWith { parseScratchcards(it) } first { totalNumberOfPoints(it) } then { numberOfFinalScratchcards(it) }
    }
}
