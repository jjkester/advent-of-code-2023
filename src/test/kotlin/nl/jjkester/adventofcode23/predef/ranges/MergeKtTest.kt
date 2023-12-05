package nl.jjkester.adventofcode23.predef.ranges

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import org.junit.jupiter.api.Test

class MergeKtTest {

    @Test
    fun `merge an IntRange iterable`() {
        val ranges = setOf(
            0..10,
            5..25,
            26..30,
            32..32,
            33..35,
            40..40,
            IntRange.EMPTY
        )

        assertThat(ranges.mergedInts())
            .containsExactly(
                0..30,
                32..35,
                40..40
            )
    }

    @Test
    fun `merge an empty IntRange iterable`() {
        assertThat(emptyList<IntRange>().mergedInts())
            .isEmpty()
    }

    @Test
    fun `merge a singleton IntRange iterable`() {
        assertThat(listOf(0..1).mergedInts())
            .containsExactly(0..1)
    }

    @Test
    fun `merge a singleton IntRange iterable with an empty range`() {
        assertThat(listOf(IntRange.EMPTY).mergedInts())
            .isEmpty()
    }

    @Test
    fun `merge a LongRange iterable`() {
        val ranges = setOf(
            0L..10L,
            5L..25L,
            26L..30L,
            32L..32L,
            33L..35L,
            40L..40L,
            LongRange.EMPTY
        )

        assertThat(ranges.mergedLongs())
            .containsExactly(
                0L..30L,
                32L..35L,
                40L..40L
            )
    }

    @Test
    fun `merge an empty LongRange iterable`() {
        assertThat(emptyList<LongRange>().mergedLongs())
            .isEmpty()
    }

    @Test
    fun `merge a singleton LongRange iterable`() {
        assertThat(listOf(0L..1L).mergedLongs())
            .containsExactly(0L..1L)
    }

    @Test
    fun `merge a singleton LongRange iterable with an empty range`() {
        assertThat(listOf(LongRange.EMPTY).mergedLongs())
            .isEmpty()
    }

    @Test
    fun `merge an IntRange sequence`() {
        val ranges = sequenceOf(
            0..10,
            5..25,
            26..30,
            32..32,
            33..35,
            40..40,
            IntRange.EMPTY
        )

        assertThat(ranges.mergedInts())
            .containsExactly(
                0..30,
                32..35,
                40..40
            )
    }

    @Test
    fun `merge an empty IntRange sequence`() {
        assertThat(emptySequence<IntRange>().mergedInts())
            .isEmpty()
    }

    @Test
    fun `merge a singleton IntRange sequence`() {
        assertThat(sequenceOf(0..1).mergedInts())
            .containsExactly(0..1)
    }

    @Test
    fun `merge a singleton IntRange sequence with an empty range`() {
        assertThat(sequenceOf(IntRange.EMPTY).mergedInts())
            .isEmpty()
    }

    @Test
    fun `merge a LongRange sequence`() {
        val ranges = sequenceOf(
            0L..10L,
            5L..25L,
            26L..30L,
            32L..32L,
            33L..35L,
            40L..40L,
            LongRange.EMPTY
        )

        assertThat(ranges.mergedLongs())
            .containsExactly(
                0L..30L,
                32L..35L,
                40L..40L
            )
    }

    @Test
    fun `merge an empty LongRange sequence`() {
        assertThat(emptySequence<LongRange>().mergedLongs())
            .isEmpty()
    }

    @Test
    fun `merge a singleton LongRange sequence`() {
        assertThat(sequenceOf(0L..1L).mergedLongs())
            .containsExactly(0L..1L)
    }

    @Test
    fun `merge a singleton LongRange sequence with an empty range`() {
        assertThat(sequenceOf(LongRange.EMPTY).mergedLongs())
            .isEmpty()
    }
}
