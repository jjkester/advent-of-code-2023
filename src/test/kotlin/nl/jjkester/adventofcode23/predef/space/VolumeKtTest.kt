package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.*
import kotlin.random.Random

class VolumeKtTest {

    private val volume: Volume = mock()

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `contains extension function delegates to contains on interface`(returnValue: Boolean) {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()
        val coordinate = coordinateOf(x, y, z)

        volume.stub {
            on { contains(any(), any(), any()) } doReturn returnValue
        }

        assertThat(volume.contains(coordinate))
            .isEqualTo(returnValue)

        verify(volume).contains(x, y, z)
        verifyNoMoreInteractions(volume)
    }
}
