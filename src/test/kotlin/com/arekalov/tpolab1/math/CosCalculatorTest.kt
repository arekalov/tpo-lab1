package com.arekalov.tpolab1.math

import com.arekalov.tpolab1.math.CosTestData.EPSILON
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Параметризованные тесты для функции cos(x).
 * Используется JUnit 5 с @ParameterizedTest.
 * Все эталонные значения получены НЕ из java.lang.Math.
 */
@DisplayName("Тесты функции CosCalculator.cos(x)")
class CosCalculatorTest {

    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideSpecialPoints")
    @DisplayName("Ключевые точки периода функции")
    fun testSpecialPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideBoundaryPoints")
    @DisplayName("Граничные и промежуточные значения")
    fun testBoundaryPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideSymmetryPoints")
    @DisplayName("Проверка чётности: cos(-x) = cos(x)")
    fun testSymmetryPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("providePeriodicityPoints")
    @DisplayName("Проверка периодичности: cos(x) = cos(x + 2π)")
    fun testPeriodicityPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideArbitraryPoints")
    @DisplayName("Произвольные значения")
    fun testArbitraryPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }

    companion object {
        @JvmStatic
        fun provideSpecialPoints() = CosTestData.specialPoints.toArguments()

        @JvmStatic
        fun provideBoundaryPoints() = CosTestData.boundaryPoints.toArguments()

        @JvmStatic
        fun provideSymmetryPoints() = CosTestData.symmetryPoints.toArguments()

        @JvmStatic
        fun providePeriodicityPoints() = CosTestData.periodicityPoints.toArguments()

        @JvmStatic
        fun provideArbitraryPoints() = CosTestData.arbitraryPoints.toArguments()
    }
}
