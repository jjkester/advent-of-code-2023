package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.*
import kotlin.random.Random

class AreaCoordinateSetTest {

    private val area: Area = mock()

    private val systemUnderTest = area.coordinates()

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
        val x = Random.nextInt()
        val y = Random.nextInt()
        val coordinate = coordinateOf(x, y)

        area.stub {
            on { contains(any(), any()) } doReturn returnValue
        }

        assertThat(systemUnderTest.contains(coordinate))
            .isEqualTo(returnValue)

        verify(area).contains(x, y)
        verifyNoMoreInteractions(area)
    }

    @ParameterizedTest
    @MethodSource("coordinatesForContainsAll")
    fun `containsAll combines results from contains on area`(
        coordinates: Array<Coordinate2D>,
        expectedResult: Boolean
    ) {
        area.stub {
            on { contains(any(), any()) } doReturn false
            containedCoordinates.forEach { coordinate ->
                on { contains(coordinate.x, coordinate.y) } doReturn true
            }
        }

        assertThat(systemUnderTest.containsAll(coordinates.toList()))
            .isEqualTo(expectedResult)

        coordinates
            .run {
                val lastCalledIndex = lastOrNull { it in containedCoordinates }?.let { indexOf(it) + 1 }
                take((lastCalledIndex ?: 0) + 1)
            }
            .forEach { coordinate ->
                verify(area).contains(coordinate.x, coordinate.y)
            }
        verifyNoMoreInteractions(area)
    }

    @Test
    fun `iterator iterates over the coordinates in the area`() {
        area.stub {
            on { x } doReturn 0..1
            on { y } doReturn 2..4
        }

        assertThat(systemUnderTest.iterator().asSequence())
            .containsExactlyInAnyOrder(
                coordinateOf(0, 2),
                coordinateOf(0, 3),
                coordinateOf(0, 4),
                coordinateOf(1, 2),
                coordinateOf(1, 3),
                coordinateOf(1, 4)
            )
    }

    companion object {
        private val containedCoordinates = arrayOf(
            coordinateOf(1, 1),
            coordinateOf(2, 2),
        )
        private val notContainedCoordinates = arrayOf(
            coordinateOf(3, 3),
            coordinateOf(4, 4)
        )

        @JvmStatic
        fun coordinatesForContainsAll() = arrayOf(
            Arguments.of(containedCoordinates, true),
            Arguments.of(notContainedCoordinates, false),
            Arguments.of(containedCoordinates + notContainedCoordinates, false)
        )
    }
}
