package nl.jjkester.adventofcode23.day10.model

import nl.jjkester.adventofcode23.predef.space.*
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

/**
 * An area of pipes and empty spaces.
 */
class PipeGrid private constructor(data: D2Array<Byte>) : MultikAreaMap<Pipe?, Byte>(data) {

    /**
     * The coordinate of the single start pipe.
     */
    val start: Coordinate2D = requireNotNull(singleOrNull { it.second == Pipe.Start }?.first) {
        "Grid must contain a single start pipe"
    }

    override fun serialize(element: Pipe?): Byte = element?.ordinal?.toByte() ?: -1

    override fun deserialize(value: Byte): Pipe? = value.takeIf { it >= 0 }?.let { Pipe.entries[it.toInt()] }

    /**
     * Finds and returns loops as steps.
     *
     * Each loop only occurs once in the result, in the first start direction that was found.
     */
    fun findLoops(): Sequence<List<Step>> = Direction.entries.asSequence()
        .map { walk(it).toList() }
        .filter { it.size > 1 }
        .distinctBy { it.mapTo(mutableSetOf()) { it.coordinate } }
        .filter { it.first().coordinate == start && it.last().coordinate == start }

    /**
     * Finds and returns the areas inside loops.
     */
    fun findContainedAreas(): Sequence<Set<Coordinate2D>> = findLoops()
        .map { loop ->
            val coordinates = loop.map { it.coordinate }.toSet()
            val startPipe = Pipe.entries.asSequence()
                .filter { it != Pipe.Start }
                .single { it.continuesTo(loop.last().direction) == loop.first().direction }

            sequence {
                y.forEach { y ->
                    var inLoop = false

                    x.forEach { x ->
                        val coordinate = coordinateOf(x, y)
                        val pipe = get(coordinate).takeIf { it != Pipe.Start } ?: startPipe
                        val onLoop = coordinate in coordinates

                        if (coordinate in coordinates) {
                            if (pipe in northPipes) {
                                inLoop = !inLoop
                            }
                        }

                        if (inLoop && !onLoop) {
                            yield(coordinate)
                        }
                    }
                }
            }.toSet()
        }

    /**
     * Walks from the [start] coordinate in the [startDirection] until the pipe ends and returns a sequence with the
     * steps that have been taken.
     *
     * When a pipe has a loop (i.e. ends up at the start coordinate again), the start coordinate is emitted at the start
     * and end of the sequence.
     *
     * @param startDirection Direction from the [start] coordinate.
     */
    fun walk(startDirection: Direction): Sequence<Step> = sequence {
        var position: Coordinate2D = start
        var direction: Direction? = startDirection
        var pipe: Pipe? = get(start)
        var startPipes = 0

        while (pipe != null && direction != null && startPipes < 2) {
            yield(Step(position, pipe, direction))

            if (pipe == Pipe.Start) startPipes++

            position = position.stepTo(direction)
            pipe = getOrNull(position)
            direction = pipe?.continuesTo(direction)
        }
    }

    override fun toString(): String = StringBuilder()
        .apply {
            y.forEach { y ->
                x.forEach { x ->
                    append(get(x, y)?.toChar() ?: ' ')
                }
                append(System.lineSeparator())
            }
        }
        .toString()

    private fun Coordinate2D.stepTo(direction: Direction) = when (direction) {
        Direction.North -> copy(y = y - 1)
        Direction.East -> copy(x = x + 1)
        Direction.South -> copy(y = y + 1)
        Direction.West -> copy(x = x - 1)
    }

    companion object {
        private val northPipes = setOf(Pipe.NorthSouth, Pipe.NorthEast, Pipe.NorthWest)

        fun parse(input: String): PipeGrid {
            val startIndexes = mutableListOf<Coordinate2D>()
            val pipes = input.lineSequence().withIndex()
                .map { (y, line) ->
                    line.asSequence()
                        .withIndex()
                        .map { (x, char) ->
                            char.takeUnless { it == '.' }
                                ?.let { pipeChar ->
                                    Pipe.parse(pipeChar)
                                        .also { if (it == Pipe.Start) startIndexes += coordinateOf(x, y) }
                                        .ordinal.toByte()
                                }
                                ?: -1
                        }
                        .toList()
                }
                .toList()

            val width = pipes.maxOf { it.size }
            val height = pipes.size

            return PipeGrid(Multik.d2arrayIndices(width, height) { x, y -> pipes[y][x] })
        }
    }

    /**
     * A step through a pipe.
     *
     * @property coordinate The coordinate to step from.
     * @property pipe The pipe at the [coordinate].
     * @property direction The direction to step in.
     */
    data class Step(val coordinate: Coordinate2D, val pipe: Pipe, val direction: Direction)
}
