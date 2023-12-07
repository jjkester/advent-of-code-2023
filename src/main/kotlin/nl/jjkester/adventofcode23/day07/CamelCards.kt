package nl.jjkester.adventofcode23.day07

import nl.jjkester.adventofcode23.day07.model.Hand
import nl.jjkester.adventofcode23.execution.isDay

/**
 * https://adventofcode.com/2023/day/7
 */
object CamelCards {

    /**
     * Parses the [input] string to a list of [Hand]s.
     */
    fun parseHands(input: String): List<Hand> = input.lines().map(Hand::parse)

    /**
     * Part one.
     */
    fun totalWinnings(hands: List<Hand>): Long = hands
        .sorted()
        .withIndex()
        .sumOf { (index, hand) -> hand.bid * (index + 1L) }

    /**
     * Part two.
     */
    fun totalWinningsWithJoker(hands: List<Hand>): Long = hands
        .map { it.withJoker() }
        .sorted()
        .withIndex()
        .sumOf { (index, hand) -> hand.bid * (index + 1L) }
}

fun main() {
    CamelCards.isDay(7) {
        parsedWith { parseHands(it) } first { totalWinnings(it) } then { totalWinningsWithJoker(it) }
    }
}
