package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.random.Random

class AreaMapKtTest {

    private val areaMap: AreaMap<Int> = mock()

    @Test
    fun `get extension function delegates to get on implementation`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val coordinate = coordinateOf(x, y)
        val returnValue = Random.nextInt()

        areaMap.stub {
            on { get(any(), any()) } doReturn returnValue
        }

        assertThat(areaMap[coordinate])
            .isEqualTo(returnValue)

        verify(areaMap)[x, y]
        verifyNoMoreInteractions(areaMap)
    }

    @Test
    fun `when the coordinate is contained in the area, then getOrNull with distinct integers returns the value`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val returnValue = Random.nextInt()

        areaMap.stub {
            on { contains(any(), any()) } doReturn true
            on { get(any(), any()) } doReturn returnValue
        }

        assertThat(areaMap.getOrNull(x, y))
            .isEqualTo(returnValue)

        verify(areaMap).contains(x, y)
        verify(areaMap)[x, y]
        verifyNoMoreInteractions(areaMap)
    }

    @Test
    fun `when the coordinate is not contained in the area, then getOrNull with distinct integers returns null`() {
        val x = Random.nextInt()
        val y = Random.nextInt()

        areaMap.stub {
            on { contains(any(), any()) } doReturn false
        }

        assertThat(areaMap.getOrNull(x, y))
            .isNull()

        verify(areaMap).contains(x, y)
        verifyNoMoreInteractions(areaMap)
    }

    @Test
    fun `when the coordinate is contained in the area, then getOrNull with coordinate returns the value`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val coordinate = coordinateOf(x, y)
        val returnValue = Random.nextInt()

        areaMap.stub {
            on { contains(any(), any()) } doReturn true
            on { get(any(), any()) } doReturn returnValue
        }

        assertThat(areaMap.getOrNull(coordinate))
            .isEqualTo(returnValue)

        verify(areaMap).contains(x, y)
        verify(areaMap)[x, y]
        verifyNoMoreInteractions(areaMap)
    }

    @Test
    fun `when the coordinate is not contained in the area, then getOrNull with coordinate returns null`() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val coordinate = coordinateOf(x, y)

        areaMap.stub {
            on { contains(any(), any()) } doReturn false
        }

        assertThat(areaMap.getOrNull(coordinate))
            .isNull()

        verify(areaMap).contains(x, y)
        verifyNoMoreInteractions(areaMap)
    }
}
