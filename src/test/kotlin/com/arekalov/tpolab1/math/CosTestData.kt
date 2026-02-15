package com.arekalov.tpolab1.math

import org.junit.jupiter.params.provider.Arguments
import kotlin.math.sqrt

/**
 * Константы и тестовые данные для проверки функции cos(x).
 * Все значения получены из независимых источников (НЕ java.lang.Math).
 */
object CosTestData {
    
    // Константа π (первые 50 знаков из математического справочника)
    const val PI = 3.14159265358979323846264338327950288419716939937510
    
    // Допустимая погрешность
    const val EPSILON = 1e-10
    
    // Вспомогательные константы для точных значений
    val SQRT_2_DIV_2 = sqrt(2.0) / 2.0  // ≈ 0.707106781186548
    val SQRT_3_DIV_2 = sqrt(3.0) / 2.0  // ≈ 0.866025403784439
    
    /**
     * Класс для хранения тестовой точки
     */
    data class TestPoint(
        val x: Double,
        val expected: Double
    )
    
    /**
     * Особые точки - стандартные углы из тригонометрии
     */
    val specialPoints = listOf(
        TestPoint(0.0, 1.0),
        TestPoint(PI / 4, SQRT_2_DIV_2),
        TestPoint(PI / 2, 0.0),
        TestPoint(PI, -1.0),
        TestPoint(3 * PI / 2, 0.0),
        TestPoint(2 * PI, 1.0)
    )
    
    /**
     * Граничные значения - малые и средние значения
     */
    val boundaryPoints = listOf(
        TestPoint(0.01, 0.999950000416665),
        TestPoint(1.0, 0.540302305868140)
    )
    
    /**
     * Тесты симметрии - проверка чётности cos(-x) = cos(x)
     */
    val symmetryPoints = listOf(
        TestPoint(-PI / 4, SQRT_2_DIV_2),
        TestPoint(-1.0, 0.540302305868140)
    )
    
    /**
     * Тесты периодичности - проверка cos(x) = cos(x + 2πn)
     */
    val periodicityPoints = listOf(
        TestPoint(PI / 4 + 2 * PI, SQRT_2_DIV_2)
    )
    
    /**
     * Произвольная точка для дополнительного покрытия
     */
    val arbitraryPoints = listOf(
        TestPoint(0.5, 0.877582561890373)
    )
    
    /**
     * Все тестовые точки объединённые
     */
    val allPoints = specialPoints + boundaryPoints + symmetryPoints + 
                    periodicityPoints + arbitraryPoints
    
    /**
     * Различные значения epsilon для тестов точности
     */
    val epsilonValues = listOf(1e-5, 1e-10)
}

fun List<CosTestData.TestPoint>.toArguments(): List<Arguments> {
    return this.map { Arguments.of(it.x, it.expected) }
}
