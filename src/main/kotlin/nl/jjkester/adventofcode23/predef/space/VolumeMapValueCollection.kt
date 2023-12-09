package nl.jjkester.adventofcode23.predef.space

private class VolumeMapValueCollection<T>(private val volumeMap: VolumeMap<T>) : Collection<T> {
    override val size: Int
        get() = volumeMap.size

    override fun isEmpty(): Boolean = volumeMap.isEmpty()

    override fun iterator(): Iterator<T> = iterator {
        volumeMap.z.forEach { z ->
            volumeMap.y.forEach { y ->
                volumeMap.x.forEach { x ->
                    yield(volumeMap[x, y, z])
                }
            }
        }
    }

    override fun containsAll(elements: Collection<T>): Boolean = elements.all(::contains)

    override fun contains(element: T): Boolean = volumeMap.contains(element)
}

/**
 * Returns the collection of values in this volume map.
 */
fun <T> VolumeMap<T>.values(): Collection<T> = VolumeMapValueCollection(this)
