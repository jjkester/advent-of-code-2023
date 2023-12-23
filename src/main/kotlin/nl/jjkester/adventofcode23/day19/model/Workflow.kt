package nl.jjkester.adventofcode23.day19.model

/**
 * A workflow for accepting or rejecting parts.
 *
 * @property name Name of the workflow. This name can be referenced by other workflows.
 * @property rules Ordered rules that are executed by this workflow.
 */
data class Workflow(val name: WorkflowName, val rules: List<WorkflowRule>) {

    /**
     * Applies this workflow to the [part] and returns the resulting [action][WorkflowAction].
     *
     * @param part The part to accept or reject.
     * @return The [WorkflowAction] for the [part].
     */
    fun apply(part: Part): WorkflowAction = rules.asSequence()
        .mapNotNull { it.apply(part) }
        .first()

    companion object {

        /**
         * Parses the [input] string to a [Workflow] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Workflow] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [Workflow].
         */
        fun parse(input: String): Workflow {
            val (name, remainder) = input.split('{', limit = 2)
            val rules = remainder.removeSuffix("}").split(",")

            return Workflow(WorkflowName(name), rules.map { WorkflowRule.parse(it.trim()) })
        }
    }
}
