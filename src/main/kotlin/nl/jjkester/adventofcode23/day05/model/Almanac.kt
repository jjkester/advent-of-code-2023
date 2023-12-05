package nl.jjkester.adventofcode23.day05.model

import nl.jjkester.adventofcode23.predef.coerceIn
import nl.jjkester.adventofcode23.predef.sections
import nl.jjkester.adventofcode23.predef.size

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
    private val seedToSoil: RangeLookup<Seed, Soil>,
    private val soilToFertilizer: RangeLookup<Soil, Fertilizer>,
    private val fertilizerToWater: RangeLookup<Fertilizer, Water>,
    private val waterToLight: RangeLookup<Water, Light>,
    private val lightToTemperature: RangeLookup<Light, Temperature>,
    private val temperatureToHumidity: RangeLookup<Temperature, Humidity>,
    private val humidityToLocation: RangeLookup<Humidity, Location>
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
                .map { (first, second) -> first..Seed(first.value + second.value) }

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
                    .let { (firstValue, firstKey, size) -> firstKey..(firstKey + size) to firstValue..(firstValue + size) }
            }
            .let { RangeLookup(extractKey, transformValue, it) }
    }
}

/**
 * A type of seed.
 *
 * @property value The value representing the type of seed.
 */
@JvmInline
value class Seed(val value: Long) : Comparable<Seed> {

    override fun compareTo(other: Seed): Int = value.compareTo(other.value)
}

/**
 * A type of soil.
 *
 * @property value The value representing the type of soil.
 */
@JvmInline
value class Soil(val value: Long) : Comparable<Soil> {

    override fun compareTo(other: Soil): Int = value.compareTo(other.value)
}

/**
 * A type of fertilizer.
 *
 * @property value The value representing the type of fertilizer.
 */
@JvmInline
value class Fertilizer(val value: Long) : Comparable<Fertilizer> {

    override fun compareTo(other: Fertilizer): Int = value.compareTo(other.value)
}

/**
 * An amount of water.
 *
 * @property value The value representing the amount of water.
 */
@JvmInline
value class Water(val value: Long) : Comparable<Water> {

    override fun compareTo(other: Water): Int = value.compareTo(other.value)
}

/**
 * An amount of light.
 *
 * @property value The value representing the amount of light.
 */
@JvmInline
value class Light(val value: Long) : Comparable<Light> {

    override fun compareTo(other: Light): Int = value.compareTo(other.value)
}

/**
 * A temperature.
 *
 * @property value The value representing the temperature.
 */
@JvmInline
value class Temperature(val value: Long) : Comparable<Temperature> {

    override fun compareTo(other: Temperature): Int = value.compareTo(other.value)
}

/**
 * An amount of humidity.
 *
 * @property value The value representing the amount of humidity.
 */
@JvmInline
value class Humidity(val value: Long) : Comparable<Humidity> {

    override fun compareTo(other: Humidity): Int = value.compareTo(other.value)
}

/**
 * A type of location.
 *
 * @property value The value representing the type of location.
 */
@JvmInline
value class Location(val value: Long) : Comparable<Location> {

    override fun compareTo(other: Location): Int = value.compareTo(other.value)
}

// TODO: Clean up and extract generic code
class RangeLookup<K : Comparable<K>, V : Comparable<V>>(
    private val extractKey: (K) -> Long,
    private val transformValue: (Long) -> V,
    ranges: List<Pair<LongRange, LongRange>>
) {

    private val ranges = ranges.sortedBy { it.first.first }

    init {
        require(ranges.all { it.first.size == it.second.size })
    }

    operator fun get(key: K): V = get(extractKey(key))

    private fun get(keyValue: Long) = (ranges.firstOrNull { keyValue in it.first }
        ?.let { transformValue((keyValue - it.first.first) + it.second.first) }
        ?: transformValue(keyValue))

    fun map(keys: ClosedRange<K>): Set<ClosedRange<V>> {
        val keyRange = extractKey(keys.start)..extractKey(keys.endInclusive)

        val mapped = ranges.mapNotNull { range ->
            range.first.coerceIn(keyRange)
                .takeIf { !it.isEmpty() }
                ?.let { coercedKeys ->
                    val firstValue = range.second.first + (coercedKeys.first - range.first.first)
                    val lastValue = range.second.last - (range.first.last - coercedKeys.last)
                    coercedKeys to firstValue..lastValue
                }
        }

        val unmapped = sequence {
            var i = keyRange.first

            mapped.forEach { range ->
                if (i < range.first.first) {
                    yield(i..<range.first.first)
                }
                i = range.first.last + 1
            }

            if (i <= keyRange.last) {
                yield(i..keyRange.last)
            }
        }

        return (mapped.asSequence().map { it.second } + unmapped)
            .map { range -> transformValue(range.first)..transformValue(range.last) }
            .toSet()
    }

    fun mapAll(keys: Iterable<ClosedRange<K>>): Set<ClosedRange<V>> = keys.flatMap(::map).toSet()
}

operator fun ClosedRange<Seed>.iterator() = (start.value..endInclusive.value).asSequence().map(::Seed).iterator()
