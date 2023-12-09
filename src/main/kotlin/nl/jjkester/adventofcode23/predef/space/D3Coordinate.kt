package nl.jjkester.adventofcode23.predef.space

/**
 * A single point in a 3-dimensional space.
 *
 * @property x Point on the x-axis.
 * @property y Point on the y-axis.
 * @property z Point on the z-axis.
 */
data class D3Coordinate(val x: Int, val y: Int, val z: Int)

/**
 * Creates and returns a [D2Coordinate] with the [x] and [y] coordinates.
 *
 * @param x Point on the x-axis.
 * @param y Point on the y-axis.
 * @param z Point on the z-axis.
 * @return A [D2Coordinate] with the [x], [y] and [z] coordinates.
 */
fun coordinateOf(x: Int, y: Int, z: Int): D3Coordinate = D3Coordinate(x, y, z)

/**
 * Creates and returns a [D3Coordinate] with [this] value as [x][D3Coordinate.x] and [y][D3Coordinate.y] coordinates and
 * the [other] value as [z][D3Coordinate.z] coordinate.
 */
infix fun D2Coordinate.by(other: Int): D3Coordinate = D3Coordinate(this.x, this.y, other)
