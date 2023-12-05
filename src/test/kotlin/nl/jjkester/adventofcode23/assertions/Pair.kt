package nl.jjkester.adventofcode23.assertions

import assertk.Assert
import assertk.assertions.prop

fun <A, B> Assert<Pair<A, B>>.first() = prop("first", Pair<A, B>::first)

fun <A, B> Assert<Pair<A, B>>.second() = prop("second", Pair<A, B>::second)
