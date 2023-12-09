package nl.jjkester.adventofcode23.predef.space

import nl.jjkester.adventofcode23.predef.ranges.size

/**
 * Abstract implementation of [Volume].
 *
 * Implements [size], [isEmpty] and [contains] based on the values for [x], [y] and [z].
 */
abstract class AbstractVolume : Volume {
    final override val size: Int
        get() = x.size * y.size * z.size

    final override fun isEmpty(): Boolean = x.isEmpty() || y.isEmpty() || z.isEmpty()

    final override fun contains(x: Int, y: Int, z: Int): Boolean = x in this.x && y in this.y && z in this.z
}
