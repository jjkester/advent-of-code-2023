package nl.jjkester.adventofcode23.predef.space

/**
 * A volume in three-dimensional space that holds data.
 *
 * @param T Type of data contained in this volume map.
 */
interface VolumeMap<out T> : Volume {

    /**
     * Returns the element at the [x], [y] and [z] coordinate.
     *
     * @param x Point on the x-axis.
     * @param y Point on the y-axis.
     * @param z Point on the z-axis.
     * @return The element at the [x], [y] and [z] coordinate.
     * @throws IndexOutOfBoundsException The [x], [y] and [z] coordinate is out of bounds of this volume.
     */
    operator fun get(x: Int, y: Int, z: Int): T

    /**
     * Returns whether the [element] is contained in this volume map.
     *
     * @param element The element to check.
     * @return Whether the [element] is contained in this volume map.
     */
    operator fun contains(element: @UnsafeVariance T): Boolean

    /**
     * Returns the area map covering the [facing].
     *
     * @param facing The facing of the volume to get.
     * @return The area map covering the [facing].
     */
    override fun get(facing: Volume.Facing): AreaMap<T>
}

/**
 * Returns the element at the [coordinate].
 *
 * @param coordinate Coordinate on the x- and y-axes.
 * @return The element at the [coordinate].
 * @throws IndexOutOfBoundsException The [coordinate] is out of bounds of this volume.
 */
operator fun <T> VolumeMap<T>.get(coordinate: D3Coordinate): T = get(coordinate.x, coordinate.y, coordinate.z)

/**
 * Returns the element at the [x], [y] and [z] coordinate, or `null` if the coordinate is out of bounds of this volume.
 *
 * @param x Point on the x-axis.
 * @param y Point on the y-axis.
 * @param z Point on the z-axis.
 * @return The element at the [x], [y] and [z] coordinate, or `null` if the coordinate is out of bounds of this volume.
 */
fun <T> VolumeMap<T>.getOrNull(x: Int, y: Int, z: Int): T? = if (contains(x, y, z)) get(x, y, z) else null


/**
 * Returns the element at the [coordinate], or `null` if the coordinate is out of bounds of this volume.
 *
 * @param coordinate Coordinate on the x- and y-axes.
 * @return The element at the [coordinate], or `null` if the coordinate is out of bounds of this volume.
 */
fun <T> VolumeMap<T>.getOrNull(coordinate: D3Coordinate): T? = getOrNull(coordinate.x, coordinate.y, coordinate.z)
