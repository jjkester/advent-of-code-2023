package nl.jjkester.adventofcode23.day20.model

/**
 * Definition of a module.
 *
 * @property name Name of the module.
 * @property type Type of the module.
 * @property destinations Other modules this module sends pulses to.
 */
data class ModuleDefinition(val name: ModuleName, val type: Type, val destinations: List<ModuleName>) {

    companion object {
        private val regex = Regex(
            """(?<left>(?<type>[%&])(?<name>\w+)|broadcaster)\s+->\s*(?<dests>(\w+\s*,\s*)*\w+)"""
        )

        /**
         * Parses the [input] string into a [ModuleDefinition] and returns it.
         *
         * @param input The input string to parse.
         * @return The [ModuleDefinition] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [ModuleDefinition].
         */
        fun parse(input: String): ModuleDefinition {
            val result = requireNotNull(regex.matchEntire(input)) { "Input is not a valid module definition" }

            val destinations = result.groups["dests"]?.value?.split(',')
                ?.map { ModuleName(it.trim()) }
                .orEmpty()

            return if (result.groups["left"]?.value == "broadcaster") {
                ModuleDefinition(ModuleName("broadcaster"), Type.Broadcaster, destinations)
            } else {
                ModuleDefinition(
                    name = ModuleName(requireNotNull(result.groups["name"]?.value) { "Name is required" }),
                    type = when (result.groups["type"]?.value) {
                        "&" -> Type.Conjunction
                        "%" -> Type.FlipFlop
                        else -> throw IllegalArgumentException("Invalid module type")
                    },
                    destinations = destinations
                )
            }
        }
    }

    /**
     * Type of module in a [ModuleDefinition].
     */
    enum class Type {
        /** A module that broadcasts its incoming pulses unmodified. */
        Broadcaster,

        /** A module that keeps state per upstream module to determine the outgoing pulses. */
        Conjunction,

        /** A module that keeps state based on a single incoming pulse. */
        FlipFlop
    }
}
