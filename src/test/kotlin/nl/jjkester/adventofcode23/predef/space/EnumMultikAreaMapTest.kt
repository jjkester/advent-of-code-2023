package nl.jjkester.adventofcode23.predef.space

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isIn
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource

class EnumMultikAreaMapTest {

    private val systemUnderTest = object : EnumMultikAreaMap<TestEnum>(
        TestEnum.entries,
        Multik.d2arrayIndices(5, 2) { x, y -> x * 10 + y }
    ) {}

    @ParameterizedTest
    @EnumSource(TestEnum::class, names = ["A", "B"])
    fun `serialization should work for contains when the element is contained in the area`(element: TestEnum) {
        assertThat(systemUnderTest.contains(element))
            .isTrue()
    }

    @Test
    fun `serialization should work for contains when the element is not contained in the area`() {
        assertThat(systemUnderTest.contains(TestEnum.C))
            .isFalse()
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `deserialization should work for get when the stored value represents an enum value`(y: Int) {
        assertThat(systemUnderTest[0, y])
            .isIn(TestEnum.A, TestEnum.B)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `deserialization should throw from get when the stored value does not represent an enum value`(y: Int) {
        assertFailure { systemUnderTest[1, y] }
            .isInstanceOf<IllegalStateException>()
    }

    enum class TestEnum {
        A, B, C
    }
}
