package com.arekalov.tpolab1.math

import kotlin.math.abs

/**
 * Калькулятор для вычисления косинуса через разложение в ряд Тейлора.
 *
 * Использует формулу: cos(x) = 1 - x²/2! + x⁴/4! - x⁶/6! + x⁸/8! - ...
 */
object CosCalculator {
    
    /**
     * Вычисляет cos(x) через ряд Тейлора.
     *
     * @param x аргумент в радианах
     * @param epsilon точность вычисления (по умолчанию 1e-10)
     * @return значение cos(x)
     */
    fun cos(x: Double, epsilon: Double = 1e-10): Double {
        var term = 1.0  // Первый член ряда
        var sum = term
        var n = 1
        
        // Продолжаем суммирование пока член ряда больше требуемой точности
        while (abs(term) > epsilon) {
            term *= -x * x / ((2 * n - 1) * (2 * n))
            sum += term
            n++
        }
        
        return sum
    }
}
