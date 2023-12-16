package nl.jjkester.adventofcode23.predef.space

/**
 * Abstract implementation of [AreaMap].
 *
 * Implements [size], [isEmpty] and [contains] based on the values for [x] and [y], and implements [columns] and [rows]
 * using [get].
 */
abstract class AbstractAreaMap<T> : AbstractArea(), AreaMap<T> {

    final override val columns: List<List<T>> by lazy { x.map { column -> y.map { row -> this[column, row] } } }

    final override val rows: List<List<T>> by lazy { y.map { row -> x.map { column -> this[column, row] } } }
}
