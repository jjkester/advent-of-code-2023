package nl.jjkester.adventofcode23.day17.model

/**
 * A wrapper for the shortest path algorithm to enable a single vertex to represent multiple nodes, depending on the
 * list of previous [directions].
 *
 * @param vertex Known node for path finding purposes.
 * @param directions Previous directions to allow for multiple, but not all possible, paths to be considered.
 */
data class CheckedVertex<T>(val vertex: T, val directions: List<Direction>) {

    /**
     * Length of the last line.
     */
    val line: Int by lazy(LazyThreadSafetyMode.NONE) {
        directions.asReversed().asSequence().takeWhile { it == directions.last() }.count()
    }

}
