package nl.jjkester.adventofcode23.predef.ranges

/**
 * Returns the list of condensed ranges by combining overlapping ranges.
 *
 * @return List of condensed ranges.
 */
fun Iterable<IntRange>.mergeIntRanges(): List<IntRange> = mergeIntRangesTo(mutableListOf())

/**
 * Appends all condensed ranges to [destination] by combining overlapping ranges.
 *
 * @param destination The collection to append the results to.
 * @return The [destination] collection.
 */
fun <C : MutableCollection<IntRange>> Iterable<IntRange>.mergeIntRangesTo(destination: C): C =
    mergeInternal(Int::rangeTo, Int::inc).toCollection(destination)

/**
 * Returns the list of condensed ranges by combining overlapping ranges.
 *
 * @return List of condensed ranges.
 */
fun Iterable<LongRange>.mergeLongRanges(): List<LongRange> = mergeLongRangesTo(mutableListOf())

/**
 * Appends all condensed ranges to [destination] by combining overlapping ranges.
 *
 * @param destination The collection to append the results to.
 * @return The [destination] collection.
 */
fun <C : MutableCollection<LongRange>> Iterable<LongRange>.mergeLongRangesTo(destination: C): C =
    mergeInternal(Long::rangeTo, Long::inc).toCollection(destination)

/**
 * Returns the sequence of condensed ranges by combining overlapping ranges.
 *
 * @return Sequence of condensed ranges.
 */
fun Sequence<IntRange>.mergeIntRanges(): Sequence<IntRange> = asIterable().mergeInternal(Int::rangeTo, Int::inc)

/**
 * Returns the sequence of condensed ranges by combining overlapping ranges.
 *
 * @return Sequence of condensed ranges.
 */
fun Sequence<LongRange>.mergeLongRanges(): Sequence<LongRange> = asIterable().mergeInternal(Long::rangeTo, Long::inc)

private inline fun <N, T : ClosedRange<N>> Iterable<T>.mergeInternal(
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
