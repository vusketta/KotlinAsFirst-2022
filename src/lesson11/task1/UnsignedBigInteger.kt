package lesson11.task1

import lesson3.task1.digitNumber
import lesson4.task1.pow
import kotlin.math.ceil

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger : Comparable<UnsignedBigInteger> {
    private var mag: MutableList<UByte>
    private var isZero = false

    companion object {
        val NUMBER_BASE = 100u
        val INT_MAX_VALUE = UnsignedBigInteger(Int.MAX_VALUE)
        val BASE = UnsignedBigInteger(100)
        val ONE = UnsignedBigInteger(1)
    }

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        if (!s.matches(Regex("""\d+"""))) throw IllegalArgumentException()
        val number = skipLeadingZeroes(s)
        mag = number.chunkedRight(2).map { it.toUByte() }.reversed().toMutableList()
        isZero = mag.isEmpty() || mag[0] == 0.toUByte()
    }

    private fun skipLeadingZeroes(s: String): String {
        var cursor = 0
        while (cursor < s.length && s[cursor] == '0') cursor++
        return s.substring(cursor)
    }

    //Maybe Kotlin has a method, which behavior is similar, but I haven't found it yet.
    private fun String.chunkedRight(size: Int): List<String> {
        val thisSize = this.length
        val resultCapacity = thisSize / 2 + if (thisSize % 2 == 0) 0 else 1
        val result = ArrayList<String>(resultCapacity)
        var index = thisSize
        while (index in 0..thisSize) {
            val begin = index - size
            val coercedBegin = if (begin < 0) 0 else begin
            result.add(substring(coercedBegin, index))
            index -= size
        }
        if (result.last().isEmpty()) result.removeLast()
        return result.reversed()
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        if (i < 0) throw IllegalArgumentException()
        var number = i
        val magSize = ceil(digitNumber(i) / 2.0).toInt()
        mag = mutableListOf()
        repeat(magSize) {
            mag.add((number % 100).toUByte())
            number /= 100
        }
        isZero = i == 0
    }

    private constructor() {
        mag = mutableListOf()
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (isZero) return other
        if (other.isZero) return this
        val result = UnsignedBigInteger()
        val len = maxOf(mag.size, other.mag.size)
        var carry = 0u
        for (i in 0 until len) {
            var sum = carry
            if (i in mag.indices) sum += mag[i]
            if (i in other.mag.indices) sum += other.mag[i]
            result.mag.add((sum % NUMBER_BASE).toUByte())
            carry = if (sum > 99u) 1u else 0u
        }
        if (carry == 1u) result.mag.add(carry.toUByte())
        return result
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (this < other) throw ArithmeticException()
        if (this == other) return UnsignedBigInteger(0)
        if (other.isZero) return this
        val result = UnsignedBigInteger()
        val minLen = minOf(mag.size, other.mag.size)
        val maxLen = maxOf(mag.size, other.mag.size)
        for (i in 0 until minLen) {
            if (mag[i] >= other.mag[i]) {
                result.mag.add((mag[i] - other.mag[i]).toUByte())
            } else {
                result.mag.add((mag[i] + NUMBER_BASE - other.mag[i]).toUByte())
                mag[i + 1]--
            }
        }
        result.mag.addAll(
            if (mag.size == maxLen) mag.subList(minLen, maxLen)
            else other.mag.subList(minLen, maxLen)
        )
        return result
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /*operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        if (isZero || other.isZero) return UnsignedBigInteger(0)
        if (this == BASE) return other.timesBase()
        if (other == BASE) return this.timesBase()
        val min = min(this, other)
        val max = max(this, other)
        if (min.mag.size == 1) return timesSimple(max, min)
        var result = UnsignedBigInteger(0)
        for (i in min.mag.indices) {
            var t1 = timesSimple(max, UnsignedBigInteger((min.mag[i] % 10u).toInt()))
            repeat(i) { t1 = t1.timesBase() }
            result += t1
            if (min.mag[i] / 10u > 0u) {
                var t2 = timesSimple(max, UnsignedBigInteger((min.mag[i] / 10u).toInt()))
                t2.mag.add(0u)
                repeat(i) { t2 = t2.timesBase() }
                result += t2
            }
        }
        return result
    }
    private fun timesSimple(a: UnsignedBigInteger, b: UnsignedBigInteger): UnsignedBigInteger {
        if (b == ONE) return a
        var result = UnsignedBigInteger(0)
        for (i in a.mag.indices) {
            var t = UnsignedBigInteger((a.mag[i] * b.mag[0]).toInt())
            repeat(i) { t = t.timesBase() }
            result += t
        }
        return result
    }

    private fun timesBase(): UnsignedBigInteger {
        val result = UnsignedBigInteger()
        result.mag.add(0u)
        result.mag.addAll(mag)
        return result
    }
*/
    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean =
        other is UnsignedBigInteger && mag == other.mag && isZero == other.isZero

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        if (this == other) return 0
        if (isZero || mag.size < other.mag.size) return -1
        if (other.isZero || mag.size > other.mag.size) return 1

        for (i in mag.size - 1 downTo 0) {
            if (mag[i] > other.mag[i]) return 1
            if (mag[i] < other.mag[i]) return -1
        }
        return 0
    }

    private fun max(a: UnsignedBigInteger, b: UnsignedBigInteger): UnsignedBigInteger =
        if (a > b) a.copy() else b.copy()

    private fun min(a: UnsignedBigInteger, b: UnsignedBigInteger): UnsignedBigInteger =
        if (a < b) a.copy() else b.copy()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = if (isZero) "0" else mag.reversed().joinToString("")

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int = when {
        this > INT_MAX_VALUE -> throw ArithmeticException()
        isZero -> 0
        else -> mag.mapIndexed { index, i -> i.toInt() * pow(100, index) }.sum()
    }

    override fun hashCode(): Int = javaClass.hashCode()

    private fun copy(): UnsignedBigInteger {
        if (isZero) return UnsignedBigInteger(0)
        val result = UnsignedBigInteger()
        result.mag.addAll(mag)
        return result
    }
}