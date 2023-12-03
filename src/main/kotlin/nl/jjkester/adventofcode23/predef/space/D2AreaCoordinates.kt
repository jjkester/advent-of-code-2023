package nl.jjkester.adventofcode23.predef.space

private class D2AreaCoordinates(private val area: D2Area) : Set<D2Coordinate> {
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
fun D2Area.coordinates(): Set<D2Coordinate> = D2AreaCoordinates(this)
