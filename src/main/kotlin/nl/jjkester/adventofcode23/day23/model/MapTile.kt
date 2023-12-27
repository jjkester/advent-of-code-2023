package nl.jjkester.adventofcode23.day23.model

/**
 * A tile in a [TrailMap].
 */
sealed class MapTile {

    abstract fun toChar(): Char

    /**
     * A path that can be walked in any direction.
     */
    data object Path : MapTile() {

        override fun toChar(): Char = '.'
    }

    /**
     * A forest that is not accessible.
     */
    data object Forest : MapTile() {

        override fun toChar(): Char = '#'
    }

    /**
     * A slope that can only be walked in the downhill direction.
     *
     * @property downTo The direction of the slope when going downhill.
     */
    data class Slope(val downTo: Direction) : MapTile() {

        override fun toChar(): Char = when (downTo) {
            Direction.North -> '^'
            Direction.East -> '>'
            Direction.South -> 'v'
            Direction.West -> '<'
        }
    }

    companion object {

        /**
         * Parses the [input] character to a [Path], [Forest] or [Slope] and returns it.
         *
         * @param input The character to parse.
         * @return The [MapTile] represented by the [input] character.
         * @throws IllegalArgumentException The [input] character does not represent a valid [MapTile].
         */
        fun parse(input: Char): MapTile = when (input) {
            '.' -> Path
            '#' -> Forest
            '^' -> Slope(Direction.North)
            '>' -> Slope(Direction.East)
            'v' -> Slope(Direction.South)
            '<' -> Slope(Direction.West)
            else -> throw IllegalArgumentException("Input is not a valid map tile")
        }
    }
}
