package nl.jjkester.adventofcode23.predef.space

import nl.jjkester.adventofcode23.predef.size

/**
 * Abstract implementation of [D2Area].
 *
 * Implements [size], [isEmpty] and [contains] based on the values for [x] and [y].
 */
abstract class AbstractD2Area : D2Area {
    final override val size: Int
        get() = x.size * y.size

    final override fun isEmpty(): Boolean = x.isEmpty() || y.isEmpty()

    final override fun contains(x: Int, y: Int): Boolean = x in this.x && y in this.y
}
