package nl.jjkester.adventofcode23.day02.model

/**
 * A draw of colored cubes, consisting of any number of [red], [green] and [blue] cubes.
 *
 * @property red The amount of red cubes in the draw. Must be a non-negative integer.
 * @property green The amount of green cubes in the draw. Must be a non-negative integer.
 * @property blue The amount of blue cubes in the draw. Must be a non-negative integer.
 */
data class Cubes(val red: Int, val green: Int, val blue: Int) {

    /**
     * The power of this draw of cubes. The power is defined as the amount of each color of cubes multiplied together.
     */
    val power: Int by lazy(LazyThreadSafetyMode.NONE) {
        red * green * blue
    }

    /**
     * Creates a new [Cubes] instance from the map of [Color] values and amounts. When a [Color] is absent from the
     * [cubes] map, the amount is set to zero.
     */
    constructor(cubes: Map<Color, Int>) : this(
        red = cubes[Color.Red] ?: 0,
        green = cubes[Color.Green] ?: 0,
        blue = cubes[Color.Blue] ?: 0,
    )

    init {
        require(red >= 0) { "Amount of red cubes cannot be negative" }
        require(green >= 0) { "Amount of green cubes cannot be negative" }
        require(blue >= 0) { "Amount of blue cubes cannot be negative" }
    }

    companion object {
        private val regex = Regex("""(?<amount>\d+)\s*(?<color>blue|red|green)""")

        /**
         * Parses a draw of cubes from the [input] string and returns it.
         *
         * A valid [input] consists of a comma-separated list of a number and color (for example, '3 red').
         *
         * @param input The input string to parse.
         * @return The draw of cubes containing the amount of cubes of each color as listed in the [input].
         * @throws IllegalArgumentException The [input] does not represent a valid draw of cubes.
         */
        fun parse(input: String): Cubes = Cubes(
            input.split(",").associate { group ->
                val result = requireNotNull(regex.matchEntire(group.trim())) { "Input is not a valid list of revealed cubes" }

                val amount = requireNotNull(result.groups["amount"]?.value?.toIntOrNull())
                val color = Color.parse(result.groups["color"]?.value.orEmpty())

                color to amount
            }
        )
    }
}
