package nl.jjkester.adventofcode23.day20

import nl.jjkester.adventofcode23.day20.model.ControlSystem
import nl.jjkester.adventofcode23.day20.model.ModuleDefinition
import nl.jjkester.adventofcode23.day20.model.ModuleName
import nl.jjkester.adventofcode23.execution.isDay
import nl.jjkester.adventofcode23.predef.algorithms.lcm

/**
 * https://adventofcode.com/2023/day/20
 */
object PulsePropagation {

    /**
     * Parses the [input] string into a list of [module definitions][ModuleDefinition].
     */
    fun parseInput(input: String): List<ModuleDefinition> = input.lines().map(ModuleDefinition::parse)

    /**
     * Part one.
     */
    fun pulsesAfter1000Presses(modules: List<ModuleDefinition>) = ControlSystem.of(modules)
        .apply {
            repeat(1000) {
                pressButton()
            }
        }
        .counts
        .values
        .reduce(Long::times)

    /**
     * Part two.
     */
    fun buttonPressesForRx(modules: List<ModuleDefinition>): Long {
        val controlSystem = ControlSystem.of(modules)
        val conjunctions = controlSystem.conjunctionsForRx()
        val counters = mutableMapOf<ModuleName, Long>()

        generateSequence(1L, Long::inc)
            .takeWhile { counter ->
                controlSystem.pressButton()

                conjunctions.forEach {
                    if (it.sentHighPulse) {
                        counters.computeIfAbsent(it.name) { counter }
                    }
                }

                counters.size < conjunctions.size
            }
            .last()

        return counters.values.reduce(::lcm)
    }
}

fun main() {
    PulsePropagation.isDay(20) {
        parsedWith { parseInput(it) } first { pulsesAfter1000Presses(it) } then { buttonPressesForRx(it) }
    }
}
