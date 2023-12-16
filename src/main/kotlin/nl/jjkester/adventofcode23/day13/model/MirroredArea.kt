package nl.jjkester.adventofcode23.day13.model

import nl.jjkester.adventofcode23.predef.space.*
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.set

/**
 * An area of ash and rocks containing a mirror edge.
 */
class MirroredArea private constructor(
    data: D2Array<Int>
) : EnumMultikAreaMap<MirroredArea.Content>(Content.entries, data) {

    /**
     * Finds the vertical reflections and returns the column numbers where the reflections occur.
     *
     * The column number is equal to the index of the column on the right of the mirror edge.
     */
    fun findVerticalReflections(): Sequence<Int> = x.asSequence().filter { x ->
        val size = minOf(x - this.x.first, this.x.last - x + 1)

        if (size > 0) {
            (0..<size).all { offset -> getColumn(x + offset) == getColumn(x - offset - 1) }
        } else {
            false
        }
    }

    /**
     * Finds the horizontal reflections and yields the row numbers where the reflections occur.
     *
     * Each row number is equal to the index of the column below the mirror edge.
     */
    fun findHorizontalReflections(): Sequence<Int> = y.asSequence().filter { y ->
        val size = minOf(y - this.y.first, this.y.last - y + 1)

        if (size > 0) {
            (0..<size).all { offset -> getRow(y + offset) == getRow(y - offset - 1) }
        } else {
            false
        }
    }

    /**
     * Finds alternative vertical reflections by flipping a single value and returns the column numbers where the new
     * reflections occur.
     *
     * Each column number is equal to the index of the column on the right of the mirror edge.
     */
    fun findAlternativeVerticalReflections(): Sequence<Int> = findVerticalSmudges()
        .map { MirroredArea(data.copy().apply { set(it.x, it.y, (!get(it)).ordinal) }) }
        .flatMap { it.findVerticalReflections() }
        .onEach { println("Vertical reflection: $it") }
        .run {
            val reflection = findVerticalReflections().firstOrNull()
            println("Initial vertical reflection: $reflection")
            filter { it != reflection }
        }
        .distinct()

    /**
     * Finds alternative horizontal reflections by flipping a single value and returns the row numbers where the new
     * reflections occur.
     *
     * Each row number is equal to the index of the column below the mirror edge.
     */
    fun findAlternativeHorizontalReflections(): Sequence<Int> = findHorizontalSmudges()
        .map { MirroredArea(data.copy().apply { set(it.x, it.y, (!get(it)).ordinal) }) }
        .flatMap { it.findHorizontalReflections() }
        .onEach { println("Horizontal reflection: $it") }
        .run {
            val reflection = findHorizontalReflections().firstOrNull()
            println("Initial horizontal reflection: $reflection")
            filter { it != reflection }
        }
        .distinct()

    private fun findVerticalSmudges(): Sequence<Coordinate2D> = x.asSequence()
        .flatMap { first -> x.asSequence().filter { it > first }.map { first to it } }
        .mapNotNull { (first, second) ->
            differences(getColumn(first), getColumn(second))
                .singleOrNull()
                ?.let { y ->
                    coordinateOf(first, y)
                }
        }
        .onEach { println("Vertical smudge: $it") }

    private fun findHorizontalSmudges(): Sequence<Coordinate2D> = y.asSequence()
        .flatMap { first -> y.asSequence().filter { it > first }.map { first to it } }
        .mapNotNull { (first, second) ->
            differences(getRow(first), getRow(second))
                .singleOrNull()
                ?.let { x ->
                    coordinateOf(x, first)
                }
        }
        .onEach { println("Horizontal smudge: $it") }

    private fun differences(first: List<Content>, second: List<Content>): Sequence<Int> {
        require(first.size == second.size) { "Cannot compute the difference between lists of unequal size" }
        return first.asSequence().zip(second.asSequence())
            .withIndex()
            .filter { it.value.first != it.value.second }
            .map { it.index }
    }

    companion object {

        /**
         * Parses the [input] string to a [MirroredArea] and returns it.
         *
         * @param input The input string to parse.
         * @return The [MirroredArea] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string is not a valid [MirroredArea].
         */
        fun parse(input: String): MirroredArea {
            val contents = input.lineSequence()
                .map { it.map(Content::parse) }
                .toList()

            val width = contents.maxOf { it.size }
            val height = contents.size

            require(contents.all { it.size == width }) { "All input lines must have the same length" }

            return MirroredArea(
                Multik.d2arrayIndices(width, height) { x, y -> contents[y][x].ordinal }
            )
        }
    }

    enum class Content {
        Ash,
        Rocks;

        /**
         * Returns the opposite content to this content.
         */
        operator fun not(): Content = when (this) {
            Ash -> Rocks
            Rocks -> Ash
        }

        companion object {

            /**
             * Parses the [input] character to a [Content] and returns it.
             *
             * @param input The input character to parse.
             * @return The [Content] represented by the [input] character.
             * @throws IllegalArgumentException The [input] character is not a valid [Content].
             */
            fun parse(input: Char): Content = when (input) {
                '.' -> Ash
                '#' -> Rocks
                else -> throw IllegalArgumentException("Input is not a valid content")
            }
        }
    }
}
