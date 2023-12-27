package nl.jjkester.adventofcode23.predef.space

import kotlin.math.abs

/**
 * A linear force in a 3-dimensional space.
 *
 * @property x Force in the direction of the x-axis.
 * @property y Force in the direction of the y-axis.
 * @property z Force in the direction of the z-axis.
 */
data class Vector3D(val x: Int, val y: Int, val z: Int) {

    override fun toString(): String = "($x, $y, $z)"
}

/**
 * Creates and returns a [Vector3D] with the [x], [y] and [z] forces.
 *
 * @param x Force in the direction of the x-axis.
 * @param y Force in the direction of the y-axis.
 * @param z Force in the direction of the z-axis.
 * @return A [Vector3D] with the [x], [y] and [z] forces.
 */
fun vectorOf(x: Int, y: Int, z: Int): Vector3D = Vector3D(x, y, z)

/**
 * Creates and returns a [Vector3D] from this coordinate relative to zero.
 */
fun Coordinate3D.toVector(): Vector3D = Vector3D(x, y, z)

operator fun Vector3D.plus(other: Vector3D): Vector3D = Vector3D(x + other.x, y + other.y, z + other.z)

operator fun Vector3D.minus(other: Vector3D): Vector3D = Vector3D(x - other.x, y - other.y, z - other.z)

operator fun Vector3D.unaryMinus(): Vector3D = Vector3D(-x, -y, -z)

operator fun Vector3D.times(number: Int) = Vector3D(x * number, y * number, z * number)

fun abs(vector: Vector3D): Vector3D = Vector3D(abs(vector.x), abs(vector.y), abs(vector.z))

val Vector3D.absoluteValue: Vector3D
    get() = abs(this)
