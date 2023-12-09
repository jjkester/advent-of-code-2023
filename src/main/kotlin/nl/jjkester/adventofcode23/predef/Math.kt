package nl.jjkester.adventofcode23.predef

/**
 * Lowest common multiple of two longs [a] and [b].
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
