package nl.jjkester.adventofcode23.day19.model

import nl.jjkester.adventofcode23.predef.sections

data class PartSystem(val workflows: List<Workflow>, val parts: List<Part>) {

    private val allParts = (1..4000).let { AcceptableParts(it, it, it, it) }

    private val workflowMap = workflows.associateBy { it.name.value }

    init {
        require("in" in workflowMap) { "Missing starting workflow with name 'in'" }
    }

    fun acceptedParts(): List<Part> = parts.filter(::isAccepted)

    fun acceptableParts(): Sequence<AcceptableParts> = acceptableParts(allParts, this[start])

    private fun acceptableParts(initial: AcceptableParts, workflow: Workflow): Sequence<AcceptableParts> = sequence {
        var limited = initial

        for (rule in workflow.rules) {
            val (matching, remaining) = when (rule) {
                is WorkflowRule.LessThan -> Pair(
                    limited.coerceIn(rule.category, lt(rule.rating)),
                    limited.coerceIn(rule.category, gte(rule.rating))
                )

                is WorkflowRule.GreaterThan -> Pair(
                    limited.coerceIn(rule.category, gt(rule.rating)),
                    limited.coerceIn(rule.category, lte(rule.rating))
                )

                is WorkflowRule.All -> limited to null
            }

            when (val action = rule.action) {
                WorkflowAction.Accepted -> yield(matching)
                is WorkflowAction.Continue -> yieldAll(acceptableParts(matching, this@PartSystem[action.workflowName]))
                WorkflowAction.Rejected -> Unit
            }

            limited = remaining ?: break
        }
    }

    private fun isAccepted(part: Part): Boolean {
        var result: WorkflowAction? = null

        do {
            result = this[(result as? WorkflowAction.Continue)?.workflowName ?: start].apply(part)
        } while (result is WorkflowAction.Continue)

        return result == WorkflowAction.Accepted
    }

    private operator fun get(name: WorkflowName): Workflow = checkNotNull(workflowMap[name.value]) {
        "Reference to unknown workflow"
    }

    companion object {
        private val start = WorkflowName("in")

        fun parse(input: String): PartSystem = input.sections().let { (first, second) ->
            PartSystem(first.lines().map(Workflow::parse), second.lines().map(Part::parse))
        }

        private fun lt(number: Int) = Int.MIN_VALUE..<number
        private fun lte(number: Int) = Int.MIN_VALUE..number
        private fun gte(number: Int) = number..Int.MAX_VALUE
        private fun gt(number: Int) = number + 1..Int.MAX_VALUE
    }
}
