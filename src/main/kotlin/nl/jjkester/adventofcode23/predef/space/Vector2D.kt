package nl.jjkester.adventofcode23.predef.space

import kotlin.math.abs

/**
 * A linear force in a 2-dimensional space.
 *
 * @property x Force in the direction of the x-axis.
 * @property y Force in the direction of the y-axis.
 */
data class Vector2D(val x: Int, val y: Int) {

    override fun toString(): String = "($x, $y)"
}

/**
 * Creates and returns a [Vector2D] with the [x] and [y] forces.
 *
 * @param x Force in the direction of the x-axis.
 * @param y Force in the direction of the y-axis.
 * @return A [Vector2D] with the [x] and [y] forces.
 */
fun vectorOf(x: Int, y: Int): Vector2D = Vector2D(x, y)

/**
 * Creates and returns a [Vector2D] from this coordinate relative to zero.
 */
fun Coordinate2D.toVector(): Vector2D = Vector2D(x, y)

operator fun Vector2D.plus(other: Vector2D): Vector2D = Vector2D(x + other.x, y + other.y)

operator fun Vector2D.minus(other: Vector2D): Vector2D = Vector2D(x - other.x, y - other.y)

operator fun Vector2D.unaryMinus(): Vector2D = Vector2D(-x, -y)

operator fun Vector2D.times(number: Int) = Vector2D(x * number, y * number)

fun abs(vector: Vector2D): Vector2D = Vector2D(abs(vector.x), abs(vector.y))

val Vector2D.absoluteValue: Vector2D
    get() = abs(this)
