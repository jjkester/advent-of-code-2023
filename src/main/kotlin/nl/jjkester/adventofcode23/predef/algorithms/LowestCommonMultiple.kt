package nl.jjkester.adventofcode23.predef.algorithms

/**
 * Lowest common multiple algorithm to compute the lowest number divisible by both [a] and [b].
 *
 * @param a First number.
 * @param b Second number.
 * @return The lowest common multiple of [a] and [b].
 */
fun lcm(a: Int, b: Int): Int = lcm(a.toLong(), b.toLong()).toInt()

/**
 * Lowest common multiple algorithm to compute the lowest number divisible by both [a] and [b].
 *
 * @param a First number.
 * @param b Second number.
 * @return The lowest common multiple of [a] and [b].
 */
fun lcm(a: Long, b: Long): Long {
    val multiple = a * b

    require(a > 0 && b > 0) { "Both numbers must be greater than zero" }
    require(multiple >= a && multiple >= b) {
        "The multiple of both numbers is too large, overflow occurred"
    }

    return maxOf(a, b)
        .let { it..multiple step it }
        .firstOrNull { it % a == 0L && it % b == 0L }
        ?: multiple
}
