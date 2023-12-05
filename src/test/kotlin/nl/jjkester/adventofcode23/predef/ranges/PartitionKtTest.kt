package nl.jjkester.adventofcode23.predef.ranges

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.single
import nl.jjkester.adventofcode23.assertions.first
import nl.jjkester.adventofcode23.assertions.second
import org.junit.jupiter.api.Test

class PartitionKtTest {

    @Test
    fun `partition an IntRange`() {
        val range = 0..100
        val overlapsWith = setOf(
            -10..-2,
            -10..5,
            5..6,
            30..80,
            40..60,
            81..85,
            99..99,
            101..1000,
            IntRange.EMPTY
        )

        assertThat(range.partition(overlapsWith)).all {
            first().containsExactly(
                0..5,
                6..6,
                30..80,
                81..85,
                99..99
            )
            second().containsExactly(
                7..29,
                86..98,
                100..100
            )
        }
    }

    @Test
    fun `partition an empty IntRange`() {
        assertThat(IntRange.EMPTY.partition(listOf(0..1))).all {
            first().isEmpty()
            second().isEmpty()
        }
    }

    @Test
    fun `partition an IntRange by an empty iterable`() {
        assertThat((0..1).partition(emptyList())).all {
            first().isEmpty()
            second().single().isEqualTo(0..1)
        }
    }

    @Test
    fun `partition an LongRange`() {
        val range = 0L..100L
        val overlapsWith = setOf(
            -10L..-2L,
            -10L..5L,
            5L..6L,
            30L..80L,
            40L..60L,
            81L..85L,
            99L..99L,
            101L..1000L,
            LongRange.EMPTY
        )

        assertThat(range.partition(overlapsWith)).all {
            first().containsExactly(
                0L..5L,
                6L..6L,
                30L..80L,
                81L..85L,
                99L..99L
            )
            second().containsExactly(
                7L..29L,
                86L..98L,
                100L..100L
            )
        }
    }

    @Test
    fun `partition an empty LongRange`() {
        assertThat(LongRange.EMPTY.partition(listOf(0L..1L))).all {
            first().isEmpty()
            second().isEmpty()
        }
    }

    @Test
    fun `partition an LongRange by an empty iterable`() {
        assertThat((0L..1L).partition(emptyList())).all {
            first().isEmpty()
            second().single().isEqualTo(0L..1L)
        }
    }
}
