package nl.jjkester.adventofcode23.day07.model

/**
 * Value of a card.
 *
 * @param value Character representing this value in strings.
 */
enum class CardValue(val value: Char) : Comparable<CardValue> {
    JOKER('J'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    companion object {
        private val byValue = entries.associateBy { it.value }
            .also { check(JOKER !in it.values) }

        /**
         * Parses the [input] character to a [CardValue] and returns it.
         *
         * `'J'` characters are always parsed to [CardValue.JACK].
         *
         * @param input The input character to parse.
         * @return The [CardValue] represented by the [input] character.
         * @throws IllegalArgumentException The [input] character does not represent a [CardValue].
         */
        fun parse(input: Char): CardValue = requireNotNull(byValue[input]) { "Input is not a valid card value" }
    }
}
