package nl.jjkester.adventofcode23.day15.model

/**
 * Holiday ASCII utility.
 */
object HolidayAscii {

    /**
     * Hashes the [string] with the Holiday ASCII hashing algorithm.
     */
    fun hash(string: String): Short = string.asSequence()
        .map(Char::code)
        .fold(0) { acc, code -> ((acc + code) * 17 % 256).toShort() }
}
