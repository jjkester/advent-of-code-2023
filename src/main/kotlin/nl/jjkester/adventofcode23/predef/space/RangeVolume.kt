package nl.jjkester.adventofcode23.predef.space

/**
 * A volume in a three-dimensional space.
 *
 * @property x Range on the x-axis this volume covers.
 * @property y Range on the y-axis this volume covers.
 * @property z Range on the z-axis this volume covers.
 * @constructor Creates a new volume covering where the [x], [y] and [z] ranges overlap.
 */
data class RangeVolume(override val x: IntRange, override val y: IntRange, override val z: IntRange) : AbstractVolume()
