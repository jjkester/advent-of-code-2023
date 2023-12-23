package nl.jjkester.adventofcode23.day19.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.random.Random

class WorkflowRuleTest {

    @ParameterizedTest
    @MethodSource("inputs")
    fun parse(input: String, expected: WorkflowRule) {
        assertThat(WorkflowRule.parse(input))
            .isEqualTo(expected)
    }

    @Nested
    inner class LessThan {

        private val action = WorkflowAction.Continue(WorkflowName("foo"))

        private val systemUnderTest = WorkflowRule.LessThan(Category.S, 100, action)

        @ParameterizedTest
        @ValueSource(ints = [1, 99])
        fun `apply applicable`(s: Int) {
            val part = Part(Random.nextInt(), Random.nextInt(), Random.nextInt(), s)

            assertThat(systemUnderTest.apply(part))
                .isSameAs(action)
        }

        @ParameterizedTest
        @ValueSource(ints = [100, 4000])
        fun `apply not applicable`(s: Int) {
            val part = Part(Random.nextInt(), Random.nextInt(), Random.nextInt(), s)

            assertThat(systemUnderTest.apply(part))
                .isNull()
        }
    }

    @Nested
    inner class GreaterThan {

        private val action = WorkflowAction.Continue(WorkflowName("foo"))

        private val systemUnderTest = WorkflowRule.GreaterThan(Category.S, 100, action)

        @ParameterizedTest
        @ValueSource(ints = [101, 4000])
        fun `apply applicable`(s: Int) {
            val part = Part(Random.nextInt(), Random.nextInt(), Random.nextInt(), s)

            assertThat(systemUnderTest.apply(part))
                .isSameAs(action)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 100])
        fun `apply not applicable`(s: Int) {
            val part = Part(Random.nextInt(), Random.nextInt(), Random.nextInt(), s)

            assertThat(systemUnderTest.apply(part))
                .isNull()
        }
    }

    @Nested
    inner class All {

        private val action = WorkflowAction.Continue(WorkflowName("foo"))

        private val systemUnderTest = WorkflowRule.All(action)

        @ParameterizedTest
        @ValueSource(ints = [1, 99, 100, 101, 4000])
        fun apply(s: Int) {
            val part = Part(Random.nextInt(), Random.nextInt(), Random.nextInt(), s)

            assertThat(systemUnderTest.apply(part))
                .isSameAs(action)
        }
    }

    companion object {

        @JvmStatic
        fun inputs() = arrayOf(
            Arguments.of("foo", WorkflowRule.All(WorkflowAction.Continue(WorkflowName("foo")))),
            Arguments.of("bar", WorkflowRule.All(WorkflowAction.Continue(WorkflowName("bar")))),
            Arguments.of("A", WorkflowRule.All(WorkflowAction.Accepted)),
            Arguments.of(
                "a < 42 : bar",
                WorkflowRule.LessThan(Category.A, 42, WorkflowAction.Continue(WorkflowName("bar")))
            ),
            Arguments.of(
                "x>2:R",
                WorkflowRule.GreaterThan(Category.X, 2, WorkflowAction.Rejected)
            )
        )
    }
}
