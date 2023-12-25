package nl.jjkester.adventofcode23.day22.model

import nl.jjkester.adventofcode23.predef.ranges.shift
import nl.jjkester.adventofcode23.predef.space.*

/**
 * A snapshot of falling [bricks]. A snapshot is immutable, and a copy will be made when bricks have fallen down more.
 *
 * @property bricks The bricks in the snapshot.
 */
class Snapshot(val bricks: List<Brick>) {

    private val stacked: List<Pair<Brick, Brick>> = bricks.flatMap { brick ->
        bricks
            .filter { brick[topDownFacing] overlaps it[topDownFacing] && brick.z.last + 1 == it.z.first }
            .map { brick to it }
    }

    private val supporting = stacked.groupBy { it.first }.mapValues { (_, value) -> value.map { it.second } }

    private val supportedBy = stacked.groupBy { it.second }.mapValues { (_, value) -> value.map { it.first } }

    private val disintegrationCandidates by lazy {
        bricks.mapTo(mutableSetOf()) { brick -> brick to wouldFall(brick).count() - 1 }
    }

    init {
        require(bricks.flatMap { it.volume.coordinates() }.let { it.size == it.distinct().size }) {
            "Bricks cannot overlap"
        }
    }

    /**
     * Computes and returns a snapshot of when all bricks have fallen to the ground and cannot fall any further. Not all
     * bricks may be on the ground, bricks may also be supported by other bricks.
     */
    fun toGround(): Snapshot {
        val movedBricks = mutableListOf<Brick>()

        bricks.sortedWith(compareBy({ it.z.first }, { it.z.last })).forEach { brick ->
            val heightAtSurface = movedBricks
                .filter { it[topDownFacing] overlaps brick[topDownFacing] }
                .maxOfOrNull { it.z.last }
                ?: 0
            val difference = brick.z.first - (heightAtSurface + 1)

            movedBricks += brick.run {
                copy(volume = volume.copy(z = brick.z shift -difference))
            }
        }

        return Snapshot(movedBricks)
    }

    /**
     * Returns a set of the candidates for disintegration. The result contains the number of bricks that will fall
     * for each brick in the snapshot.
     */
    fun disintegrationCandidates(): Set<Pair<Brick, Int>> = disintegrationCandidates

    private fun wouldFall(brick: Brick, fallen: Set<Brick> = setOf()): Set<Brick> {
        return supporting[brick]
            ?.asSequence()
            ?.filter { supportedBy[it]?.minus(fallen)?.minus(brick).isNullOrEmpty() }
            ?.fold(fallen + brick) { acc, cascaded -> wouldFall(cascaded, acc) }
            ?: (fallen + brick)
    }

    companion object {
        private val topDownFacing = Volume.Facing.Back

        private val idSequence = generateSequence("A") {
            if (it.last() < 'Z') {
                "${it.dropLast(1)}${it.last().inc()}"
            } else {
                "A${it.dropLast(1)}A"
            }
        }

        /**
         * Parses the [input] string to a [Snapshot] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Snapshot] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid snapshot.
         */
        fun parse(input: String): Snapshot {
            val idGenerator = idSequence.iterator()

            val bricks = input.lines()
                .map { line ->
                    line.split('~')
                        .also { require(it.size == 2) { "A line must be precisely two coordinates" } }
                        .map(::parseCoordinate3D)
                        .let { (first, second) -> Brick(idGenerator.next(), volumeOf(first, second)) }
                }

            return Snapshot(bricks)
        }

        private fun parseCoordinate3D(input: String): Coordinate3D = input
            .split(',')
            .also { require(it.size == 3) { "A coordinate must have three dimensions" } }
            .let { (x, y, z) -> coordinateOf(x.toInt(), y.toInt(), z.toInt()) }
    }
}
