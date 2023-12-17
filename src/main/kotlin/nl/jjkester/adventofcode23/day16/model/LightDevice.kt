package nl.jjkester.adventofcode23.day16.model

/**
 * A device that transforms a beam of light.
 */
enum class LightDevice {

    /** An empty space, light is not affected. */
    None,

    /** A diagonal mirror from the upper left to the lower right. */
    LeftMirror,

    /** A diagonal mirror from the upper right to the lower left. */
    RightMirror,

    /** A splitter placed horizontally, will distribute the light horizontally when hit from a vertical direction. */
    HorizontalSplitter,

    /** A splitter placed vertically, will distribute the light vertically when hit from a horizontal direction. */
    VerticalSplitter;

    fun apply(direction: Direction): Sequence<Direction> = when (this) {
        None -> sequenceOf(direction)
        LeftMirror -> sequenceOf(
            when (direction) {
                Direction.North -> Direction.West
                Direction.East -> Direction.South
                Direction.South -> Direction.East
                Direction.West -> Direction.North
            }
        )

        RightMirror -> sequenceOf(
            when (direction) {
                Direction.North -> Direction.East
                Direction.East -> Direction.North
                Direction.South -> Direction.West
                Direction.West -> Direction.South
            }
        )

        HorizontalSplitter -> when (direction) {
            Direction.North, Direction.South -> sequenceOf(Direction.East, Direction.West)
            Direction.East, Direction.West -> sequenceOf(direction)
        }

        VerticalSplitter -> when (direction) {
            Direction.North, Direction.South -> sequenceOf(direction)
            Direction.East, Direction.West -> sequenceOf(Direction.North, Direction.South)
        }
    }

    override fun toString(): String = when (this) {
        None -> "·"
        LeftMirror -> "╲"
        RightMirror -> "╱"
        HorizontalSplitter -> "─"
        VerticalSplitter -> "│"
    }

    companion object {

        /**
         * Parses the [input] character to a [LightDevice] and returns it.
         *
         * @param input The input character to parse.
         * @return The [LightDevice] represented by the [input] character.
         * @throws IllegalArgumentException The [input] character does not represent a valid [LightDevice].
         */
        fun parse(input: Char): LightDevice = when (input) {
            '.' -> None
            '\\' -> LeftMirror
            '/' -> RightMirror
            '-' -> HorizontalSplitter
            '|' -> VerticalSplitter
            else -> throw IllegalArgumentException("Input is not a valid light device")
        }
    }
}
