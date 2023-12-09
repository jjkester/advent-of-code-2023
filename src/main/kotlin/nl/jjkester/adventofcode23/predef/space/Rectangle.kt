package nl.jjkester.adventofcode23.predef.space

import nl.jjkester.adventofcode23.predef.ranges.coerceIn

/**
 * An area in a two-dimensional space.
 *
 * @property x Range on the x-axis this rectangle covers.
 * @property y Range on the y-axis this rectangle covers.
 * @constructor Creates a new rectangle covering an area where the [x] and [y] ranges overlap.
 */
data class Rectangle(override val x: IntRange, override val y: IntRange) : AbstractArea() {

    /**
     * Creates a new rectangle covering an area where the [x] line covers the [y] range.
     *
     * @param x Value on the x-axis  this rectangle covers.
     * @param y Range on the y-axis this rectangle covers.
     */
    constructor(x: Int, y: IntRange) : this(x..x, y)

    /**
     * Creates a new rectangle covering an area where the [y] line covers the [x] range.
     *
     * @param x Range on the x-axis this rectangle covers.
     * @param y Value on the y-axis this rectangle covers.
     */
    constructor(x: IntRange, y: Int) : this(x, y..y)

    /**
     * Creates a new rectangle covering an area where the [x] line crosses the [y] line.
     *
     * @param x Value on the x-axis this rectangle covers.
     * @param y Value on the y-axis this rectangle covers.
     */
    constructor(x: Int, y: Int) : this(x..x, y..y)
}

/**
 * Returns a new rectangle that is evenly scaled by the [amount]. The rectangle is shrunk or expanded by the [amount] in
 * each direction. A negative [amount] will shrink the rectangle. The [x][Rectangle.x] and [y][Rectangle.y] dimensions
 * of the resulting rectangle will be increased or decreased by double the [amount].
 *
 * Given a rectangle with a top left position of `4,4` and a bottom right position of `6,6`:
 * - providing an [amount] of `2` will result in a rectangle of `2,2;8,8`;
 * - providing an [amount] of `-1` will result in a rectangle of `5,5;5,5`, covering a single coordinate;
 * - providing an [amount] of `-2` will result in an empty rectangle.
 *
 * @param amount The amount to adjust the size of the rectangle with in each direction.
 * @return A new rectangle with the scaled size.
 */
fun Rectangle.scale(amount: Int): Rectangle = if (amount == 0) {
    this
} else {
    Rectangle(x.first - amount..x.last + amount, y.first - amount..y.last + amount)
}

/**
 * Returns a new rectangle that represents the overlap between the original and [other] rectangles.
 *
 * @param other The area to limit the size to.
 * @return A new rectangle fit to the bounds of both this and the [other] rectangle.
 */
fun Rectangle.coerceIn(other: Area): Rectangle = coerceIn(other.x, other.y)

/**
 * Returns a new rectangle that represents the overlap between the original rectangle and the rectangle from the [x] and
 * [y] ranges.
 *
 * @param x Range on the x-axis to limit the size to.
 * @param y Range on the y-axis to limit the size to.
 * @return A new rectangle fit to the bounds of this rectangle and the [x] and [y] ranges.
 */
fun Rectangle.coerceIn(x: IntRange, y: IntRange): Rectangle = Rectangle(this.x.coerceIn(x), this.y.coerceIn(y))
