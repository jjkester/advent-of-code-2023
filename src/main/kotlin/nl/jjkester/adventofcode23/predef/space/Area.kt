package nl.jjkester.adventofcode23.predef.space

import nl.jjkester.adventofcode23.predef.ranges.overlaps

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
operator fun Area.contains(coordinate: Coordinate2D): Boolean = contains(coordinate.x, coordinate.y)

/**
 * Returns whether the [other] area overlaps with this area.
 *
 * @param other The area to check overlap with.
 * @return Whether the [other] area overlaps with this area.
 */
infix fun Area.overlaps(other: Area): Boolean = x.overlaps(other.x) && y.overlaps(other.y)
