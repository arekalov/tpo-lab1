package com.arekalov.tpolab1.math

import org.junit.jupiter.params.provider.Arguments
import kotlin.math.sqrt

/**
 * Константы и тестовые данные для проверки функции cos(x).
 * Все значения получены из независимых источников (НЕ java.lang.Math).
 */
object CosTestData {
    const val PI = 3.14159265358979323846264338327950288419716939937510
    const val EPSILON = 1e-10
    val SQRT_2_DIV_2 = sqrt(2.0) / 2.0 // ≈ 0.707106781186548

    /**
     * Класс для хранения тестовой точки
     */
    data class TestPoint(
        val x: Double,
        val expected: Double,
    )

    /**
     * Ключевые точки периода функции
     */
    val specialPoints = listOf(
        TestPoint(0.0, 1.0), // №1: Начало периода, максимум
        TestPoint(PI / 4, SQRT_2_DIV_2), // №2: Угол 45°
        TestPoint(PI / 2, 0.0), // №3: Точка смены знака
        TestPoint(PI, -1.0), // №4: Половина периода, минимум
        TestPoint(3 * PI / 2, 0.0), // №5: Точка смены знака
        TestPoint(2 * PI, 1.0), // №6: Полный период
    )

    /**
     * Граничные и промежуточные значения
     */
    val boundaryPoints = listOf(
        TestPoint(0.01, 0.999950000416665), // №7: Очень малый угол
        TestPoint(1.0, 0.540302305868140), // №8: Среднее значение
    )

    /**
     * Проверка чётности функции: cos(-x) = cos(x)
     */
    val symmetryPoints = listOf(
        TestPoint(-PI / 4, SQRT_2_DIV_2), // №9: Симметрично углу 45°
        TestPoint(-1.0, 0.540302305868140), // №10: Симметрично x=1.0
    )

    /**
     * Проверка периодичности: cos(x) = cos(x + 2π)
     */
    val periodicityPoints = listOf(
        TestPoint(PI / 4 + 2 * PI, SQRT_2_DIV_2), // №11: Периодичность
    )

    /**
     * Произвольные значения
     */
    val arbitraryPoints = listOf(
        TestPoint(0.2, 0.980066577841242),
        TestPoint(0.5, 0.877582561890373),
        TestPoint(0.7, 0.764842187284489),
        TestPoint(1.5, 0.0707372016677029),
        TestPoint(2.0, -0.416146836547142),
        TestPoint(2.5, -0.801143615546934),
    )
}

fun List<CosTestData.TestPoint>.toArguments(): List<Arguments> {
    return this.map { Arguments.of(it.x, it.expected) }
}
