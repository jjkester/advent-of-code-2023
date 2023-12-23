package nl.jjkester.adventofcode23.day19.model

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import org.junit.jupiter.api.Test

class PartSystemTest {

    @Test
    fun parse() {
        assertThat(PartSystem.parse(testInput)).all {
            transform { it.workflows }
                .extracting { it.name.value to it.rules.size }
                .containsExactly(
                    "px" to 3,
                    "pv" to 2,
                    "lnx" to 2,
                    "rfg" to 3,
                    "qs" to 2,
                    "qkq" to 2,
                    "crn" to 2,
                    "in" to 2,
                    "qqz" to 3,
                    "gd" to 2,
                    "hdj" to 2
                )
            transform { it.parts }.containsExactly(
                Part(x = 787, m = 2655, a = 1222, s = 2876),
                Part(x = 1679, m = 44, a = 2067, s = 496),
                Part(x = 2036, m = 264, a = 79, s = 2244),
                Part(x = 2461, m = 1339, a = 466, s = 291),
                Part(x = 2127, m = 1623, a = 2188, s = 1013)
            )
        }
    }

    @Test
    fun acceptedParts() {
        assertThat(parsedTestInput.acceptedParts())
            .containsExactlyInAnyOrder(
                Part(x = 787, m = 2655, a = 1222, s = 2876),
                Part(x = 2036, m = 264, a = 79, s = 2244),
                Part(x = 2127, m = 1623, a = 2188, s = 1013)
            )
    }

    @Test
    fun acceptableParts() {
        assertThat(parsedTestInput.acceptableParts())
            .containsExactlyInAnyOrder(
                AcceptableParts(x = 1..1415, m = 1..4000, a = 1..2005, s = 1..1350),
                AcceptableParts(x = 2663..4000, m = 1..4000, a = 1..2005, s = 1..1350),
                AcceptableParts(x = 1..4000, m = 2091..4000, a = 2006..4000, s = 1..1350),
                AcceptableParts(x = 1..2440, m = 1..2090, a = 2006..4000, s = 537..1350),
                AcceptableParts(x = 1..4000, m = 1..4000, a = 1..4000, s = 3449..4000),
                AcceptableParts(x = 1..4000, m = 1549..4000, a = 1..4000, s = 2771..3448),
                AcceptableParts(x = 1..4000, m = 1..1548, a = 1..4000, s = 2771..3448),
                AcceptableParts(x = 1..4000, m = 839..1800, a = 1..4000, s = 1351..2770),
                AcceptableParts(x = 1..4000, m = 1..838, a = 1..1716, s = 1351..2770)
            )
    }

    companion object {
        private val testInput = """
            px{a<2006:qkq,m>2090:A,rfg}
            pv{a>1716:R,A}
            lnx{m>1548:A,A}
            rfg{s<537:gd,x>2440:R,A}
            qs{s>3448:A,lnx}
            qkq{x<1416:A,crn}
            crn{x>2662:A,R}
            in{s<1351:px,qqz}
            qqz{s>2770:qs,m<1801:hdj,R}
            gd{a>3333:R,R}
            hdj{m>838:A,pv}

            {x=787,m=2655,a=1222,s=2876}
            {x=1679,m=44,a=2067,s=496}
            {x=2036,m=264,a=79,s=2244}
            {x=2461,m=1339,a=466,s=291}
            {x=2127,m=1623,a=2188,s=1013}
        """.trimIndent()

        private val parsedTestInput by lazy(LazyThreadSafetyMode.NONE) {
            PartSystem.parse(testInput)
        }
    }
}
