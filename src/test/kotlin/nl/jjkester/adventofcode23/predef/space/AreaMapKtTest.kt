package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.containsExactly
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

    @Test
    fun `when retrieving a column then the column contains all elements in order`() {
        val x = Random.nextInt()

        areaMap.stub {
            on { y } doReturn 1..3
            on { get(any(), any()) } doAnswer { it.getArgument(1) }
        }

        assertThat(areaMap.getColumn(x))
            .containsExactly(1, 2, 3)

        verify(areaMap).y
        verify(areaMap).get(x, 1)
        verify(areaMap).get(x, 2)
        verify(areaMap).get(x, 3)
        verifyNoMoreInteractions(areaMap)
    }

    @Test
    fun `when retrieving a row then the row contains all elements in order`() {
        val y = Random.nextInt()

        areaMap.stub {
            on { x } doReturn 1..3
            on { get(any(), any()) } doAnswer { it.getArgument(0) }
        }

        assertThat(areaMap.getRow(y))
            .containsExactly(1, 2, 3)

        verify(areaMap).x
        verify(areaMap).get(1, y)
        verify(areaMap).get(2, y)
        verify(areaMap).get(3, y)
        verifyNoMoreInteractions(areaMap)
    }
}
