@file:Suppress("UNUSED_PARAMETER")

package lesson9.task2

import lesson9.task1.*
import java.util.*
import kotlin.math.abs

// Все задачи в этом файле требуют наличия реализации интерфейса "Матрица" в Matrix.kt

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 * При транспонировании строки матрицы становятся столбцами и наоборот:
 *
 * 1 2 3      1 4 6 3
 * 4 5 6  ==> 2 5 5 2
 * 6 5 4      3 6 4 1
 * 3 2 1
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

/**
 * Пример
 *
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    require(!(width != other.width || height != other.height))
    if (width < 1 || height < 1) return this
    val result = createMatrix(height, width, this[0, 0])
    for (i in 0 until height) {
        for (j in 0 until width) {
            result[i, j] = this[i, j] + other[i, j]
        }
    }
    return result
}

/**
 * Сложная (5 баллов)
 *
 * Заполнить матрицу заданной высоты height и ширины width
 * натуральными числами от 1 до m*n по спирали,
 * начинающейся в левом верхнем углу и закрученной по часовой стрелке.
 *
 * Пример для height = 3, width = 4:
 *  1  2  3  4
 * 10 11 12  5
 *  9  8  7  6
 */
fun generateSpiral(height: Int, width: Int): Matrix<Int> {
    val matrix = createMatrix(height, width, 0)
    var (l, r, u, d) = listOf(0, width - 1, 0, height - 1)
    var value = 1
    while (true) {
        if (l > r) break
        for (i in l..r) {
            matrix[u, i] = value++
        }
        u++
        if (u > d) break
        for (i in u..d) {
            matrix[i, r] = value++
        }
        r--
        if (l > r) break
        for (i in r downTo l) {
            matrix[d, i] = value++
        }
        d--
        if (u > d) break
        for (i in d downTo u) {
            matrix[i, l] = value++
        }
        l++
    }
    return matrix
}

/**
 * Сложная (5 баллов)
 *
 * Заполнить матрицу заданной высоты height и ширины width следующим образом.
 * Элементам, находящимся на периферии (по периметру матрицы), присвоить значение 1;
 * периметру оставшейся подматрицы – значение 2 и так далее до заполнения всей матрицы.
 *
 * Пример для height = 5, width = 6:
 *  1  1  1  1  1  1
 *  1  2  2  2  2  1
 *  1  2  3  3  2  1
 *  1  2  2  2  2  1
 *  1  1  1  1  1  1
 */
fun generateRectangles(height: Int, width: Int): Matrix<Int> {
    val matrix = createMatrix(height, width, 0)
    var (l, r, u, d) = listOf(0, width - 1, 0, height - 1)
    var value = 0

    while (true) {
        value++
        if (l > r) break
        for (i in l..r) {
            matrix[u, i] = value
        }
        u++
        if (u > d) break
        for (i in u..d) {
            matrix[i, r] = value
        }
        r--
        if (l > r) break
        for (i in r downTo l) {
            matrix[d, i] = value
        }
        d--
        if (u > d) break
        for (i in d downTo u) {
            matrix[i, l] = value
        }
        l++
    }
    return matrix
}

/**
 * Сложная (5 баллов)
 *
 * Заполнить матрицу заданной высоты height и ширины width диагональной змейкой:
 * в левый верхний угол 1, во вторую от угла диагональ 2 и 3 сверху вниз, в третью 4-6 сверху вниз и так далее.
 *
 * Пример для height = 5, width = 4:
 *  1  2  4  7
 *  3  5  8 11
 *  6  9 12 15
 * 10 13 16 18
 * 14 17 19 20
 */
fun generateSnake(height: Int, width: Int): Matrix<Int> = TODO()

/**
 * Средняя (3 балла)
 *
 * Содержимое квадратной матрицы matrix (с произвольным содержимым) повернуть на 90 градусов по часовой стрелке.
 * Если height != width, бросить IllegalArgumentException.
 *
 * Пример:    Станет:
 * 1 2 3      7 4 1
 * 4 5 6      8 5 2
 * 7 8 9      9 6 3
 */
fun <E> rotate(matrix: Matrix<E>): Matrix<E> {
    if (matrix.height != matrix.width) throw IllegalArgumentException()
    val matrixTransposed = transpose(matrix)
    for (i in 0 until matrix.height) {
        for (j in 0 until matrix.width / 2) {
            matrixTransposed[i, j] = matrixTransposed[i, matrix.height - j - 1]
                .also { matrixTransposed[i, matrix.height - j - 1] = matrixTransposed[i, j] }
        }
    }
    return matrixTransposed
}

/**
 * Сложная (5 баллов)
 *
 * Проверить, является ли квадратная целочисленная матрица matrix латинским квадратом.
 * Латинским квадратом называется матрица размером n x n,
 * каждая строка и каждый столбец которой содержат все числа от 1 до n.
 * Если height != width, вернуть false.
 *
 * Пример латинского квадрата 3х3:
 * 2 3 1
 * 1 2 3
 * 3 1 2
 */
fun <E> getRow(matrix: Matrix<E>, row: Int) = List(matrix.width) { matrix[row, it] }

fun <E> getColumn(matrix: Matrix<E>, column: Int) = List(matrix.height) { matrix[it, column] }

fun isLatinSquare(matrix: Matrix<Int>): Boolean {
    if (matrix.height != matrix.width) return false
    for (i in 0 until matrix.height)
        if (!getRow(matrix, i).toSet().containsAll((1..matrix.height).toSet())) return false
    for (i in 0 until matrix.width)
        if (!getColumn(matrix, i).toSet().containsAll((1..matrix.width).toSet())) return false
    return true
}

/**
 * Средняя (3 балла)
 *
 * В матрице matrix каждый элемент заменить суммой непосредственно примыкающих к нему
 * элементов по вертикали, горизонтали и диагоналям.
 *
 * Пример для матрицы 4 x 3: (11=2+4+5, 19=1+3+4+5+6, ...)
 * 1 2 3       11 19 13
 * 4 5 6  ===> 19 31 19
 * 6 5 4       19 31 19
 * 3 2 1       13 19 11
 *
 * Поскольку в матрице 1 х 1 примыкающие элементы отсутствуют,
 * для неё следует вернуть как результат нулевую матрицу:
 *
 * 42 ===> 0
 */
fun sumNeighbours(matrix: Matrix<Int>): Matrix<Int> {
    val result = createMatrix(matrix.height, matrix.width, 0)
    matrix.forEach { (first, second), _ -> result[first, second] = matrix.neighbourSum(first, second) }
    return result
}

fun Matrix<Int>.neighbourSum(i: Int, j: Int): Int {
    val neighbours = listOf(
        i - 1 to j - 1, i - 1 to j, i - 1 to j + 1, i to j - 1, i to j + 1,
        i + 1 to j - 1, i + 1 to j, i + 1 to j + 1
    )
    var sum = 0
    for ((first, second) in neighbours) {
        if (first in 0 until height && second in 0 until width)
            sum += this[first, second]
    }
    return sum
}

/**
 * Средняя (4 балла)
 *
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val rows: MutableList<Int> = mutableListOf()
    val columns: MutableList<Int> = mutableListOf()
    for (i in 0 until matrix.height)
        if (!getRow(matrix, i).contains(1)) rows.add(i)
    for (i in 0 until matrix.width)
        if (!getColumn(matrix, i).contains(1)) columns.add(i)
    return Holes(rows, columns)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Средняя (3 балла)
 *
 * В целочисленной матрице matrix каждый элемент заменить суммой элементов подматрицы,
 * расположенной в левом верхнем углу матрицы matrix и ограниченной справа-снизу данным элементом.
 *
 * Пример для матрицы 3 х 3:
 *
 * 1  2  3      1  3  6
 * 4  5  6  =>  5 12 21
 * 7  8  9     12 27 45
 *
 * К примеру, центральный элемент 12 = 1 + 2 + 4 + 5, элемент в левом нижнем углу 12 = 1 + 4 + 7 и так далее.
 */
fun sumSubMatrix(matrix: Matrix<Int>): Matrix<Int> {
    for (i in 1 until matrix.width) matrix[0, i] += matrix[0, i - 1]
    for (i in 1 until matrix.height) matrix[i, 0] += matrix[i - 1, 0]
    matrix.forEach { (i, j), _ ->
        if (0 < i && i < matrix.height &&
            0 < j && j < matrix.width
        ) matrix[i, j] += matrix[i - 1, j] + matrix[i, j - 1] - matrix[i - 1, j - 1]
    }
    return matrix
}

/**
 * Простая (2 балла)
 *
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    for (i in 0 until height) {
        for (j in 0 until width) {
            this[i, j] = -this[i, j]
        }
    }
    return this
}

/**
 * Средняя (4 балла)
 *
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    if (this.width != other.height) throw IllegalArgumentException()
    val result = createMatrix(this.height, other.width, 0)
    for (i in 0 until this.height) {
        for (j in 0 until other.width) {
            for (k in 0 until this.width) {
                result[i, j] += this[i, k] * other[k, j]
            }
        }
    }
    return result
}

/**
 * Сложная (7 баллов)
 *
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (rowShift in 0..lock.height - key.height) {
        for (colShift in 0..lock.width - key.width) {
            loop@ for (i in 0 until key.height) {
                for (j in 0 until key.width) {
                    if (lock[i + rowShift, j + colShift] == 1 && key[i, j] != 0) {
                        break@loop
                    }
                    if (i == key.height - 1 && j == key.width - 1) return Triple(true, rowShift, colShift)
                }
            }
        }
    }
    return Triple(false, -1, -1)
}

/**
 * Сложная (8 баллов)
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  1
 *  2 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой. Цель игры -- упорядочить фишки на игровом поле.
 *
 * В списке moves задана последовательность ходов, например [8, 6, 13, 11, 10, 3].
 * Ход задаётся номером фишки, которая передвигается на пустое место (то есть, меняется местами с нулём).
 * Фишка должна примыкать к пустому месту по горизонтали или вертикали, иначе ход не будет возможным.
 * Все номера должны быть в пределах от 1 до 15.
 * Определить финальную позицию после выполнения всех ходов и вернуть её.
 * Если какой-либо ход является невозможным или список содержит неверные номера,
 * бросить IllegalStateException.
 *
 * В данном случае должно получиться
 * 5  7  9  1
 * 2 12 14 15
 * 0  4 13  6
 * 3 10 11  8
 */

fun fifteenGameMoves(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {
    for (move in moves) {
        val (zeroIndices, moveIndices) = listOf(matrix.indicesOf(0), matrix.indicesOf(move))
        if (moveIndices == -1 to -1 ||
            abs(moveIndices.first - zeroIndices.first) > 1 ||
            abs(moveIndices.second - zeroIndices.second) > 1 ||
            abs(moveIndices.first - zeroIndices.first) == 1 &&
            abs(moveIndices.second - zeroIndices.second) == 1
        ) throw IllegalStateException()
        matrix[zeroIndices.first, zeroIndices.second] = move
        matrix[moveIndices.first, moveIndices.second] = 0
    }
    return matrix
}

/**
 * Очень сложная (32 балла)
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  2
 *  1 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой.
 *
 * Цель игры -- упорядочить фишки на игровом поле, приведя позицию к одному из следующих двух состояний:
 *
 *  1  2  3  4          1  2  3  4
 *  5  6  7  8   ИЛИ    5  6  7  8
 *  9 10 11 12          9 10 11 12
 * 13 14 15  0         13 15 14  0
 *
 * Можно математически доказать, что РОВНО ОДНО из этих двух состояний достижимо из любой исходной позиции.
 *
 * Вернуть решение -- список ходов, приводящих исходную позицию к одной из двух упорядоченных.
 * Каждый ход -- это перемена мест фишки с заданным номером с пустой клеткой (0),
 * при этом заданная фишка должна по горизонтали или по вертикали примыкать к пустой клетке (но НЕ по диагонали).
 * К примеру, ход 13 в исходной позиции меняет местами 13 и 0, а ход 11 в той же позиции невозможен.
 *
 * Одно из решений исходной позиции:
 *
 * [8, 6, 14, 12, 4, 11, 13, 14, 12, 4,
 * 7, 5, 1, 3, 11, 7, 3, 11, 7, 12, 6,
 * 15, 4, 9, 2, 4, 9, 3, 5, 2, 3, 9,
 * 15, 8, 14, 13, 12, 7, 11, 5, 7, 6,
 * 9, 15, 8, 14, 13, 9, 15, 7, 6, 12,
 * 9, 13, 14, 15, 12, 11, 10, 9, 13, 14,
 * 15, 12, 11, 10, 9, 13, 14, 15]
 *
 * Перед решением этой задачи НЕОБХОДИМО решить предыдущую
 */
data class State(val matrix: Matrix<Int>, val move: Int, val previous: State?)

fun isGoal(state: State, isSolvable: Boolean): Boolean {
    val goal = createMatrix(4, 4, 0)
    var i = 0
    goal.forEach { (first, second), _ -> goal[first, second] = if (first == 3 && second == 3) 0 else ++i }
    if (!isSolvable) {
        goal[3, 1] = 15
        goal[3, 2] = 14
    }
    return state.matrix == goal
}

fun getNeighbour(matrix: Matrix<Int>, cell: Int, type: String): Triple<Int, Int, Int> {
    val cellPos = matrix.indicesOf(cell)
    val neighbor = when (type) {
        "up" -> cellPos.first + 1 to cellPos.second
        "down" -> cellPos.first - 1 to cellPos.second
        "right" -> cellPos.first to cellPos.second + 1
        "left" -> cellPos.first to cellPos.second - 1
        else -> -1 to -1
    }
    return Triple(
        neighbor.first,
        neighbor.second,
        if (neighbor.first !in 0 until matrix.height || neighbor.second !in 0 until matrix.width) -1
        else matrix[neighbor.first, neighbor.second]
    )
}

fun countDisorder(matrix: Matrix<Int>, e: Int): Int {
    val ePos = matrix.indicesOf(e)
    var disorders = 0
    matrix.forEach { (first, second), i -> if (ePos.first + ePos.second < first + second && e > i) disorders++ }
    return disorders
}

fun fifteenGameSolution(matrix: Matrix<Int>): List<Int> {
    val isSolvable = matrix.map { countDisorder(matrix, it) }.sum() % 2 == 0
    val moves = mutableListOf<Int>()
    val stack = Stack<State>()
    val visited = mutableSetOf<Matrix<Int>>()
    var resultState: State? = null
    stack.add(State(matrix, -1, null))

    fun makeMove(state: State, type: String) {
        val neighbour = getNeighbour(state.matrix, 0, type)
        if (neighbour.first !in 0 until state.matrix.height
            || neighbour.second !in 0 until state.matrix.width
        ) return
        val zero = state.matrix.indicesOf(0)
        val newMatrix = state.matrix.copy()
        newMatrix[neighbour.first, neighbour.second] = 0
        newMatrix[zero.first, zero.second] = neighbour.third
        if (!visited.contains(newMatrix)) stack.add(State(newMatrix, neighbour.third, state))
    }

    while (stack.isNotEmpty()) {
        val curr = stack.pop()
        if (!visited.contains(curr.matrix)) {
            if (isGoal(curr, isSolvable)) {
                resultState = curr
                break
            }
            makeMove(curr, "up")
            makeMove(curr, "down")
            makeMove(curr, "right")
            makeMove(curr, "left")
            visited.add(curr.matrix)
        }
    }

    while (resultState!!.previous != null) {
        moves.add(resultState.move)
        resultState = resultState.previous
    }

    return moves
}
//TODO() всё очень плохо