package nl.jjkester.adventofcode23.predef.space

/**
 * A single point in a 2-dimensional space.
 *
 * @property x Point on the x-axis.
 * @property y Point on the y-axis.
 */
data class D2Coordinate(val x: Int, val y: Int)

/**
 * Creates and returns a [D2Coordinate] with the [x] and [y] coordinates.
 *
 * @param x Point on the x-axis.
 * @param y Point on the y-axis.
 * @return A [D2Coordinate] with the [x] and [y] coordinates.
 */
fun coordinateOf(x: Int, y: Int): D2Coordinate = D2Coordinate(x, y)

/**
 * Creates and returns a [D2Coordinate] with [this] value as [x][D2Coordinate.x] coordinate and the [other] value as
 * [y][D2Coordinate.y] coordinate.
 */
infix fun Int.by(other: Int): D2Coordinate = D2Coordinate(this, other)
