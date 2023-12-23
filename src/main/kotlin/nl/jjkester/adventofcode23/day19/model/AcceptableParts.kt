package nl.jjkester.adventofcode23.day19.model

import nl.jjkester.adventofcode23.predef.ranges.coerceIn
import nl.jjkester.adventofcode23.predef.ranges.size

/**
 * A four-dimensional range of acceptable parts.
 *
 * @property x The acceptable x ratings.
 * @property m The acceptable m ratings.
 * @property a The acceptable a ratings.
 * @property s The acceptable s ratings.
 */
data class AcceptableParts(val x: IntRange, val m: IntRange, val a: IntRange, val s: IntRange) {

    /**
     * Returns the number of distinct acceptable parts.
     */
    fun count(): Long = x.size.toLong() * m.size.toLong() * a.size.toLong() * s.size.toLong()

    /**
     * Limits the acceptable parts by the [ratings] in the [category].
     *
     * @param category The category of [ratings] to limit the group on.
     * @param ratings The ratings in the [category] to limit the group to.
     * @return The resulting group of limiting the existing group.
     */
    fun coerceIn(category: Category, ratings: IntRange): AcceptableParts = when (category) {
        Category.X -> copy(x = x.coerceIn(ratings))
        Category.M -> copy(m = m.coerceIn(ratings))
        Category.A -> copy(a = a.coerceIn(ratings))
        Category.S -> copy(s = s.coerceIn(ratings))
    }
}
