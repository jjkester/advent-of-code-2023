package nl.jjkester.adventofcode23.day21.model

import nl.jjkester.adventofcode23.predef.space.Coordinate2D
import org.apache.commons.math3.fitting.PolynomialCurveFitter
import org.apache.commons.math3.fitting.WeightedObservedPoint
import kotlin.math.roundToInt

/**
 * A polynomial with the given [factors] in order of weight.
 */
data class Polynomial(val factors: List<Int>) {

    init {
        require(factors.size > 1) { "At least one factor required" }
    }

    /**
     * Computes and returns the resulting value for the given [x] value.
     */
    operator fun invoke(x: Int): Long = factors
        .withIndex()
        .sumOf { (i, p) -> p * (List(i) { x.toLong() }.reduceOrNull(Long::times) ?: 1L) }

    override fun toString(): String = buildString {
        factors.forEachIndexed { index, factor ->
            append(factor.toString())

            when (index) {
                0 -> Unit
                1 -> append("x")
                else -> append("x^$index")
            }

            if (index < factors.lastIndex) {
                append(" + ")
            }
        }
    }

    companion object {

        /**
         * Finds and returns a quadratic function that crosses the [observed] points as closely as possible.
         */
        fun fit(observed: List<Coordinate2D>): Polynomial = PolynomialCurveFitter.create(2)
            .fit(observed.map { WeightedObservedPoint(1.0, it.x.toDouble(), it.y.toDouble()) })
            .let { result -> Polynomial(result.map { it.roundToInt() }) }
    }
}
