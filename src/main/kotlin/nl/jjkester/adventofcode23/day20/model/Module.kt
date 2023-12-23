package nl.jjkester.adventofcode23.day20.model

/**
 * A stateful module of a [ControlSystem].
 */
sealed class Module {

    private val queue = ArrayDeque<Pair<ModuleName, Pulse>>()

    /**
     * The name of the module.
     */
    abstract val name: ModuleName

    /**
     * Registers the [module] as a source for this module.
     */
    open fun connectFrom(module: ModuleName) {
        // No default behavior.
    }

    /**
     * Queues the received [pulse] for processing.
     *
     * @param from The module sending the [pulse].
     * @param pulse The pulse that is received.
     */
    fun receive(from: ModuleName, pulse: Pulse) {
        queue.addLast(from to pulse)
    }

    /**
     * Runs a step to process a single queued pulse.
     *
     * @return The effect of the pulse ([PulseResult.Success]) or [PulseResult.Empty] when there was no pulse to
     * process.
     */
    fun step(): PulseResult = queue.removeFirstOrNull()
        ?.let { (from, pulse) -> PulseResult.Success(transform(from, pulse)) }
        ?: PulseResult.Empty

    protected abstract fun transform(from: ModuleName, pulse: Pulse): Pulse?

    /**
     * The broadcaster module. This module is stateless.
     */
    data object Broadcaster : Module() {

        override val name: ModuleName = ModuleName("broadcaster")

        override fun transform(from: ModuleName, pulse: Pulse): Pulse = pulse
    }

    /**
     * A conjunction module. The module will emit a low pulse for every incoming pulse unless the previous pulse from
     * every connected module was a high pulse, in which case a high pulse is emitted.
     *
     * @property name The name of the module.
     */
    data class Conjunction(override val name: ModuleName) : Module() {

        private val memory = mutableMapOf<ModuleName, Pulse>()

        /**
         * Whether this module has sent a high pulse at least once.
         */
        var sentHighPulse: Boolean = false
            private set

        override fun connectFrom(module: ModuleName) {
            memory[module] = Pulse.Low
        }

        override fun transform(from: ModuleName, pulse: Pulse): Pulse {
            memory[from] = pulse
            return if (Pulse.Low !in memory.values) Pulse.Low else Pulse.High.also { sentHighPulse = true }
        }
    }

    /**
     * A flip-flop module. This module will switch on or off on every received low pulse. When the state is switched on,
     * a high pulse is emitted. When the state is switched off, a low pulse is emitted. Incoming high pulses never
     * result in an outgoing pulse.
     *
     * @property name The name of the module.
     */
    data class FlipFlop(override val name: ModuleName) : Module() {

        private var state = false

        override fun transform(from: ModuleName, pulse: Pulse): Pulse? = when (pulse) {
            Pulse.Low -> {
                state = !state
                if (state) Pulse.High else Pulse.Low
            }

            Pulse.High -> null
        }
    }
}
