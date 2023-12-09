package nl.jjkester.adventofcode23.predef.space

private class AreaCoordinateSet(private val area: Area) : Set<D2Coordinate> {
    override val size: Int
        get() = area.size

    override fun isEmpty(): Boolean = area.isEmpty()

    override fun iterator(): Iterator<D2Coordinate> = iterator {
        area.y.forEach { y ->
            area.x.forEach { x ->
                yield(D2Coordinate(x, y))
            }
        }
    }

    override fun containsAll(elements: Collection<D2Coordinate>): Boolean = elements.all(::contains)

    override fun contains(element: D2Coordinate): Boolean = area.contains(element)
}

/**
 * Returns a collection of the coordinates that are covered by this area.
 */
fun Area.coordinates(): Set<D2Coordinate> = AreaCoordinateSet(this)
