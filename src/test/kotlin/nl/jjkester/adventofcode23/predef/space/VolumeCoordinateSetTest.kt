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

class VolumeCoordinateSetTest {

    private val volume: Volume = mock()

    private val systemUnderTest = volume.coordinates()

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, Int.MAX_VALUE])
    fun `size delegates to size on volume`(returnValue: Int) {
        volume.stub {
            on { size } doReturn returnValue
        }

        assertThat(systemUnderTest.size)
            .isEqualTo(returnValue)

        verify(volume).size
        verifyNoMoreInteractions(volume)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `isEmpty delegates to isEmpty on volume`(returnValue: Boolean) {
        volume.stub {
            on { isEmpty() } doReturn returnValue
        }

        assertThat(systemUnderTest.isEmpty())
            .isEqualTo(returnValue)

        verify(volume).isEmpty()
        verifyNoMoreInteractions(volume)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `contains delegates to contains on volume`(returnValue: Boolean) {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()
        val coordinate = coordinateOf(x, y, z)

        volume.stub {
            on { contains(any(), any(), any()) } doReturn returnValue
        }

        assertThat(systemUnderTest.contains(coordinate))
            .isEqualTo(returnValue)

        verify(volume).contains(x, y, z)
        verifyNoMoreInteractions(volume)
    }

    @ParameterizedTest
    @MethodSource("coordinatesForContainsAll")
    fun `containsAll combines results from contains on volume`(
        coordinates: Array<Coordinate3D>,
        expectedResult: Boolean
    ) {
        volume.stub {
            on { contains(any(), any(), any()) } doReturn false
            containedCoordinates.forEach { coordinate ->
                on { contains(coordinate.x, coordinate.y, coordinate.z) } doReturn true
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
                verify(volume).contains(coordinate.x, coordinate.y, coordinate.z)
            }
        verifyNoMoreInteractions(volume)
    }

    @Test
    fun `iterator iterates over the coordinates in the volume`() {
        volume.stub {
            on { x } doReturn 0..1
            on { y } doReturn 2..4
            on { z } doReturn 5..5
        }

        assertThat(systemUnderTest.iterator().asSequence())
            .containsExactlyInAnyOrder(
                coordinateOf(0, 2, 5),
                coordinateOf(0, 3, 5),
                coordinateOf(0, 4, 5),
                coordinateOf(1, 2, 5),
                coordinateOf(1, 3, 5),
                coordinateOf(1, 4, 5)
            )
    }

    companion object {
        private val containedCoordinates = arrayOf(
            coordinateOf(1, 1, 1),
            coordinateOf(2, 2, 2),
        )
        private val notContainedCoordinates = arrayOf(
            coordinateOf(3, 3, 3),
            coordinateOf(4, 4, 4)
        )

        @JvmStatic
        fun coordinatesForContainsAll() = arrayOf(
            Arguments.of(containedCoordinates, true),
            Arguments.of(notContainedCoordinates, false),
            Arguments.of(containedCoordinates + notContainedCoordinates, false)
        )
    }
}
