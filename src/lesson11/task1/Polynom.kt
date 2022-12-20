package lesson11.task1

import kotlin.math.max
import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {
    private val data: DoubleArray

    init {
        val nonZeroIndex = coeffs.indexOfFirst { it != 0.0 }
        data = if (coeffs.isEmpty()) doubleArrayOf(0.0) else
            coeffs.copyOfRange(
                if (nonZeroIndex == -1) 0 else nonZeroIndex, coeffs.size
            ).reversedArray()
    }

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = data.getOrNull(i) ?: throw NoSuchElementException()

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double = data.indices.sumOf { coeff(it) * x.pow(it) }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int = max(0, data.lastIndex)

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom = Polynom(
        *DoubleArray(maxOf(data.size, other.data.size)) {
            data.getOrElse(it) { 0.0 } + other.data.getOrElse(it) { 0.0 }
        }.reversedArray()
    )

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(
        *DoubleArray(data.size) { -data[it] }.reversedArray()
    )

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom =plus(other.unaryMinus())

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val result = DoubleArray(data.size + other.data.size) { 0.0 }
        for (i in data.indices) {
            for (j in other.data.indices) {
                result[i + j] += data[i] * other.data[j]
            }
        }
        return Polynom(*result.reversedArray())
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = TODO()

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        other is Polynom && data.contentEquals(other.data)

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int = data.contentHashCode()
}
