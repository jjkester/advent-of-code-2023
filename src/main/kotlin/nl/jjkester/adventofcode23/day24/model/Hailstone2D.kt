package nl.jjkester.adventofcode23.day24.model

/**
 * A hailstone in 2-dimensional space, moving in a linear trajectory with fixed speed.
 *
 * @property position The position of the hailstone.
 * @property trajectory The trajectory of the hailstone in time.
 */
data class Hailstone2D(val position: Vector, val trajectory: Vector) {

    private val m by lazy(LazyThreadSafetyMode.NONE) {
        trajectory.y / trajectory.x
    }

    private val c by lazy(LazyThreadSafetyMode.NONE) {
        -(m * position.x - position.y)
    }

    /**
     * Returns the point where the trajectories of this hailstone and the [other] hailstone will intersect, or `null`
     * when the trajectories never intersect.
     */
    fun intersectionWith(other: Hailstone2D): Vector? = if (m == other.m) {
        null
    } else {
        Vector(
            x = (-other.c + c) / (-m + other.m),
            y = (c * other.m - other.c * m) / (-m + other.m)
        )
    }

    /**
     * Returns the point where the trajectories of this hailstone and the [other] hailstone will intersect in the
     * future, or `null` when the trajectories never intersect or have intersected in the past.
     */
    fun futureIntersectionWith(other: Hailstone2D): Vector? = intersectionWith(other)?.takeIf { intersection ->
        val thisFuture = (intersection.x - position.x) / trajectory.x > 0
        val otherFuture = (intersection.x - other.position.x) / other.trajectory.x > 0
        thisFuture && otherFuture
    }

    data class Vector(val x: Double, val y: Double)
}
