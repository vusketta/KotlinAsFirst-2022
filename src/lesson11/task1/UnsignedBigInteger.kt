package lesson11.task1

import lesson3.task1.digitNumber
import lesson4.task1.pow
import java.math.BigInteger
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
    private var mag: MutableList<Int>

    val size: Int get() = mag.size
    val isZero: Boolean get() = size == 1 && mag[0] == 0

    companion object {
        val BASE = UnsignedBigInteger(Int.MAX_VALUE)
        val ONE = UnsignedBigInteger(1)
    }

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        if (!s.matches(Regex("""\d+"""))) throw IllegalArgumentException()
        val number = skipLeadingZeroes(s)
        mag = mutableListOf()
    }

    private fun skipLeadingZeroes(s: String): String {
        var cursor = 0
        while (cursor < s.length && s[cursor] == '0') cursor++
        return s.substring(cursor)
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        if (i < 0) throw IllegalArgumentException()
        mag = mutableListOf(i)
    }

    private constructor() {
        mag = mutableListOf()
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

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
    override fun equals(other: Any?): Boolean = other is UnsignedBigInteger && mag == other.mag

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int = TODO()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = TODO()

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int = if (size == 1) mag[0] else throw ArithmeticException()

    override fun hashCode(): Int = javaClass.hashCode()
}