package nl.jjkester.adventofcode23.predef.space

/**
 * An area in a two-dimensional space.
 */
interface Area {

    /**
     * Range on the x-axis that is covered by this area.
     */
    val x: IntRange

    /**
     * Range on the y-axis that is covered by this area.
     */
    val y: IntRange

    /**
     * Size of the area.
     */
    val size: Int

    /**
     * Returns whether this area is empty.
     *
     * @return Whether this area is empty.
     */
    fun isEmpty(): Boolean

    /**
     * Returns whether the [x] and [y] coordinate is within the bounds of this area.
     *
     * @param x Point on the x-axis.
     * @param y Point on the y-axis.
     * @return Whether the [x] and [y] coordinate is within the bounds of this area.
     */
    fun contains(x: Int, y: Int): Boolean
}

/**
 * Returns whether the [coordinate] is within the bounds of this area.
 *
 * @param coordinate Coordinate on the x- and y-axes.
 * @return Whether the [coordinate] is within the bounds of this area.
 */
operator fun Area.contains(coordinate: D2Coordinate): Boolean = contains(coordinate.x, coordinate.y)
