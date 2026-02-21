package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SupercomputerTest {

    private fun createSupercomputer(name: String = "Deep Thought"): Supercomputer =
        Supercomputer(name)

    @Test
    @DisplayName("Создание суперкомпьютера")
    fun testCreateSupercomputer() {
        val computer = createSupercomputer()

        assertEquals("Deep Thought", computer.name)
        assertEquals(0.0, computer.progressPercentage)
        assertFalse(computer.isCalculationComplete)
        assertFalse(computer.isCurrentlyCalculating)
    }

    @Test
    @DisplayName("startCalculation запускает вычисление")
    fun testStartCalculation() {
        val computer = createSupercomputer()

        val result = computer.startCalculation()

        assertTrue(result.contains("начинает глубокие размышления"))
        assertTrue(computer.isCurrentlyCalculating)
    }

    @Test
    @DisplayName("startCalculation нельзя запустить дважды")
    fun testStartCalculationTwice() {
        val computer = createSupercomputer()
        computer.startCalculation()

        val result = computer.startCalculation()

        assertTrue(result.contains("Вычисление уже идет"))
    }

    @Test
    @DisplayName("tick увеличивает прогресс")
    fun testTickIncreasesProgress() {
        val computer = createSupercomputer()
        computer.startCalculation()

        val progress1 = computer.tick(timeUnits = 1000)
        val progress2 = computer.tick(timeUnits = 2000)

        assertEquals(1000, progress1)
        assertEquals(3000, progress2)
    }

    @Test
    @DisplayName("tick не работает без запуска вычисления")
    fun testTickWithoutStart() {
        val computer = createSupercomputer()

        val progress = computer.tick(timeUnits = 1000)

        assertEquals(0, progress)
        assertEquals(0.0, computer.progressPercentage)
    }

    @Test
    @DisplayName("progressPercentage вычисляется корректно")
    fun testProgressPercentage() {
        val computer = createSupercomputer()
        computer.startCalculation()

        computer.tick(timeUnits = 750_000)

        assertEquals(10.0, computer.progressPercentage, 0.01)
    }

    @Test
    @DisplayName("tick завершает вычисление при достижении времени")
    fun testTickCompletesCalculation() {
        val computer = createSupercomputer()
        computer.startCalculation()

        computer.tick(timeUnits = 7_500_000)

        assertTrue(computer.isCalculationComplete)
        assertFalse(computer.isCurrentlyCalculating)
        assertEquals(42, computer.getAnswer())
    }

    @Test
    @DisplayName("tick не превышает максимальное время")
    fun testTickDoesNotExceedMaxTime() {
        val computer = createSupercomputer()
        computer.startCalculation()

        computer.tick(timeUnits = 10_000_000)

        assertTrue(computer.isCalculationComplete)
        assertEquals(100.0, computer.progressPercentage)
    }

    @Test
    @DisplayName("getAnswer возвращает null до завершения")
    fun testGetAnswerBeforeComplete() {
        val computer = createSupercomputer()
        computer.startCalculation()

        computer.tick(timeUnits = 1_000_000)

        assertNull(computer.getAnswer())
    }

    @Test
    @DisplayName("getAnswer возвращает 42 после завершения")
    fun testGetAnswerAfterComplete() {
        val computer = createSupercomputer()
        computer.startCalculation()

        computer.tick(timeUnits = 7_500_000)

        assertEquals(42, computer.getAnswer())
    }

    @Test
    @DisplayName("reset сбрасывает состояние")
    fun testReset() {
        val computer = createSupercomputer()
        computer.startCalculation()
        computer.tick(timeUnits = 1_000_000)

        computer.reset()

        assertEquals(0.0, computer.progressPercentage)
        assertFalse(computer.isCalculationComplete)
        assertFalse(computer.isCurrentlyCalculating)
        assertNull(computer.getAnswer())
    }

    @Test
    @DisplayName("askQuestion до начала вычислений")
    fun testAskQuestionBeforeStart() {
        val computer = createSupercomputer()

        val response = computer.askQuestion(question = "Test?")

        assertTrue(response.contains("еще не начал вычисления"))
    }

    @Test
    @DisplayName("askQuestion во время вычислений")
    fun testAskQuestionDuringCalculation() {
        val computer = createSupercomputer()
        computer.startCalculation()

        computer.tick(timeUnits = 750_000)
        val response = computer.askQuestion(question = "Test?")

        assertTrue(response.contains("все еще думаю"))
        assertTrue(response.contains("10%"))
    }

    @Test
    @DisplayName("askQuestion после завершения вычислений")
    fun testAskQuestionAfterComplete() {
        val computer = createSupercomputer()
        computer.startCalculation()
        computer.tick(timeUnits = 7_500_000)

        val response = computer.askQuestion(question = "В чём смысл жизни?")

        assertTrue(response.contains("В чём смысл жизни?"))
        assertTrue(response.contains("42"))
    }

    @Test
    @DisplayName("Полный цикл вычисления с проверкой прогресса")
    fun testFullCalculationCycle() {
        val computer = createSupercomputer()

        computer.startCalculation()
        assertTrue(computer.isCurrentlyCalculating)
        assertEquals(0.0, computer.progressPercentage)

        computer.tick(timeUnits = 1_875_000)
        assertEquals(25.0, computer.progressPercentage, 0.01)
        assertFalse(computer.isCalculationComplete)

        computer.tick(timeUnits = 1_875_000)
        assertEquals(50.0, computer.progressPercentage, 0.01)
        assertFalse(computer.isCalculationComplete)

        computer.tick(timeUnits = 1_875_000)
        assertEquals(75.0, computer.progressPercentage, 0.01)
        assertFalse(computer.isCalculationComplete)

        computer.tick(timeUnits = 1_875_000)
        assertEquals(100.0, computer.progressPercentage)
        assertTrue(computer.isCalculationComplete)
        assertFalse(computer.isCurrentlyCalculating)
        assertEquals(42, computer.getAnswer())
    }

    @Test
    @DisplayName("reset позволяет начать вычисление заново")
    fun testResetAndRestart() {
        val computer = createSupercomputer()
        computer.startCalculation()
        computer.tick(timeUnits = 1_000_000)

        computer.reset()
        val result = computer.startCalculation()

        assertTrue(result.contains("начинает глубокие размышления"))
        assertTrue(computer.isCurrentlyCalculating)
    }

    @Test
    @DisplayName("Множественные tick с малыми значениями")
    fun testMultipleSmallTicks() {
        val computer = createSupercomputer()
        computer.startCalculation()

        repeat(100) { computer.tick(timeUnits = 75_000) }

        assertTrue(computer.isCalculationComplete)
        assertEquals(42, computer.getAnswer())
    }

    @Test
    @DisplayName("tick() без параметра использует значение по умолчанию")
    fun testTickWithDefaultParameter() {
        val computer = createSupercomputer()
        computer.startCalculation()

        val progress = computer.tick()

        assertEquals(1, progress)
    }
}
