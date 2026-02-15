package com.arekalov.tpolab1.math

import com.arekalov.tpolab1.math.CosTestData.EPSILON
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.abs

/**
 * Параметризованные тесты для функции cos(x).
 * Используется JUnit 5 с @ParameterizedTest.
 * Все эталонные значения получены НЕ из java.lang.Math.
 */
@DisplayName("Тесты функции CosCalculator.cos(x)")
class CosCalculatorTest {

    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideAllTestPoints")
    @DisplayName("Все тестовые точки")
    fun testAllPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideSpecialPoints")
    @DisplayName("Особые точки (стандартные углы)")
    fun testSpecialPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideBoundaryPoints")
    @DisplayName("Граничные значения (малые и средние x)")
    fun testBoundaryPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideSymmetryPoints")
    @DisplayName("Тесты симметрии (отрицательные x)")
    fun testSymmetryPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("providePeriodicityPoints")
    @DisplayName("Тесты периодичности")
    fun testPeriodicityPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("provideArbitraryPoints")
    @DisplayName("Произвольные точки")
    fun testArbitraryPoints(x: Double, expected: Double) {
        val result = CosCalculator.cos(x)
        assertEquals(
            expected, result, EPSILON,
            "cos($x) должно быть $expected, получено $result"
        )
    }
    
    @ParameterizedTest(name = "cos(0) = 1 с epsilon = {0}")
    @MethodSource("provideDifferentEpsilons")
    @DisplayName("Тесты с различными значениями epsilon")
    fun testDifferentEpsilons(epsilon: Double) {
        val result = CosCalculator.cos(0.0, epsilon)
        assertTrue(
            abs(result - 1.0) <= epsilon,
            "cos(0) с epsilon=$epsilon должно быть близко к 1.0, получено $result"
        )
    }

    companion object {
        @JvmStatic
        fun provideAllTestPoints() = CosTestData.allPoints.toArguments()

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

        @JvmStatic
        fun provideDifferentEpsilons() = CosTestData.epsilonValues.map { Arguments.of(it) }
    }
}
