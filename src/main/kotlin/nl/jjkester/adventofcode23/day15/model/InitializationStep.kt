package nl.jjkester.adventofcode23.day15.model

/**
 * A single instruction to [Add] or [Remove] a lens.
 */
sealed class InitializationStep {

    /**
     * Label of the lens the instruction is for.
     */
    abstract val label: String

    /**
     * Instruction to add a lens to a box.
     *
     * @property label The label to add to the lens. This label is also used to find the right box. If the box already
     * contains a lens with the same label, the new lens replaces the old one.
     * @property focalLength The focal length of the lens to use.
     */
    data class Add(override val label: String, val focalLength: Int) : InitializationStep()

    /**
     * Instruction to remove a lens from a box.
     *
     * @property label The label of the lens to remove. This label is also used to find the right box.
     */
    data class Remove(override val label: String) : InitializationStep()

    companion object {
        private val regex = Regex("""(\w+)(-|=\d+)""")

        /**
         * Parses the [input] string to an [InitializationStep] and returns it.
         *
         * @param input The input string to parse.
         * @return The [InitializationStep] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [InitializationStep].
         */
        fun parse(input: String): InitializationStep {
            val result = requireNotNull(regex.matchEntire(input)) { "Input is not a valid step" }
            val (label, details) = result.destructured

            return when (details[0]) {
                '-' -> Remove(label)
                '=' -> Add(label, details.drop(1).toInt())
                else -> throw IllegalArgumentException("Input is not a valid step")
            }
        }
    }
}
