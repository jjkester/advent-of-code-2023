package nl.jjkester.adventofcode23.predef.space

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

/**
 * Implementation of [AreaMap] using an array of numbers to enable efficient matrix operations.
 */
open class NumberMultikAreaMap<T : Number>(data: D2Array<T>) : MultikAreaMap<T, T>(data) {

    final override fun serialize(element: T): T = element

    final override fun deserialize(value: T): T = value
}
