package nl.jjkester.adventofcode23.predef.space

private class VolumeCoordinateSet(private val volume: Volume) : Set<Coordinate3D> {
    override val size: Int
        get() = volume.size

    override fun isEmpty(): Boolean = volume.isEmpty()

    override fun iterator(): Iterator<Coordinate3D> = iterator {
        volume.z.forEach { z ->
            volume.y.forEach { y ->
                volume.x.forEach { x ->
                    yield(Coordinate3D(x, y, z))
                }
            }
        }
    }

    override fun containsAll(elements: Collection<Coordinate3D>): Boolean = elements.all(::contains)

    override fun contains(element: Coordinate3D): Boolean = volume.contains(element)
}

/**
 * Returns a collection of the coordinates that are covered by this volume.
 */
fun Volume.coordinates(): Set<Coordinate3D> = VolumeCoordinateSet(this)
