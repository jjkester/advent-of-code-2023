package nl.jjkester.adventofcode23.day21.model

import nl.jjkester.adventofcode23.predef.ranges.size
import nl.jjkester.adventofcode23.predef.space.*
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

/**
 * A garden with plots and rocks.
 */
class Garden private constructor(data: D2Array<Int>) : EnumMultikAreaMap<Garden.Type>(Type.entries, data) {

    private val start: Coordinate2D = requireNotNull(
        singleOrNull { it.second == Type.Start }?.first
    ) { "Garden does not have a start coordinate" }

    /**
     * Returns the number of coordinates that are reachable in exactly [steps].
     */
    fun reachableInPrecise(steps: Int): Int = reachableAfterSteps()
        .drop(steps)
        .first()
        .size

    /**
     * Returns the estimated number of coordinates that are reachable in exactly [steps] based on a few assumptions:
     *
     * 1. The start point is in the middle of the garden.
     * 2. All edges around the garden are free of rocks.
     * 3. There are straight paths without rocks from the start point to the edges of the garden.
     *
     * These assumptions lead to a repetition pattern on which an estimate can be based.
     */
    fun reachableInEstimated(steps: Int): Long {
        require(start.x == x.size / 2 && start.y == y.size / 2) { "Start is not in the center of the garden" }
        require(
            getRow(y.first).all { it.canWalk } &&
                    getRow(y.last).all { it.canWalk } &&
                    getColumn(x.first).all { it.canWalk } &&
                    getColumn(x.last).all { it.canWalk }
        ) { "Garden edges are not all walkable" }
        require(getRow(start.y).all { it.canWalk }) { "Start row is not all walkable" }
        require(getColumn(start.x).all { it.canWalk }) { "Start column is not all walkable" }

        val size = maxOf(x.size, y.size)
        val repetitions = mutableListOf<Int>()

        val (computedSteps, computedVisited) = reachableAfterSteps()
            .take(steps + 1)
            .withIndex()
            .onEach { if (it.index % size == size / 2) repetitions.add(it.value.size) }
            .takeWhile { repetitions.size < 3 }
            .last()

        return if (computedSteps < steps) {
            val polynomial = Polynomial.fit(repetitions.mapIndexed { index, value -> index by value })
            polynomial(steps / size)
        } else {
            computedVisited.size.toLong()
        }
    }

    /**
     * Returns a sequence yielding the set of reachable coordinates in each step, starting from the set containing just
     * the start coordinate.
     */
    fun reachableAfterSteps(): Sequence<Set<Coordinate2D>> = sequence {
        var coordinates = setOf(start)

        do {
            yield(coordinates)
            coordinates = coordinates.asSequence()
                .flatMap { it.neighbours() }
                .distinct()
                .filter { getOrNull(wrap(it))?.canWalk ?: false }
                .toSet()
        } while (true)
    }

    private fun Coordinate2D.neighbours() = sequence {
        yield(copy(x = x - 1))
        yield(copy(x = x + 1))
        yield(copy(y = y - 1))
        yield(copy(y = y + 1))
    }

    companion object {

        /**
         * Parses the [input] string to a [Garden] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Garden] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [Garden].
         */
        fun parse(input: String): Garden {
            val contents = input.lineSequence()
                .map { it.map(Type.Companion::parse) }
                .toList()

            val width = contents.maxOf { it.size }
            val height = contents.size

            require(contents.all { it.size == width }) { "All input lines must have the same length" }

            return Garden(
                Multik.d2arrayIndices(width, height) { x, y -> contents[y][x].ordinal }
            )
        }
    }

    /**
     * Type of squares in the [Garden].
     *
     * @property canWalk Whether the square is accessible.
     */
    enum class Type(val canWalk: Boolean) {
        /** The start square. This is also a plot. */
        Start(true),

        /** A garden plot. */
        Plot(true),

        /** A rock. */
        Rock(false);

        companion object {

            /**
             * Parses the [input] character to a [Type] and returns it.
             *
             * @param input The input character to parse.
             * @return The [Type] represented by the [input] character.
             * @throws IllegalArgumentException The [input] character does not represent a valid [Type].
             */
            fun parse(input: Char): Type = when (input) {
                'S' -> Start
                '.' -> Plot
                '#' -> Rock
                else -> throw IllegalArgumentException("Input is not a valid garden type")
            }
        }
    }
}
