package nl.jjkester.adventofcode23.predef.space

/**
 * An area in three-dimensional space.
 */
interface Volume {

    /**
     * Range on the x-axis that is covered by this area.
     */
    val x: IntRange

    /**
     * Range on the y-axis that is covered by this area.
     */
    val y: IntRange

    /**
     * Range on the z-axis that is covered by this area.
     */
    val z: IntRange

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
     * Returns whether the [x], [y] and [z] coordinate is within the bounds of this area.
     *
     * @param x Point on the x-axis.
     * @param y Point on the y-axis.
     * @param z Point on the z-axis.
     * @return Whether the [x], [y] and [z] coordinate is within the bounds of this area.
     */
    fun contains(x: Int, y: Int, z: Int): Boolean

    /**
     * Returns the area covering the [facing].
     *
     * @param facing The facing of the volume to get.
     * @return The area covering the [facing].
     */
    operator fun get(facing: Facing): Area

    /**
     * Identifier for a face of a three-dimensional volume.
     */
    enum class Facing {

        /**
         * Top facing of a volume.
         *
         * Covers the [Volume.x] and [Volume.z] axes at the maximum of the [Volume.y] axis.
         */
        Top,

        /**
         * Front facing of a volume.
         *
         * Covers the [Volume.x] and [Volume.y] axes at the minimum of the [Volume.z] axis.
         */
        Front,

        /**
         * Left facing of a volume.
         *
         * Covers the [Volume.y] and [Volume.z] axes at the minimum of the [Volume.x] axis.
         */
        Left,

        /**
         * Right facing of a volume.
         *
         * Covers the [Volume.y] and [Volume.z] axes at the maximum of the [Volume.x] axis.
         */
        Right,

        /**
         * Back facing of a volume.
         *
         * Covers the [Volume.x] and [Volume.y] axes at the maximum of the [Volume.x] axis.
         */
        Back,

        /**
         * Bottom facing of a volume.
         *
         * Covers the [Volume.x] and [Volume.z] axes at the minimum of the [Volume.y] axis.
         */
        Bottom
    }
}

/**
 * Returns whether the [coordinate] is within the bounds of this volume.
 *
 * @param coordinate Coordinate on the x-, y- and z-axes.
 * @return Whether the [coordinate] is within the bounds of this volume.
 */
operator fun Volume.contains(coordinate: Coordinate3D): Boolean = contains(coordinate.x, coordinate.y, coordinate.z)
