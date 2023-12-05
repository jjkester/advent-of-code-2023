package nl.jjkester.adventofcode23.day05.model

import nl.jjkester.adventofcode23.predef.sections

/**
 * An almanac with available seeds and instructions on how and where to plant them.
 *
 * @property seeds Set of seeds to start with.
 * @property seedRanges Set of ranges representing the seeds to start with.
 * @property seedToSoil Instructions on which seeds should be planted in which soil type.
 * @property soilToFertilizer Instructions on which soil requires which fertilizer.
 * @property fertilizerToWater Instructions on which fertilizer requires which amount of water.
 * @property waterToLight Instructions on which amount of water requires which amount of light.
 * @property lightToTemperature Instructions on which amount of light requires which temperature.
 * @property temperatureToHumidity Instructions on which temperature requires which humidity.
 * @property humidityToLocation Instructions on which humidity requires which location.
 */
data class Almanac(
    val seeds: Set<Seed>,
    val seedRanges: Set<ClosedRange<Seed>>,
    val seedToSoil: AlmanacStep<Seed, Soil>,
    val soilToFertilizer: AlmanacStep<Soil, Fertilizer>,
    val fertilizerToWater: AlmanacStep<Fertilizer, Water>,
    val waterToLight: AlmanacStep<Water, Light>,
    val lightToTemperature: AlmanacStep<Light, Temperature>,
    val temperatureToHumidity: AlmanacStep<Temperature, Humidity>,
    val humidityToLocation: AlmanacStep<Humidity, Location>
) {

    /**
     * Determines and returns the optimal locations for the individual [seeds].
     */
    fun findIndividualLocations(): Sequence<Location> = seeds.asSequence().map(::findLocation)

    /**
     * Determines and returns the optimal locations for the [ranges of seeds][seedRanges].
     */
    fun findRangeLocations(): Sequence<ClosedRange<Location>> = seedRanges.asSequence().flatMap { findLocations(it) }

    /**
     * Determines and returns the ideal location for the [seed].
     *
     * @param seed Seed to find the ideal location for.
     * @return The ideal location for the [seed].
     */
    fun findLocation(seed: Seed): Location = seed
        .let(seedToSoil::get)
        .let(soilToFertilizer::get)
        .let(fertilizerToWater::get)
        .let(waterToLight::get)
        .let(lightToTemperature::get)
        .let(temperatureToHumidity::get)
        .let(humidityToLocation::get)

    /**
     * Determines and returns the ideal locations for the [seeds][seedRange].
     *
     * @param seedRange Seeds to find the ideal location for.
     * @return The ideal locations for the [seeds][seedRange] as ranges.
     */
    fun findLocations(seedRange: ClosedRange<Seed>): Set<ClosedRange<Location>> = seedRange
        .let(seedToSoil::map)
        .let(soilToFertilizer::mapAll)
        .let(fertilizerToWater::mapAll)
        .let(waterToLight::mapAll)
        .let(lightToTemperature::mapAll)
        .let(temperatureToHumidity::mapAll)
        .let(humidityToLocation::mapAll)

    companion object {

        /**
         * Parses the [input] string to an [Almanac] and returns it.
         *
         * @param input The input string to parse.
         * @return The [Almanac] represented by the [input] string.
         * @throws IllegalArgumentException The [input] string is not a valid almanac.
         */
        fun parse(input: String): Almanac {
            val sections = input.sections()

            val seeds = sections.single { it.startsWith("seeds: ") }
                .split(Regex("\\s+"))
                .mapNotNull { it.toLongOrNull() }
                .map(::Seed)
                .also { require(it.size % 2 == 0) { "Number of seeds must be even" } }

            val seedRanges = seeds
                .windowed(2, step = 2)
                .map { (first, second) -> first..Seed(first.value + second.value - 1) }

            return Almanac(
                seeds = seeds.toSet(),
                seedRanges = seedRanges.toSet(),
                seedToSoil = sections.extractRangeLookups("seed-to-soil", Seed::value, ::Soil),
                soilToFertilizer = sections.extractRangeLookups("soil-to-fertilizer", Soil::value, ::Fertilizer),
                fertilizerToWater = sections.extractRangeLookups("fertilizer-to-water", Fertilizer::value, ::Water),
                waterToLight = sections.extractRangeLookups("water-to-light", Water::value, ::Light),
                lightToTemperature = sections.extractRangeLookups("light-to-temperature", Light::value, ::Temperature),
                temperatureToHumidity = sections.extractRangeLookups(
                    "temperature-to-humidity",
                    Temperature::value,
                    ::Humidity
                ),
                humidityToLocation = sections.extractRangeLookups("humidity-to-location", Humidity::value, ::Location)
            )
        }

        private fun <K : Comparable<K>, V : Comparable<V>> List<String>.extractRangeLookups(
            name: String,
            extractKey: (K) -> Long,
            transformValue: (Long) -> V
        ) = single { it.startsWith("$name map:") }
            .lines()
            .drop(1)
            .map { line ->
                line.split(Regex("\\s+")).mapNotNull { it.toLongOrNull() }
                    .also { require(it.size == 3) { "Each map entry must have a length of three" } }
                    .let { (firstValue, firstKey, size) -> firstKey..<(firstKey + size) to firstValue..<(firstValue + size) }
            }
            .let { AlmanacStep(extractKey, transformValue, it) }

        private operator fun ClosedRange<Seed>.iterator() =
            (start.value..endInclusive.value).asSequence().map(::Seed).iterator()
    }
}
