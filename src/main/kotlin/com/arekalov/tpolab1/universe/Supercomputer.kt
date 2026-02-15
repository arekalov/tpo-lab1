package com.arekalov.tpolab1.universe

/**
 * Суперкомпьютер для вычисления ответа на главный вопрос жизни, вселенной и всего такого
 */
class Supercomputer(
    val name: String,
) {
    private var isCalculating: Boolean = false
    private var calculationProgress: Int = 0
    private val calculationTime: Int = 7_500_000 // 7.5 миллионов лет в каких-то условных единицах
    private var answer: Int? = null

    /**
     * Запустить вычисление
     */
    fun startCalculation(): String {
        if (isCalculating) {
            return "Вычисление уже идет"
        }
        isCalculating = true
        calculationProgress = 0
        return "$name начинает глубокие размышления..."
    }

    /**
     * Продолжить вычисление (симуляция прогресса)
     */
    fun tick(timeUnits: Int = 1): Int {
        if (!isCalculating) {
            return calculationProgress
        }

        calculationProgress += timeUnits

        if (calculationProgress >= calculationTime) {
            answer = 42
            isCalculating = false
            calculationProgress = calculationTime
        }

        return calculationProgress
    }

    /**
     * Получить ответ
     */
    fun getAnswer(): Int? {
        if (answer == null && calculationProgress >= calculationTime) {
            answer = 42
        }
        return answer
    }

    /**
     * Получить прогресс вычисления в процентах
     */
    fun getProgressPercentage(): Double =
        (calculationProgress.toDouble() / calculationTime) * 100

    /**
     * Проверить, завершено ли вычисление
     */
    fun isCalculationComplete(): Boolean = calculationProgress >= calculationTime

    /**
     * Проверить, идет ли вычисление
     */
    fun isCurrentlyCalculating(): Boolean = isCalculating

    /**
     * Сбросить суперкомпьютер
     */
    fun reset() {
        isCalculating = false
        calculationProgress = 0
        answer = null
    }

    /**
     * Задать вопрос суперкомпьютеру
     */
    fun askQuestion(question: String): String {
        return when {
            !isCalculationComplete() && calculationProgress == 0 ->
                "Я еще не начал вычисления. Запустите startCalculation() сначала."
            !isCalculationComplete() ->
                "Я все еще думаю... Прогресс: ${getProgressPercentage().toInt()}%"
            else -> {
                val ans = getAnswer()
                "Ответ на вопрос '$question' равен $ans"
            }
        }
    }
}
