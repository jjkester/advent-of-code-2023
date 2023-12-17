package nl.jjkester.adventofcode23.day17

import nl.jjkester.adventofcode23.day17.model.CheckedVertex
import nl.jjkester.adventofcode23.day17.model.CityMap
import nl.jjkester.adventofcode23.day17.model.Direction
import nl.jjkester.adventofcode23.execution.isDay
import nl.jjkester.adventofcode23.predef.space.Coordinate2D
import nl.jjkester.adventofcode23.predef.space.by
import nl.jjkester.adventofcode23.predef.space.contains
import nl.jjkester.adventofcode23.predef.space.get

/**
 * https://adventofcode.com/2023/day/17
 */
object ClumsyCrucible {

    /**
     * Part one.
     */
    fun leastHeatLoss(cityMap: CityMap): Int = shortestPathFast(cityMap) { crucibleNeighbours() }.cost

    /**
     * Part two.
     */
    fun leastHeatLossUltra(cityMap: CityMap): Int = shortestPathFast(cityMap) { ultraCrucibleNeighbours() }.cost

    private fun shortestPathFast(
        cityMap: CityMap,
        neighbours: CheckedVertex<Coordinate2D>.() -> Iterable<CheckedVertex<Coordinate2D>>
    ) = aStar(
        start = CheckedVertex(cityMap.start, emptyList()),
        isEnd = { it.vertex == cityMap.end },
        neighbourSelector = { checkedVertex -> checkedVertex.neighbours().filter { (to) -> to in cityMap.data } },
        costFunction = { _, it -> cityMap.data[it.vertex] },
        estimatedCostFunction = { cityMap.data[it.vertex] }
    )

    private fun CheckedVertex<Coordinate2D>.crucibleNeighbours() = vertex.neighbours()
        .mapNotNull { (coordinate, direction) ->
            val notReversing = directions.isEmpty() || direction != directions.last().inverse()
            val maximumStraight = line < 3 || direction != directions.last()

            if (notReversing && maximumStraight) {
                CheckedVertex(coordinate, directions.takeLast(2) + direction)
            } else {
                null
            }
        }
        .asIterable()

    private fun CheckedVertex<Coordinate2D>.ultraCrucibleNeighbours() = vertex.neighbours()
        .mapNotNull { (coordinate, direction) ->
            val notReversing = directions.isEmpty() || direction != directions.last().inverse()
            val minimumStraight = directions.isEmpty() || line > 3 || directions.last() == direction
            val maximumStraight = line < 9 || direction != directions.last()

            if (notReversing && minimumStraight && maximumStraight) {
                CheckedVertex(coordinate, directions.takeLast(9) + direction)
            } else {
                null
            }
        }
        .asIterable()

    private fun Coordinate2D.neighbours(): Sequence<Pair<Coordinate2D, Direction>> = sequenceOf(
        x by y - 1 to Direction.North,
        x + 1 by y to Direction.East,
        x by y + 1 to Direction.South,
        x - 1 by y to Direction.West
    )
}

fun main() {
    ClumsyCrucible.isDay(17) {
        parsedWith { CityMap.parse(it) } first { leastHeatLoss(it) } then { leastHeatLossUltra(it) }
    }
}
