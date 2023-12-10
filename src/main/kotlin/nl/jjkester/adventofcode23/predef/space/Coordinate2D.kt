package nl.jjkester.adventofcode23.predef.space

/**
 * A single point in a 2-dimensional space.
 *
 * @property x Point on the x-axis.
 * @property y Point on the y-axis.
 */
data class Coordinate2D(val x: Int, val y: Int) {

    override fun toString(): String = "($x, $y)"
}

/**
 * Creates and returns a [Coordinate2D] with the [x] and [y] coordinates.
 *
 * @param x Point on the x-axis.
 * @param y Point on the y-axis.
 * @return A [Coordinate2D] with the [x] and [y] coordinates.
 */
fun coordinateOf(x: Int, y: Int): Coordinate2D = Coordinate2D(x, y)

/**
 * Creates and returns a [Coordinate2D] with [this] value as [x][Coordinate2D.x] coordinate and the [other] value as
 * [y][Coordinate2D.y] coordinate.
 */
infix fun Int.by(other: Int): Coordinate2D = Coordinate2D(this, other)
