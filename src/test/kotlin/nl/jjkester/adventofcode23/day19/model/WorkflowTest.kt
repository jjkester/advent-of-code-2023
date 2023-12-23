package nl.jjkester.adventofcode23.day19.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

class WorkflowTest {

    private val systemUnderTest = Workflow(
        name = WorkflowName("px"),
        rules = listOf(
            WorkflowRule.LessThan(Category.A, 2006, WorkflowAction.Continue(WorkflowName("qkq"))),
            WorkflowRule.GreaterThan(Category.M, 2090, WorkflowAction.Accepted),
            WorkflowRule.All(WorkflowAction.Continue(WorkflowName("rfg")))
        )
    )

    @Test
    fun parse() {
        val testInput = "px{a<2006:qkq,m>2090:A,rfg}"

        assertThat(Workflow.parse(testInput))
            .isEqualTo(systemUnderTest)
    }

    @ParameterizedTest
    @MethodSource("parts")
    fun apply(part: Part, action: WorkflowAction) {
        assertThat(systemUnderTest.apply(part))
            .isEqualTo(action)
    }

    companion object {

        @JvmStatic
        fun parts() = arrayOf(
            Arguments.of(
                Part(Random.nextInt(), Random.nextInt(), 2005, Random.nextInt()),
                WorkflowAction.Continue(WorkflowName("qkq"))
            ),
            Arguments.of(
                Part(Random.nextInt(), Random.nextInt(), 1, Random.nextInt()),
                WorkflowAction.Continue(WorkflowName("qkq"))
            ),
            Arguments.of(
                Part(Random.nextInt(), 2091, 2006, Random.nextInt()),
                WorkflowAction.Accepted
            ),
            Arguments.of(
                Part(Random.nextInt(), 2091, 4000, Random.nextInt()),
                WorkflowAction.Accepted
            ),
            Arguments.of(
                Part(Random.nextInt(), 4000, 4000, Random.nextInt()),
                WorkflowAction.Accepted
            ),
            Arguments.of(
                Part(Random.nextInt(), 2090, 2006, Random.nextInt()),
                WorkflowAction.Continue(WorkflowName("rfg"))
            ),
            Arguments.of(
                Part(Random.nextInt(), 1, 4000, Random.nextInt()),
                WorkflowAction.Continue(WorkflowName("rfg"))
            )
        )
    }
}
