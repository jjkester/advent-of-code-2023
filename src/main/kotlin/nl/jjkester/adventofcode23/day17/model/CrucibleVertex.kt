package nl.jjkester.adventofcode23.day17.model

/**
 * A wrapper for the shortest path algorithm to enable a single vertex to represent multiple nodes, depending on the
 * [direction] and repetition thereof ([line]).
 *
 * @property vertex Known node for path finding purposes.
 * @property direction Direction of the current line.
 * @property line Length of the current line.
 */
data class CheckedVertex<T>(val vertex: T, val direction: Direction?, val line: Int)
