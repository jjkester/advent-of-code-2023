package nl.jjkester.adventofcode23.predef.space

private class VolumeCoordinateSet(private val volume: Volume) : Set<D3Coordinate> {
    override val size: Int
        get() = volume.size

    override fun isEmpty(): Boolean = volume.isEmpty()

    override fun iterator(): Iterator<D3Coordinate> = iterator {
        volume.z.forEach { z ->
            volume.y.forEach { y ->
                volume.x.forEach { x ->
                    yield(D3Coordinate(x, y, z))
                }
            }
        }
    }

    override fun containsAll(elements: Collection<D3Coordinate>): Boolean = elements.all(::contains)

    override fun contains(element: D3Coordinate): Boolean = volume.contains(element)
}

/**
 * Returns a collection of the coordinates that are covered by this volume.
 */
fun Volume.coordinates(): Set<D3Coordinate> = VolumeCoordinateSet(this)
