package nl.jjkester.adventofcode23.day02.model

/**
 * A cube color.
 */
enum class Color {
    Red,
    Green,
    Blue;

    companion object {

        /**
         * Parses a cube color from the [input] string and returns the associated enum value.
         *
         * Valid [input] strings are 'red', 'green' and 'blue'.
         *
         * @param input The input string to parse.
         * @return The [Color] value represented by the [input].
         * @throws IllegalArgumentException The [input] does not represent a valid color.
         */
        fun parse(input: String): Color = requireNotNull(
            when (input) {
                "red" -> Red
                "green" -> Green
                "blue" -> Blue
                else -> null
            }
        ) { "Input is not a valid color" }
    }
}
