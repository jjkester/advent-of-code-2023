package nl.jjkester.adventofcode23.predef.space

/**
 * An area in two-dimensional space that holds data.
 *
 * @param T Type of data contained in this area map.
 */
interface AreaMap<out T> : Area {

    /**
     * Returns the element at the [x] and [y] coordinate.
     *
     * @param x Point on the x-axis.
     * @param y Point on the y-axis.
     * @return The element at the [x] and [y] coordinate.
     * @throws IndexOutOfBoundsException The [x] and [y] coordinate is out of bounds of this area.
     */
    operator fun get(x: Int, y: Int): T

    /**
     * Returns whether the [element] is contained in this area map.
     *
     * @param element The element to check.
     * @return Whether the [element] is contained in this area map.
     */
    operator fun contains(element: @UnsafeVariance T): Boolean
}

/**
 * Returns the element at the [coordinate].
 *
 * @param coordinate Coordinate on the x- and y-axes.
 * @return The element at the [coordinate].
 * @throws IndexOutOfBoundsException The [coordinate] is out of bounds of this area.
 */
operator fun <T> AreaMap<T>.get(coordinate: D2Coordinate): T = get(coordinate.x, coordinate.y)

/**
 * Returns the element at the [x] and [y] coordinate, or `null` if the coordinate is out of bounds of this area.
 *
 * @param x Point on the x-axis.
 * @param y Point on the y-axis.
 * @return The element at the [x] and [y] coordinate, or `null` if the coordinate is out of bounds of this area.
 */
fun <T> AreaMap<T>.getOrNull(x: Int, y: Int): T? = if (contains(x, y)) get(x, y) else null


/**
 * Returns the element at the [coordinate], or `null` if the coordinate is out of bounds of this area.
 *
 * @param coordinate Coordinate on the x- and y-axes.
 * @return The element at the [coordinate], or `null` if the coordinate is out of bounds of this area.
 */
fun <T> AreaMap<T>.getOrNull(coordinate: D2Coordinate): T? = getOrNull(coordinate.x, coordinate.y)
