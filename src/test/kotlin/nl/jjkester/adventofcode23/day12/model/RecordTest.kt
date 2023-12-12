package nl.jjkester.adventofcode23.day12.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class RecordTest {

    @ParameterizedTest
    @MethodSource("possibilities")
    fun possibilities(record: Record, expectedSize: Int) {
        val possibilities = record.possibilities().toList()

        possibilities.forEach { possibility ->
            println(
                possibility
                    .map {
                        when (it) {
                            Record.Value.Good -> '.'
                            Record.Value.Bad -> '#'
                        }
                    }
                    .joinToString("")
            )
        }

        assertThat(possibilities.count())
            .isEqualTo(expectedSize)
    }

    @ParameterizedTest
    @MethodSource("possibilitiesUnfolded")
    fun numberOfPossibilities(record: Record, expectedSize: Int) {
        assertThat(record.numberOfPossibilities())
            .isEqualTo(expectedSize)
    }

    @Test
    fun unfold() {
        val record = Record.parse("?###???????? 3,2,1")
        assertThat(record.unfold(2))
            .isEqualTo(Record.parse("?###??????????###???????? 3,2,1,3,2,1"))
    }

    companion object {

        @JvmStatic
        fun possibilities() = arrayOf(
            Arguments.of(Record.parse("???.### 1,1,3"), 1),
            Arguments.of(Record.parse(".??..??...?##. 1,1,3"), 4),
            Arguments.of(Record.parse("?#?#?#?#?#?#?#? 1,3,1,6"), 1),
            Arguments.of(Record.parse("????.#...#... 4,1,1"), 1),
            Arguments.of(Record.parse("????.######..#####. 1,6,5"), 4),
            Arguments.of(Record.parse("?###???????? 3,2,1"), 10)
        )

        @JvmStatic
        fun possibilitiesUnfolded() = possibilities() + arrayOf(
            Arguments.of(Record.parse("???.### 1,1,3").unfold(5), 1),
            Arguments.of(Record.parse(".??..??...?##. 1,1,3").unfold(5), 16384),
            Arguments.of(Record.parse("?#?#?#?#?#?#?#? 1,3,1,6").unfold(5), 1),
            Arguments.of(Record.parse("????.#...#... 4,1,1").unfold(5), 16),
            Arguments.of(Record.parse("????.######..#####. 1,6,5").unfold(5), 2500),
            Arguments.of(Record.parse("?###???????? 3,2,1").unfold(5), 506250)
        )
    }
}
