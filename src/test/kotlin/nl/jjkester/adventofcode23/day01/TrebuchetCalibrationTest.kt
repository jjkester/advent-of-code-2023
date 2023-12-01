package nl.jjkester.adventofcode23.day01

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class TrebuchetCalibrationTest {

    @Test
    fun sumOfCalibrationValues() {
        val input = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """.trimIndent()

        assertThat(TrebuchetCalibration.sumOfCalibrationValues(input)).isEqualTo(142)
    }

    @Test
    fun sumOfCalibrationValuesIncludingWords() {
        val input = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent()

        assertThat(TrebuchetCalibration.sumOfCalibrationValuesIncludingWords(input)).isEqualTo(281)
    }
}
