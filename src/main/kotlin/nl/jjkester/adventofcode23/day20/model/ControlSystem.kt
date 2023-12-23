package nl.jjkester.adventofcode23.day20.model

/**
 * A stateful system with [modules] and [connections] between modules.
 */
class ControlSystem(
    val modules: List<Module>,
    val connections: Map<ModuleName, Set<ModuleName>>
) {

    private val moduleMap = modules.associateBy { it.name }

    private val counters = mutableMapOf<Pulse, Long>()

    /**
     * Number of pulses that occurred in the system per type of pulse.
     */
    val counts: Map<Pulse, Long>
        get() = counters.toMap()

    init {
        connections.forEach { (from, to) -> to.forEach { moduleMap[it]?.connectFrom(from) } }
    }

    /**
     * A button press that triggers pulses in the system.
     */
    fun pressButton() {
        send(Pulse.Low, button, broadcaster)

        var anyActive = true

        while (anyActive) {
            anyActive = false

            modules.forEach { module ->
                (module.step() as? PulseResult.Success)?.also { result ->
                    anyActive = true
                    result.pulse?.also { pulse ->
                        connections[module.name]?.forEach { destination ->
                            send(pulse, module.name, destination)
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the set of modules that must all emit [Pulse.High] in order for the "rx" module to turn on the final
     * machine.
     */
    fun conjunctionsForRx(): Set<Module.Conjunction> = getSources(conjunctionForRx().name)
        .mapNotNullTo(mutableSetOf()) { moduleMap[it] as? Module.Conjunction }

    /**
     * The [Module.Conjunction] module responsible for controlling the "rx" module.
     */
    private fun conjunctionForRx(): Module.Conjunction {
        val sourceForRx = checkNotNull(getSources(rx).singleOrNull()?.let(moduleMap::get)) {
            "Multiple ways to reach rx"
        }
        return checkNotNull(sourceForRx as? Module.Conjunction) { "Source for rx is not a conjunction" }
    }

    private fun send(pulse: Pulse, from: ModuleName, to: ModuleName) {
        moduleMap[to]?.receive(from, pulse)
        counters.compute(pulse) { _, current -> (current ?: 0) + 1 }
    }

    private fun getSources(to: ModuleName): Set<ModuleName> = connections
        .mapNotNullTo(mutableSetOf()) { (key, value) -> key.takeIf { to in value } }

    companion object {
        private val button = ModuleName("button")
        private val broadcaster = ModuleName("broadcaster")
        private val rx = ModuleName("rx")

        /**
         * Parses the [input] string to a [ControlSystem] and returns it.
         *
         * @param input The input string to parse.
         * @return The [ControlSystem] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [ControlSystem].
         */
        fun parse(input: String): ControlSystem = of(input.lines().map(ModuleDefinition::parse))

        /**
         * Creates and returns a new [ControlSystem] from the [moduleDefinitions].
         *
         * @param moduleDefinitions Definitions of the modules in this control system.
         * @return A new [ControlSystem] that is configured according to the [moduleDefinitions].
         */
        fun of(moduleDefinitions: Iterable<ModuleDefinition>): ControlSystem = ControlSystem(
            modules = moduleDefinitions.map { definition ->
                when (definition.type) {
                    ModuleDefinition.Type.Broadcaster -> Module.Broadcaster
                    ModuleDefinition.Type.Conjunction -> Module.Conjunction(definition.name)
                    ModuleDefinition.Type.FlipFlop -> Module.FlipFlop(definition.name)
                }
            },
            connections = moduleDefinitions.associateTo(mutableMapOf()) { it.name to it.destinations.toSet() }
        )
    }
}
