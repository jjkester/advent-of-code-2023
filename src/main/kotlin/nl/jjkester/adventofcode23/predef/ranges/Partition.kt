package nl.jjkester.adventofcode23.predef.ranges

/**
 * Returns a pair of the parts of this range that overlap with any of the other ranges and the parts of this range that
 * do not overlap with any of the other ranges.
 *
 * @param overlapsWith Ranges to check overlap with for the partition.
 * @return Pair of lists of ranges that overlap and do not overlap, covering precisely this range.
 */
fun IntRange.partition(overlapsWith: Iterable<IntRange>): Pair<List<IntRange>, List<IntRange>> =
    partition(overlapsWith, Int::rangeTo, Int::dec, Int::inc)

/**
 * Returns a pair of the parts of this range that overlap with any of the other ranges and the parts of this range that
 * do not overlap with any of the other ranges.
 *
 * @param overlapsWith Ranges to check overlap with for the partition.
 * @return Pair of lists of ranges that overlap and do not overlap, covering precisely this range.
 */
fun LongRange.partition(overlapsWith: Iterable<LongRange>): Pair<List<LongRange>, List<LongRange>> =
    partition(overlapsWith, Long::rangeTo, Long::dec, Long::inc)

private inline fun <N, T : ClosedRange<N>> T.partition(
    overlapsWith: Iterable<T>,
    factory: (first: N, last: N) -> T,
    dec: (N) -> N,
    inc: (N) -> N
): Pair<List<T>, List<T>> where N : Number, N : Comparable<N> {
    val overlapping = mutableListOf<T>()
    val notOverlapping = mutableListOf<T>()

    if (!isEmpty()) {
        var i = start

        overlapsWith.sortedBy { it.start }.forEach { range ->
            if (!range.isEmpty()) {
                if (i !in range && i < range.start) {
                    notOverlapping += factory(i, dec(range.start))
                }
                if (i <= range.endInclusive && endInclusive >= range.start) {
                    overlapping += factory(maxOf(i, range.start), minOf(endInclusive, range.endInclusive))
                }
                i = maxOf(i, inc(range.endInclusive))
            }
        }

        if (i <= endInclusive) {
            notOverlapping += factory(i, endInclusive)
        }
    }

    return overlapping to notOverlapping
}
