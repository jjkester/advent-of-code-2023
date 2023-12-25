package nl.jjkester.adventofcode23.predef.space

/**
 * A volume in a three-dimensional space.
 *
 * @property x Range on the x-axis this volume covers.
 * @property y Range on the y-axis this volume covers.
 * @property z Range on the z-axis this volume covers.
 * @constructor Creates a new volume covering where the [x], [y] and [z] ranges overlap.
 */
data class RangeVolume(
    override val x: IntRange,
    override val y: IntRange,
    override val z: IntRange
) : AbstractVolume() {

    override fun get(facing: Volume.Facing): Area = when(facing) {
        Volume.Facing.Top, Volume.Facing.Bottom -> RangeArea(x, z)
        Volume.Facing.Front, Volume.Facing.Back -> RangeArea(x, y)
        Volume.Facing.Left, Volume.Facing.Right -> RangeArea(z, y)
    }
}

/**
 * Creates and returns a volume covering the ranges of [x], [y] and [z] coordinates.
 *
 * @param x Value on the x-axis the area covers.
 * @param y Range on the y-axis the area covers.
 * @param z Range on the z-axis the area covers.
 * @return A volume covering the [x], [y] and [z] coordinates.
 */
fun volumeOf(x: IntRange, y: IntRange, z: IntRange): RangeVolume = RangeVolume(x, y, z)

/**
 * Creates and returns a volume covering the space between the [first] and [second] coordinate.
 *
 * @param first The first corner of the cuboid.
 * @param second The second corner of the cuboid.
 * @return A volume covering the space between the [first] and [second] coordinate.
 */
fun volumeOf(first: Coordinate3D, second: Coordinate3D): RangeVolume = RangeVolume(
    minOf(first.x, second.x)..maxOf(first.x, second.x),
    minOf(first.y, second.y)..maxOf(first.y, second.y),
    minOf(first.z, second.z)..maxOf(first.z, second.z)
)
