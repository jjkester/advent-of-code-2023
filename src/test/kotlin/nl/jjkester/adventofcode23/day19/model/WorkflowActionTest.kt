package nl.jjkester.adventofcode23.day19.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class WorkflowActionTest {

    @Test
    fun `parse results in accepted`() {
        assertThat(WorkflowAction.parse("A"))
            .isEqualTo(WorkflowAction.Accepted)
    }

    @Test
    fun `parse results in rejected`() {
        assertThat(WorkflowAction.parse("R"))
            .isEqualTo(WorkflowAction.Rejected)
    }

    @ParameterizedTest
    @ValueSource(strings = ["a", "r", "abc", "AR", "RA", "foo", "bar", "qux", ""])
    fun `parse results in continue`(input: String) {
        assertThat(WorkflowAction.parse(input))
            .isInstanceOf<WorkflowAction.Continue>()
            .transform { it.workflowName.value }
            .isEqualTo(input)
    }
}
