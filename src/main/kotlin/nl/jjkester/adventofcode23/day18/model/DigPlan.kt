package nl.jjkester.adventofcode23.day18.model

import nl.jjkester.adventofcode23.predef.algorithms.shoelace
import nl.jjkester.adventofcode23.predef.space.Coordinate2D
import nl.jjkester.adventofcode23.predef.space.by
import kotlin.math.roundToLong

/**
 * A plan for digging a hole.
 *
 * @property start Coordinate to start digging.
 * @property steps Instructions on sequential digging steps from the [start] coordinate.
 */
class DigPlan(private val start: Coordinate2D, private val steps: List<Step>) {

    private val corners by lazy {
        steps.runningFold(start) { previous, step ->
            previous.move(step.direction, step.meters)
        }
    }

    /**
     * The surface area of the hole to dig.
     */
    val size: Long by lazy {
        shoelace(
            corners,
            coordinatesAreAreas = true,
            includeBoundary = true
        ).roundToLong()
    }

    /**
     * Returns a new [DigPlan] where the [steps] are interpreted differently.
     */
    fun alternative(): DigPlan = DigPlan(start, steps.map { it.useColor() })

    private fun Coordinate2D.move(direction: Direction, meters: Int): Coordinate2D = when (direction) {
        Direction.Up -> copy(y = y - meters)
        Direction.Down -> copy(y = y + meters)
        Direction.Left -> copy(x = x - meters)
        Direction.Right -> copy(x = x + meters)
    }

    companion object {

        /**
         * Parses the [input] string to a [DigPlan] and returns it.
         *
         * @param input The input string to parse.
         * @return The [DigPlan] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [DigPlan].
         */
        fun parse(input: String): DigPlan = DigPlan(0 by 0, input.lines().map(Step::parse))
    }

    /**
     * Instructions on a single direction and distance to dig.
     *
     * @property direction The direction to dig in.
     * @property meters The distance to dig.
     * @property color The color to paint the edge.
     */
    data class Step(val direction: Direction, val meters: Int, val color: Int) {

        init {
            require(meters > 0) { "Unable to dig zero or a negative number of meters" }
        }

        /**
         * Returns a new [Step] where the [color] is interpreted as [meters] and [direction].
         */
        fun useColor(): Step = color.toString(16).let { hex ->
            copy(
                direction = Direction.entries[hex.takeLast(1).toInt(16)],
                meters = hex.dropLast(1).takeLast(5).toInt(16)
            )
        }

        companion object {
            private val regex = Regex("""([UDLR])\s*(\d+)\s*\(#([0-9a-f]{6})\)""")

            /**
             * Parses the [input] string to a [Step] and returns it.
             *
             * @param input The input string to parse.
             * @return The [Step] represented by the [input] string.
             * @throws IllegalArgumentException The [input] string does not represent a valid [Step].
             */
            fun parse(input: String): Step {
                val (direction, meters, color) = requireNotNull(regex.matchEntire(input)?.destructured) {
                    "Input is not a valid dig plan step"
                }

                return Step(Direction.parse(direction.single()), meters.toInt(), color.toInt(16))
            }
        }
    }

    /**
     * A direction to dig in.
     */
    enum class Direction {
        Right,
        Down,
        Left,
        Up;

        companion object {

            /**
             * Parses the [input] character to a [Direction] and returns it.
             *
             * @param input The input character to parse.
             * @return The [Direction] represented by the [input] character.
             * @throws IllegalArgumentException The [input] character does not represent a valid [Direction].
             */
            fun parse(input: Char): Direction = when (input) {
                'U' -> Up
                'D' -> Down
                'L' -> Left
                'R' -> Right
                else -> throw IllegalArgumentException("Input is not a valid direction")
            }
        }
    }
}
