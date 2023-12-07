package nl.jjkester.adventofcode23.day07.model

/**
 * A hand in a game. A hand consists of a five ordered [cardValues] and a [bid].
 *
 * @param cardValues Five cards in the order they are played.
 * @param bid The bid that is multiplied by winning.
 */
data class Hand(val cardValues: List<CardValue>, val bid: Int) : Comparable<Hand> {

    init {
        require(cardValues.size == 5) { "A hand must consist of precisely five card values" }
    }

    /**
     * The [Value] of the hand.
     */
    val value: Value by lazy(LazyThreadSafetyMode.NONE) {
        val cards = cardValues.groupBy { it }
        val counts = CardValue.entries.filter { it != CardValue.JOKER }.associateWith { cards[it]?.size ?: 0 }
        val jokers = cards[CardValue.JOKER]?.size ?: 0

        when (val max = counts.values.max()) {
            in (5 - jokers)..(5 + jokers) -> Value.FIVE_OF_A_KIND
            in (4 - jokers)..(4 + jokers) -> Value.FOUR_OF_A_KIND
            in (3 - jokers)..(3 + jokers) -> if (counts.values.sortedDescending().drop(1).any { it + (jokers - 3 + max) >= 2 }) {
                Value.FULL_HOUSE
            } else {
                Value.THREE_OF_A_KIND
            }
            in (2 - jokers)..(2 + jokers) -> if (counts.values.count { it + (jokers - 2 + max) >= 2 } >= 2) {
                Value.TWO_PAIR
            } else {
                Value.ONE_PAIR
            }
            else -> Value.HIGH_CARD
        }
    }

    /**
     * Returns a new [Hand] with the same [bid] and the [CardValue.JACK] [cardValues] replaced with [CardValue.JOKER].
     */
    fun withJoker(): Hand = copy(cardValues = cardValues.map { if (it == CardValue.JACK) CardValue.JOKER else it })

    override fun compareTo(other: Hand): Int = value.compareTo(other.value).takeIf { it != 0 }
        ?: cardValues.zip(other.cardValues).map { it.first.compareTo(it.second) }.firstOrNull { it != 0 } ?: 0

    companion object {
        private val regex = Regex("""([23456789TJQKA]{5}) (\d+)""")

        /**
         * Parses the [input] string to a [Hand] and returns it.
         *
         * @param input The [input] string to parse.
         * @return The hand represented by the [input] string.
         * @throws IllegalArgumentException The [input] string is not a valid [Hand].
         * @see CardValue.parse
         */
        fun parse(input: String): Hand {
            val result = requireNotNull(regex.matchEntire(input)) { "Input string is not a valid hand" }
            val (cardValues, bid) = result.destructured

            return Hand(
                cardValues.map(CardValue::parse),
                requireNotNull(bid.toIntOrNull()) { "Bid is not a valid integer" }
            )
        }
    }

    /**
     * Value of a hand. The value depends on the [CardValue]s in the hand.
     *
     * The enum values are ordered by natural value, with [HIGH_CARD] being the lowest and [FIVE_OF_A_KIND] being the
     * highest value.
     */
    enum class Value {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND,
    }
}
