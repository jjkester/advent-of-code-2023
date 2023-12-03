package nl.jjkester.adventofcode23.day03

import nl.jjkester.adventofcode23.predef.space.*
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.contains

/**
 * Engine schematic containing numbers and symbols.
 */
class Schematic private constructor(
    private val data: D2Array<Int>
) : AbstractD2Area(), D2AreaMap<Char>, Iterable<Pair<D2Coordinate, Char>> {

    override val x: IntRange = 0..<data.shape[0]

    override val y: IntRange = 0..<data.shape[1]

    override fun get(x: Int, y: Int): Char = data[x, y].toChar()

    override fun contains(element: Char): Boolean = data.contains(element.code)

    override fun iterator(): Iterator<Pair<D2Coordinate, Char>> = iterator {
        y.forEach { y ->
            x.forEach { x ->
                yield(D2Coordinate(x, y) to get(x, y))
            }
        }
    }

    /**
     * Finds and returns the part numbers in this schematic.
     *
     * A part number is a number that is surrounded by at least one symbol other than `.`.
     *
     * @return A sequence of individual part numbers.
     */
    fun findPartNumbers(): Sequence<Int> = findNumbers()
        .mapNotNull { (area, number) ->
            val searchArea = area.scale(1).coerceIn(this)
            number.takeIf { searchArea.coordinates().any { coordinate -> this[coordinate].isSymbol() } }
        }

    /**
     * Finds and returns the gears in this schematic.
     *
     * A gear is a `*` symbol adjacent to precisely two part numbers.
     *
     * @return A sequence of corresponding gear numbers.
     */
    fun findGears(): Sequence<Pair<Int, Int>> {
        val numbersWithSearchAreas = findNumbers()
            .map { (area, number) -> area.scale(1) to number }
            .toList()

        return findStars()
            .mapNotNull { coordinate ->
                numbersWithSearchAreas
                    .filter { (searchArea, _) -> coordinate in searchArea }
                    .takeIf { it.size == 2 }
                    ?.map { (_, number) -> number }
                    ?.let { it.first() to it.last() }
            }
    }

    /**
     * Finds and returns the numbers in this schematic.
     * 
     * @return A sequence of numbers.
     */
    fun findNumbers() = sequence {
        val buffer = mutableListOf<Char>()

        y.forEach { line ->
            x.forEach { col ->
                val char = get(col, line)

                // Add digit to buffer as part of the number
                if (char.isNumber()) {
                    buffer.add(char)
                }

                // If the char is not a digit, or when this is the end of the line, yield the number
                if ((!char.isNumber() || col == x.last) && buffer.isNotEmpty()) {
                    val number = buffer.joinToString("").toInt()
                    val rectangle = Rectangle((col - buffer.size)..<col, line)

                    yield(rectangle to number)
                    buffer.clear()
                }
            }
        }
    }

    /**
     * Finds and returns the stars in this schematic.
     */
    fun findStars() = asSequence()
        .mapNotNull { (coordinate, char) -> coordinate.takeIf { char == '*' } }
    
    private fun Char.isNumber() = this in numberChars

    private fun Char.isSymbol() = this !in nonSymbols

    companion object {
        private val numberChars = "1234567890".toCharArray()
        private val nonSymbols = "1234567890.".toCharArray()

        /**
         * Parses the [input] string to a queryable [Schematic] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Schematic] represented by the [input].
         * @throws IllegalArgumentException The [input] string does not represent a valid [Schematic].
         */
        fun parse(input: String): Schematic {
            val lines = input.lines()
            val noLineSeparators = input.replace(Regex("\\s"), "")

            val width = lines.maxOf { it.length }
            val height = lines.size

            require(lines.all { it.length == width }) { "All lines in the input must be of equal width" }

            return Schematic(Multik.d2arrayIndices(width, height) { x, y -> noLineSeparators[x + width * y].code })
        }
    }
}
