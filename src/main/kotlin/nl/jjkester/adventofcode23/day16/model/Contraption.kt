package nl.jjkester.adventofcode23.day16.model

import nl.jjkester.adventofcode23.predef.space.*
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

/**
 * A contraption with light devices forming beams.
 */
class Contraption private constructor(data: D2Array<Int>) : EnumMultikAreaMap<LightDevice>(LightDevice.entries, data) {

    /**
     * The edge coordinates and their entry directions of the contraption.
     */
    val edges: List<Pair<Coordinate2D, Direction>> by lazy {
        (y.asSequence().map { 0 by it to Direction.East } +
                x.asSequence().map { it by y.last to Direction.North } +
                y.asSequence().map { x.last by it to Direction.West } +
                x.asSequence().map { it by 0 to Direction.South })
            .toList()
    }

    /**
     * The number of tiles in this contraption that are energized when the beam enters the contraption at the
     * [coordinate] in the [direction].
     *
     * @param coordinate The edge coordinate to enter at.
     * @param direction The direction to enter in.
     * @return The number of energized tiles for the given entry point.
     */
    fun energizedFrom(coordinate: Coordinate2D, direction: Direction): Int {
        require(
            coordinate.x == x.first || coordinate.x == x.last || coordinate.y == y.first || coordinate.y == y.last
        ) { "Coordinate must be on the edge of the area" }

        return beamsFrom(coordinate, direction).distinct().count()
    }

    override fun toString(): String = toString(emptySet())

    private fun toString(energized: Iterable<Coordinate2D>) = y.joinToString(System.lineSeparator()) { y ->
        x.joinToString("") { x ->
            this[x, y].let { if (it == LightDevice.None && x by y in energized) "#" else it.toString() }
        }
    }

    private fun beamsFrom(coordinate: Coordinate2D, direction: Direction): Sequence<Coordinate2D> = sequence {
        val queue = mutableListOf(Beam(coordinate, direction))
        val endConditions = mutableSetOf<Pair<Coordinate2D, Direction>>()

        while (queue.isNotEmpty()) {
            val beam = queue.removeFirst()

            while (!beam.isFinished) {
                queue.addAll(step(beam, endConditions))
                endConditions.add(beam.last())
            }

            yieldAll(beam.path)
        }
    }

    private fun step(beam: Beam, endConditions: Set<Pair<Coordinate2D, Direction>>): List<Beam> {
        val directions = this[beam.coordinate].apply(beam.direction).toList()

        // Create copies for subsequent directions
        val copies = directions.dropLast(1).map { beam.copy().apply { step(it, endConditions) } }

        // Mutate beam for last direction
        beam.step(directions.last(), endConditions)

        return copies
    }

    companion object {

        /**
         * Parses the [input] string to a [Contraption] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Contraption] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [Contraption].
         */
        fun parse(input: String): Contraption {
            val contents = input.lineSequence()
                .map { it.map(LightDevice.Companion::parse) }
                .toList()

            val width = contents.maxOf { it.size }
            val height = contents.size

            require(contents.all { it.size == width }) { "All input lines must have the same length" }

            return Contraption(
                Multik.d2arrayIndices(width, height) { x, y -> contents[y][x].ordinal }
            )
        }
    }

    private inner class Beam private constructor(
        private val progress: MutableList<Pair<Coordinate2D, Direction>>,
        isFinished: Boolean
    ) : List<Pair<Coordinate2D, Direction>> by progress {

        val path: List<Coordinate2D>
            get() = progress.map { it.first }

        val direction: Direction
            get() = progress.last().second

        val coordinate: Coordinate2D
            get() = progress.last().first

        var isFinished: Boolean = isFinished
            private set

        constructor(position: Coordinate2D, direction: Direction) : this(mutableListOf(position to direction), false)

        fun step(direction: Direction, endConditions: Set<Pair<Coordinate2D, Direction>>) {
            // Only step when it makes sense to do so
            if (!isFinished) {
                // Calculate next coordinate in the direction
                val next = coordinate.stepTo(direction) to direction

                if (next.first !in this@Contraption) {
                    // If the next coordinate is out of bounds, we're done
                    isFinished = true
                } else {
                    if (next in progress || next in endConditions) {
                        // If the next coordinate loops onto itself or an end condition, we're done
                        isFinished = true
                    }
                    progress += next
                }
            }
        }

        fun copy(): Beam = Beam(progress.toMutableList(), isFinished)

        override fun toString(): String = "Beam($progress)"

        private fun Coordinate2D.stepTo(direction: Direction) = when (direction) {
            Direction.North -> copy(y = y - 1)
            Direction.East -> copy(x = x + 1)
            Direction.South -> copy(y = y + 1)
            Direction.West -> copy(x = x - 1)
        }
    }
}
