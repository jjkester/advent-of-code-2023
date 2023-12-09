package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.*
import kotlin.random.Random

class AreaMapValuesTest {

    private val area: AreaMap<Int> = mock()

    private val systemUnderTest = area.values()

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, Int.MAX_VALUE])
    fun `size delegates to size on area`(returnValue: Int) {
        area.stub {
            on { size } doReturn returnValue
        }

        assertThat(systemUnderTest.size)
            .isEqualTo(returnValue)

        verify(area).size
        verifyNoMoreInteractions(area)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `isEmpty delegates to isEmpty on area`(returnValue: Boolean) {
        area.stub {
            on { isEmpty() } doReturn returnValue
        }

        assertThat(systemUnderTest.isEmpty())
            .isEqualTo(returnValue)

        verify(area).isEmpty()
        verifyNoMoreInteractions(area)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `contains delegates to contains on area`(returnValue: Boolean) {
        val value = Random.nextInt()

        area.stub {
            on { contains(any()) } doReturn returnValue
        }

        assertThat(systemUnderTest.contains(value))
            .isEqualTo(returnValue)

        verify(area).contains(value)
        verifyNoMoreInteractions(area)
    }

    @ParameterizedTest
    @MethodSource("valuesForContainsAll")
    fun `containsAll combines results from contains on area`(
        values: Array<Int>,
        expectedResult: Boolean
    ) {
        area.stub {
            on { contains(any()) } doReturn false
            containedValues.forEach { value ->
                on { contains(value) } doReturn true
            }
        }

        assertThat(systemUnderTest.containsAll(values.toList()))
            .isEqualTo(expectedResult)

        values
            .run {
                val lastCalledIndex = lastOrNull { it in containedValues }?.let { indexOf(it) + 1 }
                take((lastCalledIndex ?: 0) + 1)
            }
            .forEach { value ->
                verify(area).contains(value)
            }
        verifyNoMoreInteractions(area)
    }

    @Test
    fun `iterator iterates over the coordinates in the area`() {
        val values = generateSequence(1, Int::inc).iterator()

        area.stub {
            on { x } doReturn 0..1
            on { y } doReturn 2..4
            on { get(any(), any()) } doAnswer { values.next() }
        }

        assertThat(systemUnderTest.iterator().asSequence())
            .containsExactly(1, 2, 3, 4, 5, 6)
    }

    companion object {
        private val containedValues = arrayOf(1, 2)
        private val notContainedValues = arrayOf(3, 4)

        @JvmStatic
        fun valuesForContainsAll() = arrayOf(
            Arguments.of(containedValues, true),
            Arguments.of(notContainedValues, false),
            Arguments.of(containedValues + notContainedValues, false)
        )
    }
}
