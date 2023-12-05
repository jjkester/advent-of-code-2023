package nl.jjkester.adventofcode23.predef.ranges

/**
 * Returns the list of condensed ranges by combining overlapping ranges.
 *
 * @return List of condensed ranges.
 */
fun Iterable<IntRange>.mergedInts(): List<IntRange> = merged(Int::rangeTo, Int::inc).toList()

/**
 * Returns the list of condensed ranges by combining overlapping ranges.
 *
 * @return List of condensed ranges.
 */
fun Iterable<LongRange>.mergedLongs(): List<LongRange> = merged(Long::rangeTo, Long::inc).toList()

/**
 * Returns the sequence of condensed ranges by combining overlapping ranges.
 *
 * @return Sequence of condensed ranges.
 */
fun Sequence<IntRange>.mergedInts(): Sequence<IntRange> = asIterable().merged(Int::rangeTo, Int::inc)

/**
 * Returns the sequence of condensed ranges by combining overlapping ranges.
 *
 * @return Sequence of condensed ranges.
 */
fun Sequence<LongRange>.mergedLongs(): Sequence<LongRange> = asIterable().merged(Long::rangeTo, Long::inc)

private inline fun <N, T : ClosedRange<N>> Iterable<T>.merged(
    crossinline factory: (first: N, last: N) -> T,
    crossinline inc: (N) -> N
): Sequence<T> where N : Number, N : Comparable<N> {
    val sorted = sortedBy { it.start }.filter { !it.isEmpty() }

    return if (sorted.size <= 1) {
        sorted.asSequence()
    } else {
        sequence {
            val iterator = sorted.iterator()
            var range = iterator.next()

            while (iterator.hasNext()) {
                val next = iterator.next()

                range = if (range.overlaps(next) || inc(range.endInclusive) == next.start) {
                    factory(range.start, next.endInclusive)
                } else {
                    yield(range)
                    next
                }
            }

            yield(range)
        }
    }
}
