package nl.jjkester.adventofcode23.day24.model

/**
 * A hailstone in 3-dimensional space, moving in a linear trajectory with fixed speed.
 *
 * @property position The position of the hailstone.
 * @property trajectory The trajectory of the hailstone in time.
 */
data class Hailstone3D(val position: Vector, val trajectory: Vector) {

    /**
     * Returns a 2-dimensional representation of this hailstone, disregarding the z-axis.
     */
    fun to2D(): Hailstone2D = Hailstone2D(
        Hailstone2D.Vector(position.x, position.y),
        Hailstone2D.Vector(trajectory.x, trajectory.y)
    )

    companion object {
        private val regex = Regex("""(-?\d+),\s*(-?\d+),\s*(-?\d+)\s*@\s*(-?\d+),\s*(-?\d+),\s*(-?\d+)""")

        fun parse(input: String): Hailstone3D {
            val result = requireNotNull(regex.matchEntire(input)) { "Input is not a valid hailstone" }
            val (px, py, pz, vx, vy, vz) = result.destructured

            return Hailstone3D(
                Vector(px.toDouble(), py.toDouble(), pz.toDouble()),
                Vector(vx.toDouble(), vy.toDouble(), vz.toDouble())
            )
        }
    }

    data class Vector(val x: Double, val y: Double, val z: Double)
}
