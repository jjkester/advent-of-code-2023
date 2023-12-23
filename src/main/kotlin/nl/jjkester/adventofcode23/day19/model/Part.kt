package nl.jjkester.adventofcode23.day19.model

/**
 * A part that is rated in the four categories [x], [m], [a] and [s].
 */
data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {

    /**
     * The total rating of this part.
     */
    val rating: Long
        get() = x.toLong() + m.toLong() + a.toLong() + s.toLong()

    /**
     * Returns the rating of this part in the [category].
     */
    operator fun get(category: Category): Int = when (category) {
        Category.X -> x
        Category.M -> m
        Category.A -> a
        Category.S -> s
    }

    companion object {
        private val regex = Regex(
            """\{\s*x\s*=\s*(\d+)\s*,\s*m\s*=\s*(\d+)\s*,\s*a\s*=\s*(\d+)\s*,\s*s\s*=\s*(\d+)\s*}"""
        )

        /**
         * Parses the [input] string to a [Part] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Part] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [Part].
         */
        fun parse(input: String): Part {
            val (x, m, a, s) = requireNotNull(regex.matchEntire(input)?.destructured) { "Input is not a valid part" }
            return Part(x.toInt(), m.toInt(), a.toInt(), s.toInt())
        }
    }
}
