package nl.jjkester.adventofcode23.day14.model

import nl.jjkester.adventofcode23.predef.space.EnumMultikAreaMap
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

/**
 * A pivoting platform containing round and square rocks.
 */
class Platform private constructor(
    data: D2Array<Int>
) : EnumMultikAreaMap<Platform.Deformation>(Deformation.entries, data) {

    /**
     * The load of round rocks on the north beams of the platform.
     */
    val northBeamLoad: Int by lazy {
        y.sumOf { y -> x.count { x -> this[x, y] == Deformation.Round } * (this.y.last - y + 1) }
    }

    /**
     * Returns a copy of this platform containing the result after a [number] of spin cycles.
     *
     * @param number The number of cycles to run.
     * @see spinCycle A single spin cycle.
     */
    fun spinCycles(number: Long): Platform = mutableListOf<Platform>()
        .also { require(number >= 0) { "Number of spin cycles must be positive" } }
        .let { history ->
            generateSequence(this) { it.spinCycle() }
                .takeWhile { history.distinct().size == history.size }
                .onEach { history.add(it) }
                .last()
                .let { history.indexOf(it) }
                .let { history.subList(0, it) to history.subList(it, history.lastIndex) }
        }
        .let { (preCycle, cycle) ->
            if (number < preCycle.size) {
                preCycle[number.toInt()]
            } else {
                cycle[((number - preCycle.size) % cycle.size).toInt()]
            }
        }

    /**
     * Returns a copy of this platform containing the result after a spin cycle. A spin cycle tilts the platform to the
     * north, west, south and east.
     */
    fun spinCycle() = tiltNorth().tiltWest().tiltSouth().tiltEast()

    /**
     * Returns a copy of this platform containing the result of tilting this platform to the north.
     */
    fun tiltNorth(): Platform = Platform(
        data.copy().also { data ->
            y.forEach { y ->
                x.forEach { x ->
                    if (data[x, y] == Deformation.Round.ordinal) {
                        (y - 1 downTo 0)
                            .takeWhile { data[x, it] == Deformation.None.ordinal }
                            .lastOrNull()
                            ?.let { target ->
                                data[x, target] = Deformation.Round.ordinal
                                data[x, y] = Deformation.None.ordinal
                            }
                    }
                }
            }
        }
    )

    /**
     * Returns a copy of this platform containing the result of tilting this platform to the west.
     */
    fun tiltWest(): Platform = Platform(
        data.copy().also { data ->
            x.forEach { x ->
                y.forEach { y ->
                    if (data[x, y] == Deformation.Round.ordinal) {
                        (x - 1 downTo 0)
                            .takeWhile { data[it, y] == Deformation.None.ordinal }
                            .lastOrNull()
                            ?.let { target ->
                                data[target, y] = Deformation.Round.ordinal
                                data[x, y] = Deformation.None.ordinal
                            }
                    }
                }
            }
        }
    )

    /**
     * Returns a copy of this platform containing the result of tilting this platform to the south.
     */
    fun tiltSouth(): Platform = Platform(
        data.copy().also { data ->
            y.reversed().forEach { y ->
                x.forEach { x ->
                    if (data[x, y] == Deformation.Round.ordinal) {
                        (y + 1..this.y.last)
                            .takeWhile { data[x, it] == Deformation.None.ordinal }
                            .lastOrNull()
                            ?.let { target ->
                                data[x, target] = Deformation.Round.ordinal
                                data[x, y] = Deformation.None.ordinal
                            }
                    }
                }
            }
        }
    )

    /**
     * Returns a copy of this platform containing the result of tilting this platform to the east.
     */
    fun tiltEast(): Platform = Platform(
        data.copy().also { data ->
            x.reversed().forEach { x ->
                y.forEach { y ->
                    if (data[x, y] == Deformation.Round.ordinal) {
                        (x + 1..this.x.last)
                            .takeWhile { data[it, y] == Deformation.None.ordinal }
                            .lastOrNull()
                            ?.let { target ->
                                data[target, y] = Deformation.Round.ordinal
                                data[x, y] = Deformation.None.ordinal
                            }
                    }
                }
            }
        }
    )

    override fun toString(): String = y.joinToString(System.lineSeparator()) { y ->
        x.joinToString("") { x ->
            when (this[x, y]) {
                Deformation.Round -> "O"
                Deformation.Square -> "#"
                Deformation.None -> "."
            }
        }
    }

    companion object {

        /**
         * Parses the [input] string to a [Platform] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Platform] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [Platform].
         */
        fun parse(input: String): Platform {
            val contents = input.lineSequence()
                .map { it.map(Deformation::parse) }
                .toList()

            val width = contents.maxOf { it.size }
            val height = contents.size

            require(contents.all { it.size == width }) { "All input lines must have the same length" }

            return Platform(
                Multik.d2arrayIndices(width, height) { x, y -> contents[y][x].ordinal }
            )
        }
    }

    /**
     * Deformation of a section of the platform.
     */
    enum class Deformation {

        /** A round rock that rolls. */
        Round,

        /** A stationary square rock. */
        Square,

        /** An empty space. */
        None;

        companion object {

            /**
             * Parses the [input] character to a [Deformation] and returns it.
             *
             * @param input The input character to parse.
             * @return The [Deformation] represented by the [input] character.
             * @throws IllegalArgumentException The [input] character does not represent a valid [Deformation].
             */
            fun parse(input: Char): Deformation = when (input) {
                'O' -> Round
                '#' -> Square
                '.' -> None
                else -> throw IllegalArgumentException("Input is not a valid deformation")
            }
        }
    }
}
