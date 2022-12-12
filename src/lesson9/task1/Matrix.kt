package lesson9.task1

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

inline fun <E> Matrix<E>.forEach(action: (Pair<Int, Int>, E) -> Unit) {
    for (i in 0 until height) for (j in 0 until width) action(i to j, this[i, j])
}

inline fun <T, R> Matrix<T>.map(transform: (T) -> R): Matrix<R> {
    val mapped = createMatrix(height, width, transform(this[0, 0]))
    forEach { (first, second), i -> mapped[first, second] = transform(i) }
    return mapped
}

fun Matrix<Int>.sum(): Int {
    var sum = 0
    forEach { _, i -> sum += i }
    return sum
}

fun <E> Matrix<E>.indicesOf(element: E): Pair<Int, Int> {
    for (i in 0 until height) {
        for (j in 0 until width) {
            if (element == this[i, j]) return i to j
        }
    }
    return -1 to -1
}

fun <E> Matrix<E>.copy(): Matrix<E> {
    val copy = createMatrix(height, width, this[0, 0])
    copy.forEach { (first, second), _ -> copy[first, second] = this[first, second] }
    return copy
}

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    init {
        if (height <= 0 || width <= 0) throw IllegalArgumentException()
    }

    private val data = MutableList(height * width) { e }

    override fun get(row: Int, column: Int): E = data[row * width + column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        data[row * width + column] = value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    override fun equals(other: Any?) = other is MatrixImpl<*> && data == other.data

    override fun toString(): String {
        val stringBuilder = StringBuilder().appendLine("[")
        for (row in 0 until height) stringBuilder.appendLine(data.subList(row * width, (row + 1) * width))
        return stringBuilder.append("]").toString()
    }

    override fun hashCode(): Int =
        31 * (31 * height + width) + data.hashCode()
}
