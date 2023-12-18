package nl.jjkester.adventofcode23.predef.algorithms

import nl.jjkester.adventofcode23.predef.space.Coordinate2D
import kotlin.math.abs

/**
 * Shoelace algorithm to compute the surface area given a list of coordinates.
 *
 * The [coordinates] **must** be iterated in clockwise or counterclockwise order, and contain at least two coordinates.
 * None of the [coordinates] may be a negative number.
 *
 * @param coordinates The coordinates of the area in clockwise or counterclockwise order.
 * @param coordinatesAreAreas Whether the [coordinates] must be treated as having areas instead of points.
 * @param includeBoundary Whether to include the boundary in the surface area. Only applies when [coordinatesAreAreas]
 * is set to `true`.
 * @return The surface area of the area denoted by the list of coordinates.
 * @throws IllegalArgumentException The list of [coordinates] is too small.
 */
fun shoelace(
    coordinates: List<Coordinate2D>,
    coordinatesAreAreas: Boolean = true,
    includeBoundary: Boolean = true
): Double {
    require(coordinates.size > 2) { "Unable to compute area of less than three coordinates" }
    require(coordinates.none { it.x < 0 || it.y < 0 }) { "Unable to compute area of negative coordinates" }

    val coordinatePairs = coordinates.windowed(2) + listOf(listOf(coordinates.first(), coordinates.last()))

    var boundary = 0.0
    var internal = 0.0

    for ((first, second) in coordinatePairs) {
        boundary += abs(gcd(first.x - second.x, first.y - second.y))
        internal += first.x.toDouble() * second.y - second.x.toDouble() * first.y
    }

    val boundaryCorrection = when {
        coordinatesAreAreas && includeBoundary -> boundary / 2 + 1
        coordinatesAreAreas && !includeBoundary -> -boundary / 2 + 1
        else -> 0.0
    }

    return abs(internal / 2) + boundaryCorrection
}
