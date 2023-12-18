package nl.jjkester.adventofcode23.day17

import java.util.*

/**
 * A* shortest path algorithm.
 *
 * @param T Type of node.
 * @param start The node to start from.
 * @param isEnd Whether an end node has been reached.
 * @param neighbourSelector Function to find neighbours for a given node.
 * @param costFunction Function to find the cost to travel between to nodes.
 * @param estimatedCostFunction Function to estimate the cost of traveling to a node.
 * @return Shortest path between the [start] node and an [end][isEnd] node.
 */
fun <T : Any> aStar(
    start: T,
    isEnd: (T) -> Boolean,
    neighbourSelector: (T) -> Iterable<T>,
    costFunction: (T, T) -> Int,
    estimatedCostFunction: (T) -> Int
): ShortestPath<T> {
    val queue = PriorityQueue(listOf(Scored(start, 0, estimatedCostFunction(start))))
    val seen = mutableMapOf(start to Seen<T>(null, 0))
    var end: Scored<T>? = null

    while (queue.isNotEmpty() && end == null) {
        val current = queue.remove()

        if (isEnd(current.value)) {
            end = current
        }

        neighbourSelector(current.value)
            .map { candidate ->
                Scored(
                    value = candidate,
                    cost = current.cost + costFunction(current.value, candidate),
                    estimatedCost = estimatedCostFunction(candidate)
                )
            }
            .filter { scoredCandidate ->
                seen[scoredCandidate.value]
                    ?.let { scoredCandidate.cost < it.cost }
                    ?: true
            }
            .forEach { scoredCandidate ->
                seen[scoredCandidate.value] = Seen(current.value, scoredCandidate.cost)
                if (scoredCandidate !in queue) queue.add(scoredCandidate)
            }
    }

    checkNotNull(end) { "No end is reachable from the start vertex" }

    return ShortestPath(reconstructPath(end.value, seen), end.cost)
}

private fun <T : Any> reconstructPath(end: T, seen: Map<T, Seen<T>>): List<T> = buildList {
    var current: T? = end

    while (current != null) {
        add(0, current)
        current = seen[current]?.previous
    }
}

/**
 * A shortest path between two nodes.
 *
 * @property path The found path.
 * @property cost The cost of the found path.
 */
data class ShortestPath<out T>(val path: List<T>, val cost: Int)

private data class Seen<T>(val previous: T?, val cost: Int)

private data class Scored<T>(val value: T, val cost: Int, val estimatedCost: Int) : Comparable<Scored<T>> {

    override fun compareTo(other: Scored<T>): Int = (cost + estimatedCost).compareTo(other.cost + other.estimatedCost)
}
