package lesson11.task1

import lesson1.task1.sqr

/**
 * Фабричный метод для создания комплексного числа из строки вида x+yi
 */
fun Complex(s: String): Complex {
    val reim = s.removeSuffix("i").replace("-", " -")
        .replace("+", " +").removePrefix(" ")
        .split(" ").map { it.toDouble() }
    return Complex(reim.first(), reim.last())
}

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(
        this.re + other.re,
        this.im + other.im
    )

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(
        -this.re,
        -this.im
    )

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(
        this.re - other.re,
        this.im - other.im
    )

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(
        this.re * other.re - this.im * other.im,
        this.im * other.re + this.re * other.im
    )

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex = Complex(
        (this.re * other.re + this.im * other.im) / (sqr(other.im) + sqr(other.re)),
        (this.im * other.re - this.re * other.im) / (sqr(other.im) + sqr(other.re))
    )

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = TODO()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = TODO()
    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}
