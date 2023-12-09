package nl.jjkester.adventofcode23.predef.space

import nl.jjkester.adventofcode23.predef.ranges.size

/**
 * Abstract implementation of [Area].
 *
 * Implements [size], [isEmpty] and [contains] based on the values for [x] and [y].
 */
abstract class AbstractArea : Area {
    final override val size: Int
        get() = x.size * y.size

    final override fun isEmpty(): Boolean = x.isEmpty() || y.isEmpty()

    final override fun contains(x: Int, y: Int): Boolean = x in this.x && y in this.y
}
