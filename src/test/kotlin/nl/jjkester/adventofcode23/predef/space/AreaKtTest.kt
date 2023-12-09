package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
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
        val coordinate = D2Coordinate(x, y)

        area.stub {
            on { contains(any(), any()) } doReturn returnValue
        }

        assertThat(area.contains(coordinate))
            .isEqualTo(returnValue)

        verify(area).contains(x, y)
        verifyNoMoreInteractions(area)
    }
}
