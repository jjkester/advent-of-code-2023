package nl.jjkester.adventofcode23.day05.model

import nl.jjkester.adventofcode23.predef.ranges.coerceIn
import nl.jjkester.adventofcode23.predef.ranges.mergeLongRanges
import nl.jjkester.adventofcode23.predef.ranges.size

/**
 * A read-only map-like structure to find a corresponding value for a certain key based on a list of matching ranges.
 *
 * This data structure has a value for every key, if the key is not explicitly mapped than the value is the same as the
 * key but represented as a different data type.
 *
 * @param K Type of the keys.
 * @param V Type of the values.
 * @property extractKey Function to extract a [Long] value from a key.
 * @property transformValue Function to create a value object from a [Long].
 * @param ranges Ranges of keys and values where the mapping is not 1 to 1. In each pair both ranges must have the same
 * size.
 */
class AlmanacStep<K : Comparable<K>, V : Comparable<V>>(
    private val extractKey: (K) -> Long,
    private val transformValue: (Long) -> V,
    ranges: List<Pair<LongRange, LongRange>>
) {
    val ranges = ranges.sortedBy { it.first.first }

    init {
        require(ranges.all { it.first.size == it.second.size })
    }

    /**
     * Returns the corresponding value for the [key].
     *
     * @param key The key to find the corresponding value for.
     * @return The corresponding value for the [key].
     */
    operator fun get(key: K): V = get(extractKey(key))

    /**
     * Returns the corresponding values for the [keys]. Because the range of [keys] may not be mapped to a single range
     * of values, the function returns a list of ranges of values.
     *
     * @param keys The keys to find the corresponding values for.
     * @return The corresponding values for the [keys].
     */
    fun map(keys: ClosedRange<K>): List<ClosedRange<V>> {
        val keyRange = extractKeys(keys)

        val mapped = ranges.mapNotNull { range ->
            range.first.coerceIn(keyRange)
                .takeIf { !it.isEmpty() }
                ?.let { coercedKeys ->
                    val firstValue = range.second.first + (coercedKeys.first - range.first.first)
                    val lastValue = range.second.last - (range.first.last - coercedKeys.last)
                    coercedKeys to firstValue..lastValue
                }
        }

        val unmapped = sequence {
            var i = keyRange.first

            mapped.forEach { range ->
                if (i < range.first.first) {
                    yield(i..<range.first.first)
                }
                i = range.first.last + 1
            }

            if (i <= keyRange.last) {
                yield(i..keyRange.last)
            }
        }

        return (mapped.asSequence().map { it.second } + unmapped)
            .mergeLongRanges()
            .map(::transformValues)
            .toList()
    }

    /**
     * Returns the corresponding values for the [keys]. Because a range of [keys] may not be mapped to a single range of
     * values, the number of items in the returned list may be more than the number of items in the list of [keys].
     *
     * @param keys The keys to find the corresponding values for.
     * @return The corresponding values for the [keys].
     */
    fun mapAll(keys: Iterable<ClosedRange<K>>): Set<ClosedRange<V>> = keys.flatMap(::map).toSet()

    private fun get(keyValue: Long) = ranges.firstOrNull { keyValue in it.first }
        ?.let { transformValue((keyValue - it.first.first) + it.second.first) }
        ?: transformValue(keyValue)

    private fun extractKeys(keys: ClosedRange<K>) = extractKey(keys.start)..extractKey(keys.endInclusive)

    private fun transformValues(range: LongRange) = transformValue(range.first)..transformValue(range.last)
}
