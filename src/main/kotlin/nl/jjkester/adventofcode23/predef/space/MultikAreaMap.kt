package nl.jjkester.adventofcode23.predef.space

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.contains

/**
 * Abstract implementation of [AreaMap] using an array of numbers to enable efficient matrix operations.
 */
abstract class MultikAreaMap<T, U : Number>(
    protected val data: D2Array<U>
) : AbstractAreaMap<T>(), Iterable<Pair<Coordinate2D, T>> {

    override val x: IntRange = 0..<data.shape[0]

    override val y: IntRange = 0..<data.shape[1]

    override fun get(x: Int, y: Int): T = deserialize(data[x, y])

    override fun contains(element: T): Boolean = data.contains(serialize(element))

    override fun iterator(): Iterator<Pair<Coordinate2D, T>> = iterator {
        y.forEach { y ->
            x.forEach { x ->
                yield(coordinateOf(x, y) to get(x, y))
            }
        }
    }

    override fun equals(other: Any?): Boolean = (other as? MultikAreaMap<*, *>)
        ?.let {
            this::class.java.isInstance(other) && data == other.data
        } ?: false

    override fun hashCode(): Int = data.hashCode()

    /**
     * Returns the serialized numeric value for the [element].
     *
     * @param element Element to serialize.
     * @return Serialized numeric value for the [element].
     */
    protected abstract fun serialize(element: T): U

    /**
     * Returns the deserialized element corresponding to the numeric [value].
     *
     * @param value Serialized numeric value.
     * @return Element for the numeric [value].
     */
    protected abstract fun deserialize(value: U): T
}
