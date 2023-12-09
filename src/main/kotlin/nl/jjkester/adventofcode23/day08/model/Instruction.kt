package nl.jjkester.adventofcode23.day08.model

/**
 * An instruction on a direction to take.
 */
enum class Instruction {

    /** Instruction to take the left path. */
    Left,

    /** Instruction to take the right path. */
    Right;

    companion object {

        /**
         * Parses the [input] character to an [Instruction] and returns it.
         *
         * @param input Input character to parse.
         * @return The [Instruction] represented by the [input] character.
         * @throws IllegalArgumentException The [input] character is not a valid [Instruction].
         */
        fun parse(input: Char): Instruction = when (input) {
            'L' -> Left
            'R' -> Right
            else -> throw IllegalArgumentException("The input is not a valid instruction")
        }
    }
}
