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
    fun leastHeatLoss(cityMap: CityMap): Int = shortestPathFast(
        cityMap = cityMap,
        neighbours = { crucibleNeighbours() },
        endCondition = { true }
    ).cost

    /**
     * Part two.
     */
    fun leastHeatLossUltra(cityMap: CityMap): Int = shortestPathFast(
        cityMap = cityMap,
        neighbours = { ultraCrucibleNeighbours() },
        endCondition = { line > 3 }
    ).cost

    private fun shortestPathFast(
        cityMap: CityMap,
        neighbours: CheckedVertex<Coordinate2D>.() -> Iterable<CheckedVertex<Coordinate2D>>,
        endCondition: CheckedVertex<Coordinate2D>.() -> Boolean
    ) = aStar(
        start = CheckedVertex(cityMap.start, null, 0),
        isEnd = { it.vertex == cityMap.end && it.endCondition() },
        neighbourSelector = { checkedVertex -> checkedVertex.neighbours().filter { (to) -> to in cityMap.data } },
        costFunction = { _, it -> cityMap.data[it.vertex] },
        estimatedCostFunction = { cityMap.data[it.vertex] }
    )

    private fun CheckedVertex<Coordinate2D>.crucibleNeighbours() = vertex.neighbours()
        .mapNotNull { (coordinate, direction) ->
            val notReversing = this.direction == null || direction != this.direction.inverse()
            val maximumStraight = line < 3 || direction != this.direction

            if (notReversing && maximumStraight) {
                CheckedVertex(coordinate, direction, if (this.direction == direction) line + 1 else 1)
            } else {
                null
            }
        }
        .asIterable()

    private fun CheckedVertex<Coordinate2D>.ultraCrucibleNeighbours() = vertex.neighbours()
        .mapNotNull { (coordinate, direction) ->
            val notReversing = this.direction == null || direction != this.direction.inverse()
            val minimumStraight = this.direction == null || line > 3 || this.direction == direction
            val maximumStraight = line < 10 || direction != this.direction

            if (notReversing && minimumStraight && maximumStraight) {
                CheckedVertex(coordinate, direction, if (this.direction == direction) line + 1 else 1)
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
