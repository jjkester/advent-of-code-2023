package nl.jjkester.adventofcode23.predef

/**
 * Returns the sections in this string as list. Sections are separated by two or more line separators.
 */
fun String.sections(): List<String> = split(sectionSeparatorRegex)

/**
 * Returns the sections in this string as sequence. Sections are separated by two or more line separators.
 */
fun String.sectionSequence(): Sequence<String> = splitToSequence(sectionSeparatorRegex)

private val sectionSeparatorRegex = Regex("""(${System.lineSeparator()}){2,}""")
