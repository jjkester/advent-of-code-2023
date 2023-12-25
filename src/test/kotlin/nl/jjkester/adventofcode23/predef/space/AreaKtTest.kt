package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.*
import kotlin.random.Random

class AreaKtTest {

    private val area: Area = mock()

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `contains extension function delegates to contains on interface`(returnValue: Boolean) {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val coordinate = coordinateOf(x, y)

        area.stub {
            on { contains(any(), any()) } doReturn returnValue
        }

        assertThat(area.contains(coordinate))
            .isEqualTo(returnValue)

        verify(area).contains(x, y)
        verifyNoMoreInteractions(area)
    }

    @Test
    fun `overlaps extension function is true when both ranges overlap`() {
        area.stub {
            on { x } doReturn 10..15
            on { y } doReturn 20..25
        }

        val other = mock<Area> {
            on { x } doReturn 9..14
            on { y } doReturn 21..26
        }

        assertThat(area.overlaps(other))
            .isTrue()
    }

    @Test
    fun `overlaps extension function is false when only x range overlaps`() {
        area.stub {
            on { x } doReturn 10..15
            on { y } doReturn 20..25
        }

        val other = mock<Area> {
            on { x } doReturn 9..14
            on { y } doReturn 26..31
        }

        assertThat(area.overlaps(other))
            .isTrue()
    }

    @Test
    fun `overlaps extension function is false when only y range overlaps`() {
        area.stub {
            on { x } doReturn 10..15
            on { y } doReturn 20..25
        }

        val other = mock<Area> {
            on { x } doReturn 4..9
            on { y } doReturn 21..26
        }

        assertThat(area.overlaps(other))
            .isTrue()
    }

    @Test
    fun `overlaps extension function is false when no ranges overlap`() {
        area.stub {
            on { x } doReturn 10..15
            on { y } doReturn 20..25
        }

        val other = mock<Area> {
            on { x } doReturn 4..9
            on { y } doReturn 26..31
        }

        assertThat(area.overlaps(other))
            .isTrue()
    }
}
