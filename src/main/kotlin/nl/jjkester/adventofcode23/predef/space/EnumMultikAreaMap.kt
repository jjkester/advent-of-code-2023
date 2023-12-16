package nl.jjkester.adventofcode23.predef.space

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import kotlin.enums.EnumEntries

/**
 * Abstract implementation of [AreaMap] using an array of numbers to enable efficient matrix operations with data
 * represented as enums.
 */
abstract class EnumMultikAreaMap<T : Enum<T>>(
    private val entries: EnumEntries<T>,
    data: D2Array<Int>
) : MultikAreaMap<T, Int>(data) {

    final override fun serialize(element: T): Int = element.ordinal

    final override fun deserialize(value: Int): T = checkNotNull(entries.getOrNull(value)) {
        "Could not determine enum value from ordinal '$value', out of range"
    }
}
