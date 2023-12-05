package nl.jjkester.adventofcode23.day05.model

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
