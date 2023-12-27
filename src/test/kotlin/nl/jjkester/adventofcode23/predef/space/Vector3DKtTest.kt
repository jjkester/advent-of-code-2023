package nl.jjkester.adventofcode23.predef.space

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.random.Random

class Vector3DKtTest {

    @Test
    fun toVector() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()

        assertThat(coordinateOf(x, y, z).toVector())
            .isEqualTo(vectorOf(x, y, z))
    }

    @Test
    fun plus() {
        val x1 = Random.nextInt()
        val y1 = Random.nextInt()
        val z1 = Random.nextInt()
        val x2 = Random.nextInt()
        val y2 = Random.nextInt()
        val z2 = Random.nextInt()

        assertThat(vectorOf(x1, y1, z1) + vectorOf(x2, y2, z2))
            .isEqualTo(vectorOf(x1 + x2, y1 + y2, z1 + z2))

        assertThat(vectorOf(x1, y1, z1) + vectorOf(x2, y2, z2))
            .isEqualTo(vectorOf(x2, y2, z2) + vectorOf(x1, y1, z1))
    }

    @Test
    fun minus() {
        val x1 = Random.nextInt()
        val y1 = Random.nextInt()
        val z1 = Random.nextInt()
        val x2 = Random.nextInt()
        val y2 = Random.nextInt()
        val z2 = Random.nextInt()

        assertThat(vectorOf(x1, y1, z1) - vectorOf(x2, y2, z2))
            .isEqualTo(vectorOf(x1 - x2, y1 - y2, z1 - z2))

        assertThat(abs(vectorOf(x1, y1, z1) - vectorOf(x2, y2, z2)))
            .isEqualTo(abs(vectorOf(x2, y2, z2) - vectorOf(x1, y1, z1)))
    }

    @Test
    fun unaryMinus() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()

        assertThat(-vectorOf(x, y, z))
            .isEqualTo(vectorOf(-x, -y, -z))
    }

    @Test
    fun times() {
        val x = Random.nextInt()
        val y = Random.nextInt()
        val z = Random.nextInt()
        val n = Random.nextInt()

        assertThat(vectorOf(x, y, z) * n)
            .isEqualTo(vectorOf(x * n, y * n, z * n))
    }

    @Test
    fun abs() {
        val x1 = Random.nextInt(Int.MIN_VALUE, -1)
        val y1 = Random.nextInt(Int.MIN_VALUE, -1)
        val z1 = Random.nextInt(Int.MIN_VALUE, -1)
        val x2 = Random.nextInt(1, Int.MAX_VALUE)
        val y2 = Random.nextInt(1, Int.MAX_VALUE)
        val z2 = Random.nextInt(1, Int.MAX_VALUE)

        assertThat(abs(vectorOf(x1, y1, z1)))
            .isEqualTo(vectorOf(x1.absoluteValue, y1.absoluteValue, z1.absoluteValue))

        assertThat(abs(vectorOf(x2, y2, z2)))
            .isEqualTo(vectorOf(x2, y2, z2))
    }

    @Test
    fun absoluteValue() {
        val x1 = Random.nextInt(Int.MIN_VALUE, -1)
        val y1 = Random.nextInt(Int.MIN_VALUE, -1)
        val z1 = Random.nextInt(Int.MIN_VALUE, -1)
        val x2 = Random.nextInt(1, Int.MAX_VALUE)
        val y2 = Random.nextInt(1, Int.MAX_VALUE)
        val z2 = Random.nextInt(1, Int.MAX_VALUE)

        assertThat(vectorOf(x1, y1, z1).absoluteValue)
            .isEqualTo(vectorOf(x1.absoluteValue, y1.absoluteValue, z1.absoluteValue))

        assertThat(vectorOf(x2, y2, z2).absoluteValue)
            .isEqualTo(vectorOf(x2, y2, z2))
    }
}
