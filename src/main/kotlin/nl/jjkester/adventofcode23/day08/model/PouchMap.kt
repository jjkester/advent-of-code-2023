package nl.jjkester.adventofcode23.day08.model

import nl.jjkester.adventofcode23.predef.graph.Edge
import nl.jjkester.adventofcode23.predef.graph.Graph
import nl.jjkester.adventofcode23.predef.graph.HashGraph
import nl.jjkester.adventofcode23.predef.graph.Vertex
import nl.jjkester.adventofcode23.predef.sections

/**
 * A map containing a graph containing vertices with two outgoing edges (left, right), a start vertex, a way to
 * determine whether an end has been reached, and a list of instructions to follow until the end.
 *
 * @property instructions Instructions to follow. When the instructions run out, the list is repeated.
 * @property graph Graph representing the map and directions that can be taken.
 * @property start The place on the map to start at.
 * @property isEnd Function to determine whether an end has been reached.
 */
class PouchMap private constructor(
    private val instructions: List<Instruction>,
    private val graph: Graph<String, Node, Connection>,
    private val start: Node,
    private val isEnd: (Node) -> Boolean
) {

    /**
     * Lazily walks the map following the instructions, yielding whether an end has been reached.
     *
     * This sequence is infinite and will never complete.
     */
    fun walk(): Sequence<Boolean> = sequence {
        var node = start
        var index = 0

        sequence { while (true) yieldAll(instructions) }.forEach { instruction ->
            yield(isEnd(node))

            node = checkNotNull(graph.edgesFrom(node).singleOrNull { it.instruction == instructions[index] }?.to) {
                "Instruction does not resolve to a node"
            }

            index = if (index < instructions.lastIndex) index + 1 else 0
        }
    }

    /**
     * Interprets this map as a map for ghosts, and returns the sequence of resulting maps contained in this single map.
     */
    fun withGhosts(): Sequence<PouchMap> = graph.vertices.asSequence()
        .filter { it.element.endsWith('A') }
        .map { start -> PouchMap(instructions, graph, start) { it.element.endsWith('Z') } }

    companion object {
        private val regex = Regex("""(\w+)\s*=\s*\(\s*(\w+)\s*,\s*(\w+)\s*\)""")

        /**
         * Parses the [input] string to a [PouchMap] and returns it.
         *
         * @param input The [input] string to parse.
         * @return The [PouchMap] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string is not a valid [PouchMap].
         */
        fun parse(input: String): PouchMap {
            val (instructions, treeInput) = input.sections()

            val parsedTreeInput = treeInput.lines().map { line ->
                val result = requireNotNull(regex.matchEntire(line)) { "Input is not a valid map" }
                val (it, left, right) = result.destructured
                it to (left to right)
            }

            require(parsedTreeInput.isNotEmpty()) { "Input does not contain any nodes" }

            val nodes = mutableMapOf<String, Node>()

            val edges = parsedTreeInput.flatMap { (key, value) ->
                val node = nodes.computeIfAbsent(key, ::Node)
                listOf(
                    Connection(node, nodes.computeIfAbsent(value.first, ::Node), Instruction.Left),
                    Connection(node, nodes.computeIfAbsent(value.second, ::Node), Instruction.Right)
                )
            }

            return PouchMap(
                instructions.map(Instruction::parse),
                HashGraph(edges),
                requireNotNull(nodes["AAA"]) { "Input does not define the start node" }
            ) { it == requireNotNull(nodes["ZZZ"]) { "Input does not define the end node" } }
        }
    }

    private data class Node(override val element: String) : Vertex<String>

    private data class Connection(override val from: Node, override val to: Node, val instruction: Instruction) : Edge<Node> {
        override val directional: Boolean = true

        override fun contains(vertex: Node): Boolean = vertex == from || vertex == to
    }
}
