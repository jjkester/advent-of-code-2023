package nl.jjkester.adventofcode23.predef.space

import nl.jjkester.adventofcode23.predef.ranges.coerceIn

/**
 * An area in a two-dimensional space.
 *
 * @property x Range on the x-axis this area covers.
 * @property y Range on the y-axis this area covers.
 * @constructor Creates a new area covering where the [x] and [y] ranges overlap.
 */
data class RangeArea(override val x: IntRange, override val y: IntRange) : AbstractArea()

/**
 * Creates and returns an area covering the ranges of [x] and [y] coordinates.
 *
 * @param x Value on the x-axis the area covers.
 * @param y Range on the y-axis the area covers.
 * @return An area covering the [x] and [y] coordinates.
 */
fun areaOf(x: IntRange, y: IntRange): RangeArea = RangeArea(x, y)

/**
 * Creates and returns an area covering where the [x] line covers the [y] range.
 *
 * @param x Value on the x-axis the area covers.
 * @param y Range on the y-axis the area covers.
 * @return An area covering the [x] and [y] coordinates.
 */
fun areaOf(x: Int, y: IntRange): RangeArea = RangeArea(x..x, y)

/**
 * Creates and returns an area covering where the [y] line covers the [x] range.
 *
 * @param x Range on the x-axis the area covers.
 * @param y Value on the y-axis the area covers.
 * @return An area covering the [x] and [y] coordinates.
 */
fun areaOf(x: IntRange, y: Int): RangeArea = RangeArea(x, y..y)

/**
 * Creates and returns an area covering where the [x] line crosses the [y] line.
 *
 * @param x Value on the x-axis the area covers.
 * @param y Value on the y-axis the area covers.
 * @return An area covering the [x] and [y] coordinates.
 */
fun areaOf(x: Int, y: Int): RangeArea = RangeArea(x..x, y..y)

/**
 * Returns a new area that is evenly scaled by the [amount]. The area is shrunk or expanded by the [amount] in
 * each direction. A negative [amount] will shrink the area. The [x][RangeArea.x] and [y][RangeArea.y] dimensions
 * of the resulting area will be increased or decreased by double the [amount].
 *
 * Given an area with a top left position of `4,4` and a bottom right position of `6,6`:
 * - providing an [amount] of `2` will result in an area of `2,2;8,8`;
 * - providing an [amount] of `-1` will result in an area of `5,5;5,5`, covering a single coordinate;
 * - providing an [amount] of `-2` will result in an empty area.
 *
 * @param amount The amount to adjust the size of the area with in each direction.
 * @return A new area with the scaled size.
 */
fun RangeArea.scale(amount: Int): RangeArea = if (amount == 0) {
    this
} else {
    RangeArea(x.first - amount..x.last + amount, y.first - amount..y.last + amount)
}

/**
 * Returns a new area that represents the overlap between the original and [other] areas.
 *
 * @param other The area to limit the size to.
 * @return A new area fit to the bounds of both this and the [other] area.
 */
fun RangeArea.coerceIn(other: Area): RangeArea = coerceIn(other.x, other.y)

/**
 * Returns a new area that represents the overlap between the original area and the area from the [x] and
 * [y] ranges.
 *
 * @param x Range on the x-axis to limit the size to.
 * @param y Range on the y-axis to limit the size to.
 * @return A new area fit to the bounds of this area and the [x] and [y] ranges.
 */
fun RangeArea.coerceIn(x: IntRange, y: IntRange): RangeArea = RangeArea(this.x.coerceIn(x), this.y.coerceIn(y))
