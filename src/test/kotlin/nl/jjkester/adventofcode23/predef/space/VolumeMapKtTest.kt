package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.random.Random

class VolumeMapKtTest {

    private val volumeMap: VolumeMap<Int> = mock()

    @Test
    fun `get extension function delegates to get on implementation`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()
        val coordinate = coordinateOf(x, y, z)
        val returnValue = Random.nextInt()

        volumeMap.stub {
            on { get(any(), any(), any()) } doReturn returnValue
        }

        assertThat(volumeMap[coordinate])
            .isEqualTo(returnValue)

        verify(volumeMap)[x, y, z]
        verifyNoMoreInteractions(volumeMap)
    }

    @Test
    fun `when the coordinate is contained in the volume, then getOrNull with distinct integers returns the value`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()
        val returnValue = Random.nextInt()

        volumeMap.stub {
            on { contains(any(), any(), any()) } doReturn true
            on { get(any(), any(), any()) } doReturn returnValue
        }

        assertThat(volumeMap.getOrNull(x, y, z))
            .isEqualTo(returnValue)

        verify(volumeMap).contains(x, y, z)
        verify(volumeMap)[x, y, z]
        verifyNoMoreInteractions(volumeMap)
    }

    @Test
    fun `when the coordinate is not contained in the volume, then getOrNull with distinct integers returns null`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()

        volumeMap.stub {
            on { contains(any(), any(), any()) } doReturn false
        }

        assertThat(volumeMap.getOrNull(x, y, z))
            .isNull()

        verify(volumeMap).contains(x, y, z)
        verifyNoMoreInteractions(volumeMap)
    }

    @Test
    fun `when the coordinate is contained in the volume, then getOrNull with coordinate returns the value`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()
        val coordinate = coordinateOf(x, y, z)
        val returnValue = Random.nextInt()

        volumeMap.stub {
            on { contains(any(), any(), any()) } doReturn true
            on { get(any(), any(), any()) } doReturn returnValue
        }

        assertThat(volumeMap.getOrNull(coordinate))
            .isEqualTo(returnValue)

        verify(volumeMap).contains(x, y, z)
        verify(volumeMap)[x, y, z]
        verifyNoMoreInteractions(volumeMap)
    }

    @Test
    fun `when the coordinate is not contained in the volume, then getOrNull with coordinate returns null`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()
        val coordinate = coordinateOf(x, y, z)

        volumeMap.stub {
            on { contains(any(), any(), any()) } doReturn false
        }

        assertThat(volumeMap.getOrNull(coordinate))
            .isNull()

        verify(volumeMap).contains(x, y, z)
        verifyNoMoreInteractions(volumeMap)
    }
}
