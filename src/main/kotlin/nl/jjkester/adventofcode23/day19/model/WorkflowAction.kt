package nl.jjkester.adventofcode23.day19.model

/**
 * The result of applying a [Workflow] to a [Part].
 */
sealed class WorkflowAction {

    /**
     * The part is accepted.
     */
    data object Accepted : WorkflowAction()

    /**
     * The part is rejected.
     */
    data object Rejected : WorkflowAction()

    /**
     * The part needs to be checked by the workflow identified by the [workflowName].
     *
     * @property workflowName The name of the workflow to apply to the part.
     */
    data class Continue(val workflowName: WorkflowName) : WorkflowAction()

    companion object {

        /**
         * Parses the [input] string to a [WorkflowAction] and returns it.
         *
         * @param input The input string to parse.
         * @return The [WorkflowAction] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [WorkflowAction].
         */
        fun parse(input: String) = when (input) {
            "A" -> Accepted
            "R" -> Rejected
            else -> Continue(WorkflowName(input))
        }
    }
}
