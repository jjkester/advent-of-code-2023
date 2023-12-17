package nl.jjkester.adventofcode23.day17.model

/**
 * A direction of travel.
 */
enum class Direction {
    North,
    East,
    South,
    West;

    /**
     * The inverse direction of this direction.
     */
    fun inverse(): Direction = when (this) {
        North -> South
        East -> West
        South -> North
        West -> East
    }
}
