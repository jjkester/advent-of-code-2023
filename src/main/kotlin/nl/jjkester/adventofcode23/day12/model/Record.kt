package nl.jjkester.adventofcode23.day12.model

import nl.jjkester.adventofcode23.predef.ranges.size

/**
 * A record containing a list of [values] and a list of [groups].
 *
 * @property values List of good, bad and unknown springs.
 * @property groups Sizes of known groups of springs, in the right order for the [values].
 */
data class Record(val values: List<Value?>, val groups: List<Int>) {

    init {
        require(values.isNotEmpty()) { "Values must not be empty" }
        require(groups.all { it > 0 }) { "All groups must be greater than zero" }
    }

    /**
     * Interprets the record as folded and returns a new record repeating the contents of this record a number of
     * [times].
     *
     * Groups are repeated a number of [times], values are repeated the same number of [times] but with an additional
     * unknown value inserted between repetitions.
     *
     * @param times Number of times to repeat the record.
     * @return A new record with the [values] and [groups] repeated.
     */
    fun unfold(times: Int): Record = Record(
        buildList(values.size * times + times - 1) {
            repeat(times) {
                addAll(values)
                if (it < times - 1) add(null)
            }
        },
        buildList(groups.size * times) {
            repeat(times) {
                addAll(groups)
            }
        }
    )

    /**
     * Returns a sequence of all possibilities for filling in the unknown values.
     *
     * This function is not recommended for counting possibilities.
     */
    fun possibilities(): Sequence<List<Value>> = possibilities(values)
        .filter { findGroups(it, Value.Bad).map { it.size }.toList() == groups }

    /**
     * Returns the number of all possibilities for filling in the unknown values.
     *
     * This function can handle a high number of possibilities.
     */
    fun numberOfPossibilities(): Long = numberOfPossibilities(values, groups, mutableMapOf())

    private fun numberOfPossibilities(
        values: List<Value?>,
        groups: List<Int>,
        cache: MutableMap<Pair<List<Value?>, List<Int>>, Long>
    ): Long = cache[values to groups] ?: if (values.isEmpty()) {
        if (groups.isEmpty()) 1L else 0L
    } else if (groups.isEmpty()) {
        if (Value.Bad !in values) 1L else 0L
    } else {
        val remainingValues = values.drop(1)

        when (values.first()) {
            Value.Good -> numberOfPossibilities(remainingValues.dropWhile { it == Value.Good }, groups, cache)
            Value.Bad -> groups.first().let { group ->
                if (values.size >= group
                    && Value.Good !in values.take(group)
                    && values.getOrNull(group) != Value.Bad
                ) {
                    numberOfPossibilities(remainingValues.drop(group), groups.drop(1), cache)
                } else {
                    0L
                }
            }

            null -> numberOfPossibilities(values.toMutableList().apply { set(0, Value.Good) }, groups, cache) +
                    numberOfPossibilities(values.toMutableList().apply { set(0, Value.Bad) }, groups, cache)
        }.also { cache[values to groups] = it }
    }

    private fun possibilities(values: List<Value?>): Sequence<List<Value>> {
        val indexOfNull = values.indexOf(null)
        val progressIndex = indexOfNull.takeIf { it >= 0 } ?: values.size
        val foundGroups = findGroups(values, Value.Bad).filter { it.first < progressIndex }.map { it.size }.toList()
        val foundAllGroups = foundGroups.size == groups.size && foundGroups == groups
        val foundInvalidGroup =
            foundGroups.size > 1 && foundGroups.dropLast(1).withIndex().any { (index, value) -> groups[index] > value }

        return if (foundAllGroups) {
            sequenceOf(values.map { it ?: Value.Good })
        } else if (indexOfNull < 0 || foundInvalidGroup) {
            emptySequence()
        } else {
            possibilities(values.toMutableList().apply { set(indexOfNull, Value.Good) }) +
                    possibilities(values.toMutableList().apply { set(indexOfNull, Value.Bad) })
        }
    }

    private fun findGroups(values: List<Value?>, discriminator: Value?): Sequence<IntRange> = sequence {
        var previousIndex = Int.MIN_VALUE
        var start = 0

        values.withIndex().filter { it.value == discriminator }.forEach { (index, _) ->
            if (index > previousIndex + 1) {
                if (previousIndex >= start) {
                    yield(start..previousIndex)
                }
                start = index
            }
            previousIndex = index
        }

        if (previousIndex >= start) {
            yield(start..previousIndex)
        }
    }

    companion object {
        private val regex = Regex("""(?<values>[.#?]+)\s*(?<numbers>\d+(,\d+)*)""")

        /**
         * Parses the [input] string to a [Record] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Record] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string is not a valid [Record].
         */
        fun parse(input: String): Record {
            val result = requireNotNull(regex.matchEntire(input)) { "The input is not a valid record" }

            return Record(
                result.groups["values"]?.value?.map(Value::parse).orEmpty(),
                result.groups["numbers"]?.value?.split(',').orEmpty().map { it.toInt() }
            )
        }
    }

    /**
     * A value in a record.
     */
    enum class Value {
        /** Incidates a good spring. */
        Good,

        /** Indicates a bad spring. */
        Bad;

        companion object {

            /**
             * Parses the [input] character to a [Value] and returns it.
             *
             * @param input The input character to parse.
             * @return The [Value] represented by the [input] character.
             * @throws IllegalArgumentException The [input] character is not a valid [Value].
             */
            fun parse(input: Char): Value? = when (input) {
                '.' -> Good
                '#' -> Bad
                '?' -> null
                else -> throw IllegalArgumentException("The input is not a valid record value")
            }
        }
    }
}
