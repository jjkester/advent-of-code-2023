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
