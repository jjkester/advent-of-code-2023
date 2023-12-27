package nl.jjkester.adventofcode23.predef.space

/**
 * A single point in a 3-dimensional space.
 *
 * @property x Point on the x-axis.
 * @property y Point on the y-axis.
 * @property z Point on the z-axis.
 */
data class Coordinate3D(val x: Int, val y: Int, val z: Int) {

    override fun toString(): String = "($x, $y, $z)"
}

/**
 * Creates and returns a [Coordinate2D] with the [x] and [y] coordinates.
 *
 * @param x Point on the x-axis.
 * @param y Point on the y-axis.
 * @param z Point on the z-axis.
 * @return A [Coordinate2D] with the [x], [y] and [z] coordinates.
 */
fun coordinateOf(x: Int, y: Int, z: Int): Coordinate3D = Coordinate3D(x, y, z)

/**
 * Creates and returns a [Coordinate3D] with [this] value as [x][Coordinate3D.x] and [y][Coordinate3D.y] coordinates and
 * the [other] value as [z][Coordinate3D.z] coordinate.
 */
infix fun Coordinate2D.by(other: Int): Coordinate3D = Coordinate3D(this.x, this.y, other)

operator fun Coordinate3D.plus(vector: Vector3D): Coordinate3D =
    Coordinate3D(x + vector.x, y + vector.y, z + vector.z)
