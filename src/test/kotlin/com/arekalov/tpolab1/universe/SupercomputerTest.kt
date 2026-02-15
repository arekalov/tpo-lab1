package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Тесты Суперкомпьютера")
class SupercomputerTest {

    private lateinit var computer: Supercomputer

    @BeforeEach
    fun setup() {
        computer = Supercomputer("Deep Thought")
    }

    @Test
    @DisplayName("Создание суперкомпьютера")
    fun testCreateSupercomputer() {
        assertEquals("Deep Thought", computer.name)
        assertFalse(computer.isCurrentlyCalculating())
        assertFalse(computer.isCalculationComplete())
    }

    @Test
    @DisplayName("Запуск вычисления")
    fun testStartCalculation() {
        val result = computer.startCalculation()

        assertTrue(result.contains("Deep Thought"))
        assertTrue(computer.isCurrentlyCalculating())
    }

    @Test
    @DisplayName("Повторный запуск вычисления")
    fun testRestartCalculation() {
        computer.startCalculation()
        val result = computer.startCalculation()

        assertTrue(result.contains("уже идет"))
    }

    @Test
    @DisplayName("Прогресс вычисления")
    fun testCalculationProgress() {
        computer.startCalculation()

        val initialProgress = computer.getProgressPercentage()
        computer.tick(1000)
        val afterProgress = computer.getProgressPercentage()

        assertTrue(afterProgress > initialProgress)
    }

    @Test
    @DisplayName("Завершение вычисления")
    fun testCalculationComplete() {
        computer.startCalculation()
        computer.tick(10_000_000) // Большой скачок времени

        assertTrue(computer.isCalculationComplete())
        assertFalse(computer.isCurrentlyCalculating())
        assertEquals(42, computer.getAnswer())
    }

    @Test
    @DisplayName("Получение ответа до завершения")
    fun testGetAnswerBeforeComplete() {
        computer.startCalculation()
        computer.tick(100)

        assertNull(computer.getAnswer())
    }

    @Test
    @DisplayName("Вопрос к суперкомпьютеру до запуска")
    fun testAskQuestionBeforeStart() {
        val response = computer.askQuestion("What is the meaning of life?")

        assertTrue(response.contains("не начал"))
    }

    @Test
    @DisplayName("Вопрос к суперкомпьютеру во время вычисления")
    fun testAskQuestionDuringCalculation() {
        computer.startCalculation()
        computer.tick(100)

        val response = computer.askQuestion("What is the meaning of life?")

        assertTrue(response.contains("думаю"))
        assertTrue(response.contains("Прогресс"))
    }

    @Test
    @DisplayName("Вопрос к суперкомпьютеру после завершения")
    fun testAskQuestionAfterComplete() {
        computer.startCalculation()
        computer.tick(10_000_000)

        val response = computer.askQuestion("What is the meaning of life?")

        assertTrue(response.contains("42"))
    }

    @Test
    @DisplayName("Сброс суперкомпьютера")
    fun testResetSupercomputer() {
        computer.startCalculation()
        computer.tick(1000)
        computer.reset()

        assertFalse(computer.isCurrentlyCalculating())
        assertFalse(computer.isCalculationComplete())
        assertNull(computer.getAnswer())
        assertEquals(0.0, computer.getProgressPercentage(), 0.01)
    }

    @Test
    @DisplayName("Прогресс в процентах")
    fun testProgressPercentage() {
        computer.startCalculation()

        assertEquals(0.0, computer.getProgressPercentage(), 0.01)

        computer.tick(3_750_000) // 50%
        assertTrue(computer.getProgressPercentage() > 49.0)
        assertTrue(computer.getProgressPercentage() < 51.0)
    }

    @Test
    @DisplayName("Tick без активного вычисления")
    fun testTickWithoutCalculation() {
        val progress = computer.tick(100)

        assertEquals(0, progress)
        assertFalse(computer.isCurrentlyCalculating())
    }
}
