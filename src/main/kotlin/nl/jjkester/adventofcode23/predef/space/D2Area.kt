package nl.jjkester.adventofcode23.predef.space

import nl.jjkester.adventofcode23.predef.size

/**
 * An area in a two-dimensional space.
 */
interface D2Area {

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
        get() = x.size * y.size

    /**
     * Returns whether this area is empty.
     *
     * @return Whether this area is empty.
     */
    fun isEmpty(): Boolean = x.isEmpty() || y.isEmpty()

    /**
     * Returns whether the [x] and [y] coordinate is within the bounds of this area.
     *
     * @param x Point on the x-axis.
     * @param y Point on the y-axis.
     * @return Whether the [x] and [y] coordinate is within the bounds of this area.
     */
    fun contains(x: Int, y: Int): Boolean = x in this.x && y in this.y
}

/**
 * Returns whether the [coordinate] is within the bounds of this area.
 *
 * @param coordinate Coordinate on the x- and y-axes.
 * @return Whether the [coordinate] is within the bounds of this area.
 */
operator fun D2Area.contains(coordinate: D2Coordinate): Boolean = contains(coordinate.x, coordinate.y)