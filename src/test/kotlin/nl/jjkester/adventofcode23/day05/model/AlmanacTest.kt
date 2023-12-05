package nl.jjkester.adventofcode23.day05.model

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class AlmanacTest {

    @Test
    fun parse() {
        assertThat(Almanac.parse(testInput)).all {
            prop(Almanac::seeds).containsExactlyInAnyOrder(
                Seed(79),
                Seed(14),
                Seed(55),
                Seed(13)
            )
            prop(Almanac::seedRanges).containsExactlyInAnyOrder(
                Seed(79)..Seed(92),
                Seed(55)..Seed(67)
            )
            prop(Almanac::seedToSoil).prop("ranges") { it.ranges }.containsExactly(
                50L..97L to 52L..99L,
                98L..99L to 50L..51L
            )
        }
    }

    @ParameterizedTest
    @MethodSource("seedsForFindLocation")
    fun findLocation(seed: Long, location: Long) {
        assertThat(parsedTestInput.findLocation(Seed(seed)))
            .isEqualTo(Location(location))
    }

    @Test
    fun findIndividualLocations() {
        assertThat(parsedTestInput.findIndividualLocations())
            .extracting { it.value }
            .containsExactlyInAnyOrder(
                *seedLocations.values.toTypedArray()
            )
    }

    companion object {
        private val testInput = """
            seeds: 79 14 55 13

            seed-to-soil map:
            50 98 2
            52 50 48

            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15

            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4

            water-to-light map:
            88 18 7
            18 25 70

            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13

            temperature-to-humidity map:
            0 69 1
            1 0 69

            humidity-to-location map:
            60 56 37
            56 93 4
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            Almanac.parse(testInput)
        }

        private val seedLocations = mapOf(
            79L to 82L,
            14L to 43L,
            55L to 86L,
            13L to 35L
        )

        @JvmStatic
        private fun seedsForFindLocation() = seedLocations
            .map { (first, second) -> Arguments.of(first, second) }
            .toTypedArray()
    }
}
