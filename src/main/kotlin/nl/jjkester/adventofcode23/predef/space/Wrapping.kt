package nl.jjkester.adventofcode23.predef.space

import nl.jjkester.adventofcode23.predef.ranges.wrap

/**
 * Returns a new coordinate within this area where each dimension of the [coordinate] has been reduced by a multiple of
 * the size of the area in that dimension. This can be leveraged for infinitely expanding areas.
 */
fun Area.wrap(coordinate: Coordinate2D): Coordinate2D =
    Coordinate2D(x.wrap(coordinate.x), y.wrap(coordinate.y))

/**
 * Returns a new coordinate within this area where each dimension of the [coordinate] has been reduced by a multiple of
 * the size of the area in that dimension. This can be leveraged for infinitely expanding areas.
 */
fun Volume.wrap(coordinate: Coordinate3D): Coordinate3D =
    Coordinate3D(x.wrap(coordinate.x), y.wrap(coordinate.y), z.wrap(coordinate.z))
