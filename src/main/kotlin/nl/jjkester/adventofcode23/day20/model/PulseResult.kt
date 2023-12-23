package nl.jjkester.adventofcode23.day20.model

/**
 * Result of processing a single pulse in a [Module].
 */
sealed class PulseResult {

    /**
     * A pulse was processed, and the module sends an outgoing [pulse] to downstream modules. No pulse is sent
     * downstream when [pulse] is `null`.
     *
     * @property pulse The pulse to send downstream, or `null` when no pulse was emitted.
     */
    data class Success(val pulse: Pulse?) : PulseResult()

    /**
     * No pulse was processed. When all modules yield this result, the system has become stable again.
     */
    data object Empty : PulseResult()
}
