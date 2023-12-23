package nl.jjkester.adventofcode23.day19.model

/**
 * A rule in a [Workflow]. Each rule checks a distinct aspect of a [Part].
 */
sealed class WorkflowRule {

    /**
     * The action to take if a part matches this rule.
     */
    abstract val action: WorkflowAction

    protected abstract fun predicate(part: Part): Boolean

    /**
     * Applies the [part] to this rule. Returns the [action] if the rule applies for the [part], or `null` otherwise.
     *
     * @param part The part to inspect.
     * @return The [action] when the [part] matches the rule, or `null` if the rule does not apply.
     */
    fun apply(part: Part): WorkflowAction? = action.takeIf { predicate(part) }

    /**
     * A rule that checks whether the rating of a part in the [category] is less than the [rating].
     *
     * @property category The category to check.
     * @property rating The maximum rating (exclusive).
     * @property action The action to take when a part matches this rule.
     */
    data class LessThan(
        val category: Category,
        val rating: Int,
        override val action: WorkflowAction
    ) : WorkflowRule() {

        override fun predicate(part: Part): Boolean = part[category] < rating
    }

    /**
     * A rule that checks whether the rating of a part in the [category] is greater than the [rating].
     *
     * @property category The category to check.
     * @property rating The minimum rating (exclusive).
     * @property action The action to take when a part matches this rule.
     */
    data class GreaterThan(
        val category: Category,
        val rating: Int,
        override val action: WorkflowAction
    ) : WorkflowRule() {

        override fun predicate(part: Part): Boolean = part[category] > rating
    }

    /**
     * A rule that matches all parts.
     *
     * @property action The action to take for all parts.
     */
    data class All(override val action: WorkflowAction) : WorkflowRule() {

        override fun predicate(part: Part): Boolean = true
    }

    companion object {
        private val regex = Regex("""(?<a>(?<attr>[xmas])\s*(?<op>[<>])\s*(?<num>\d+)\s*:\s*)?(?<dest>\w+)""")

        /**
         * Parses the [input] string to a [WorkflowRule] and returns it.
         *
         * @param input The input string to parse.
         * @return The [WorkflowRule] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string does not represent a valid [WorkflowRule].
         */
        fun parse(input: String): WorkflowRule {
            val result = requireNotNull(regex.matchEntire(input)) { "Input is not a valid workflow rule" }

            val action = WorkflowAction.parse(
                requireNotNull(result.groups["dest"]?.value) { "Input does not have a valid destination" }
            )

            return if (result.groups["a"] != null) {
                val category = Category.parse(
                    requireNotNull(result.groups["attr"]?.value?.singleOrNull()) {
                        "Input does not check a valid attribute"
                    }
                )
                val number = requireNotNull(result.groups["num"]?.value?.toIntOrNull()) {
                    "Input does not check a valid number"
                }

                when (result.groups["op"]?.value) {
                    "<" -> LessThan(category, number, action)
                    ">" -> GreaterThan(category, number, action)
                    else -> throw IllegalArgumentException("Input does not have a valid operator")
                }
            } else {
                All(action)
            }
        }
    }
}
