package nl.jjkester.adventofcode23.day10.model

/**
 * A pipe segment in a grid.
 */
enum class Pipe(private val puzzleChar: Char, private val printChar: Char) {
    NorthSouth('|', '│'),
    EastWest('-', '─'),
    NorthEast('L', '└'),
    NorthWest('J', '┘'),
    SouthWest('7', '┐'),
    SouthEast('F', '┌'),
    Start('S', 'S');

    /**
     * Returns the direction in which this pipe continues after entering it from the [origin] direction. A return value
     * of `null` indicates that this pipe could not be entered.
     *
     * The return value for the special [Start] pipe is the [origin] direction, assuming that the direction is not
     * changed.
     *
     * @param origin Direction the pipe is entered from.
     * @return The outgoing direction of the pipe or `null` when the pipe could not be entered from the [origin].
     */
    fun continuesTo(origin: Direction): Direction? = when (this) {
        NorthSouth -> when (origin) {
            Direction.North -> Direction.North
            Direction.South -> Direction.South
            else -> null
        }
        EastWest -> when (origin) {
            Direction.East -> Direction.East
            Direction.West -> Direction.West
            else -> null
        }
        NorthEast -> when (origin) {
            Direction.South -> Direction.East
            Direction.West -> Direction.North
            else -> null
        }
        NorthWest -> when (origin) {
            Direction.South -> Direction.West
            Direction.East -> Direction.North
            else -> null
        }
        SouthWest -> when (origin) {
            Direction.North -> Direction.West
            Direction.East -> Direction.South
            else -> null
        }
        SouthEast -> when (origin) {
            Direction.North -> Direction.East
            Direction.West -> Direction.South
            else -> null
        }
        Start -> origin
    }

    /**
     * Formats and returns this pipe as character.
     */
    fun toChar(): Char = printChar

    companion object {
        private val byPuzzleChar = entries.associateBy(Pipe::puzzleChar)

        /**
         * Parses the [input] character to a [Pipe] and returns it.
         *
         * @param input The character to parse.
         * @return The [Pipe] represented by the [input] character.
         * @throws IllegalArgumentException The [input] character does not represent a [Pipe].
         */
        fun parse(input: Char): Pipe = requireNotNull(byPuzzleChar[input]) { "Input does not represent a pipe" }
    }
}
