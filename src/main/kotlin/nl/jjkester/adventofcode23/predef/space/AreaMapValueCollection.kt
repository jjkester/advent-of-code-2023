package nl.jjkester.adventofcode23.predef.space

private class D2AreaMapValues<T>(private val areaMap: AreaMap<T>) : Collection<T> {
    override val size: Int
        get() = areaMap.size

    override fun isEmpty(): Boolean = areaMap.isEmpty()

    override fun iterator(): Iterator<T> = iterator {
        areaMap.y.forEach { y ->
            areaMap.x.forEach { x ->
                yield(areaMap[x, y])
            }
        }
    }

    override fun containsAll(elements: Collection<T>): Boolean = elements.all(::contains)

    override fun contains(element: T): Boolean = areaMap.contains(element)
}

fun <T> AreaMap<T>.values(): Collection<T> = D2AreaMapValues(this)
