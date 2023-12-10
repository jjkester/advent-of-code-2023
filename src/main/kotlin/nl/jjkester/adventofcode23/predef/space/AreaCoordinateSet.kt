package nl.jjkester.adventofcode23.predef.space

private class AreaCoordinateSet(private val area: Area) : Set<Coordinate2D> {
    override val size: Int
        get() = area.size

    override fun isEmpty(): Boolean = area.isEmpty()

    override fun iterator(): Iterator<Coordinate2D> = iterator {
        area.y.forEach { y ->
            area.x.forEach { x ->
                yield(Coordinate2D(x, y))
            }
        }
    }

    override fun containsAll(elements: Collection<Coordinate2D>): Boolean = elements.all(::contains)

    override fun contains(element: Coordinate2D): Boolean = area.contains(element)
}

/**
 * Returns a collection of the coordinates that are covered by this area.
 */
fun Area.coordinates(): Set<Coordinate2D> = AreaCoordinateSet(this)
