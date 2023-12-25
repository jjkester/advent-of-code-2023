package nl.jjkester.adventofcode23.day22.model

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import nl.jjkester.adventofcode23.predef.space.by
import nl.jjkester.adventofcode23.predef.space.volumeOf
import org.junit.jupiter.api.Test

class SnapshotTest {

    @Test
    fun parse() {
        assertThat(Snapshot.parse(testInput))
            .transform { it.bricks }
            .containsExactlyInAnyOrder(
                Brick("A", volumeOf(1 by 0 by 1, 1 by 2 by 1)),
                Brick("B", volumeOf(0 by 0 by 2, 2 by 0 by 2)),
                Brick("C", volumeOf(0 by 2 by 3, 2 by 2 by 3)),
                Brick("D", volumeOf(0 by 0 by 4, 0 by 2 by 4)),
                Brick("E", volumeOf(2 by 0 by 5, 2 by 2 by 5)),
                Brick("F", volumeOf(0 by 1 by 6, 2 by 1 by 6)),
                Brick("G", volumeOf(1 by 1 by 8, 1 by 1 by 9))
            )
    }

    @Test
    fun toGround() {
        assertThat(parsedTestInput.toGround())
            .transform { it.bricks }
            .containsExactlyInAnyOrder(
                Brick("A", volumeOf(1 by 0 by 1, 1 by 2 by 1)),
                Brick("B", volumeOf(0 by 0 by 2, 2 by 0 by 2)),
                Brick("C", volumeOf(0 by 2 by 2, 2 by 2 by 2)),
                Brick("D", volumeOf(0 by 0 by 3, 0 by 2 by 3)),
                Brick("E", volumeOf(2 by 0 by 3, 2 by 2 by 3)),
                Brick("F", volumeOf(0 by 1 by 4, 2 by 1 by 4)),
                Brick("G", volumeOf(1 by 1 by 5, 1 by 1 by 6))
            )
    }

    @Test
    fun disintegrationCandidates() {
        assertThat(parsedTestInput.toGround().disintegrationCandidates())
            .extracting { it.first.id to it.second }
            .containsExactlyInAnyOrder(
                "A" to 6,
                "B" to 0,
                "C" to 0,
                "D" to 0,
                "E" to 0,
                "F" to 1,
                "G" to 0
            )
    }

    companion object {
        private val testInput = """
            1,0,1~1,2,1
            0,0,2~2,0,2
            0,2,3~2,2,3
            0,0,4~0,2,4
            2,0,5~2,2,5
            0,1,6~2,1,6
            1,1,8~1,1,9
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Snapshot.parse(testInput)
        }
    }
}
