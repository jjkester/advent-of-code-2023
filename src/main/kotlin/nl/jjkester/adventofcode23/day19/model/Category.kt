package nl.jjkester.adventofcode23.day19.model

/**
 * A category in which a [Part] is rated.
 */
enum class Category {

    /** Extremely cool looking. */
    X,

    /** Musical. */
    M,

    /** Aerodynamic. */
    A,

    /** Shiny. */
    S;

    companion object {

        /**
         * Parses the [input] character to a [Category] and returns it.
         *
         * @param input The character to parse.
         * @return The [Category] represented by the [input] character.
         * @throws IllegalArgumentException The [input] character does not represent a valid [Category].
         */
        fun parse(input: Char): Category = when (input) {
            'x' -> X
            'm' -> M
            'a' -> A
            's' -> S
            else -> throw IllegalArgumentException("Input is not a valid category")
        }
    }
}
