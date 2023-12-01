package nl.jjkester.adventofcode23.execution

import java.io.File

/**
 * Generic implementation of a main function for every Advent of Code day.
 *
 * @param dayNumber Number of the day. This is used to select the input file.
 * @param inputParser Function that processes the raw, multi-line string input into the desired representation.
 * @param partOne Implementation of the first puzzle of the day.
 * @param partTwo Implementation of the second puzzle of the day.
 */
fun <T, I> T.runAdventOfCode(
    dayNumber: Int,
    inputParser: T.(String) -> I,
    partOne: T.(I) -> Any,
    partTwo: (T.(I) -> Any)? = null
) {
    require(dayNumber in 1..25) {
        "Day number must be between 1 and 25 (inclusive)"
    }

    val inputFileResourcePath = "/day${dayNumber.toString().padStart(2, '0')}/input.txt"
    val inputFileResource = checkNotNull({}.javaClass.getResource(inputFileResourcePath)) {
        "Input file resource $inputFileResourcePath does not exist"
    }.run { File(toURI()) }

    println("Input: $inputFileResource")

    val inputFileContent = inputFileResource.readText().trim()
    val parsedInput = inputParser(inputFileContent)

    print("Part one: ")
    println(partOne(parsedInput))

    print("Part two: ")
    println(partTwo?.let { it(parsedInput) } ?: "(not set)")
}

/**
 * DSL for a main function for every Advent of Code day.
 *
 * @param R Implementation object for the Advent of Code day.
 * @param T Type of the input for both puzzles of the day.
 * @param dayNumber Number of the day. This is used to select the input file.
 * @param block Configuration for this day.
 */
fun <R : Any, T : Any> R.isDay(dayNumber: Int, block: MainFunctionScope<R, T>.() -> MainFunctionScope.Complete<R, T>) {
    when (val configuration = MainFunctionScopeImpl<R, T>().block()) {
        is MainFunctionScopeImpl.PartialDay -> runAdventOfCode(
            dayNumber = dayNumber,
            inputParser = configuration.parseFunction,
            partOne = configuration.partOne
        )

        is MainFunctionScopeImpl.Complete -> runAdventOfCode(
            dayNumber = dayNumber,
            inputParser = configuration.parseFunction,
            partOne = configuration.partOne,
            partTwo = configuration.partTwo
        )
    }
}

@DslMarker
private annotation class MainFunctionDsl

/**
 * DSL scope for configuring an Advent of Code day.
 *
 * @param R Implementation object for the Advent of Code day.
 * @param T Type of the input for both puzzles of the day.
 */
@MainFunctionDsl
sealed interface MainFunctionScope<R : Any, T : Any> {

    /**
     * Marker to use the raw input without parsing it. Only use this marker if the solutions take a [String] as input.
     */
    val withoutParsing: InputMarker<R, String>

    /**
     * Configures the runtime to parse the raw input using the provided [parseFunction].
     */
    infix fun parsedWith(parseFunction: R.(String) -> T): InputMarker<R, T>

    /**
     * Sets the solution to the first puzzle of the day.
     */
    infix fun InputMarker<R, T>.first(partOne: R.(T) -> Any): PartialDay<R, T>

    /**
     * Sets the solution to the second puzzle of the day. This is optional.
     */
    infix fun PartialDay<R, T>.then(partTwo: R.(T) -> Any): Complete<R, T>

    /**
     * Marker interface to indicate that an input value is configured.
     *
     * From this marker the first puzzle of the day can be configured.
     *
     * @param R Implementation object for the Advent of Code day.
     * @param T Type of the input provided by the configuration.
     */
    @MainFunctionDsl
    sealed interface InputMarker<in R : Any, out T : Any>

    /**
     * Marker interface to indicate that an input value and the first puzzle of the day are configured.
     *
     * From this marker the second puzzle of the day can be configured. This marker also serves as an indication of
     * a minimum runnable Advent of Code solution.
     *
     * @param R Implementation object for the Advent of Code day.
     * @param T Type of the input for both puzzles of the day.
     */
    @MainFunctionDsl
    sealed interface PartialDay<in R : Any, T : Any> : Complete<R, T>

    /**
     * Marker interface to indicate that the configuration of the day is complete.
     *
     * @param R Implementation object for the Advent of Code day.
     * @param T Type of the input for both puzzles of the day.
     */
    @MainFunctionDsl
    sealed interface Complete<in R : Any, T : Any>
}

private class MainFunctionScopeImpl<R : Any, T : Any> : MainFunctionScope<R, T> {

    override val withoutParsing: MainFunctionScope.InputMarker<R, String>
        get() = InputMarker { it }

    override fun parsedWith(parseFunction: R.(String) -> T): MainFunctionScope.InputMarker<R, T> =
        InputMarker(parseFunction)

    override fun MainFunctionScope.InputMarker<R, T>.first(partOne: R.(T) -> Any): MainFunctionScope.PartialDay<R, T> =
        when (this) {
            is InputMarker -> PartialDay(parseFunction, partOne)
        }

    override fun MainFunctionScope.PartialDay<R, T>.then(partTwo: R.(T) -> Any): MainFunctionScope.Complete<R, T> =
        when (this) {
            is PartialDay -> Complete(parseFunction, partOne, partTwo)
        }

    class InputMarker<in R : Any, out T : Any>(
        val parseFunction: R.(String) -> T
    ) : MainFunctionScope.InputMarker<R, T>

    class PartialDay<in R : Any, T : Any>(
        val parseFunction: R.(String) -> T,
        val partOne: R.(T) -> Any
    ) : MainFunctionScope.PartialDay<R, T>

    class Complete<in R : Any, T : Any>(
        val parseFunction: R.(String) -> T,
        val partOne: R.(T) -> Any,
        val partTwo: R.(T) -> Any
    ) : MainFunctionScope.Complete<R, T>
}
