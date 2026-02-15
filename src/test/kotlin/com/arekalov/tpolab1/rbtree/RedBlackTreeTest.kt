package com.arekalov.tpolab1.rbtree

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Тесты RedBlackTree")
@Suppress("ForbiddenComment")
class RedBlackTreeTest {

    private lateinit var tree: RedBlackTree

    @BeforeEach
    fun setup() {
        tree = RedBlackTree()
    }

    @Test
    @DisplayName("Новое дерево должно быть пустым")
    fun testNewTreeIsEmpty() {
        assertTrue(tree.isEmpty())
        assertEquals(0, tree.size())
    }

    @Test
    @DisplayName("Дерево с одним элементом не пустое")
    fun testTreeWithOneElementIsNotEmpty() {
        tree.insert(1)

        assertTrue(!tree.isEmpty())
        assertEquals(1, tree.size())
    }

    @Test
    @DisplayName("Вставка и поиск элементов")
    fun testInsertAndSearch() {
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)

        assertTrue(tree.search(10))
        assertTrue(tree.search(5))
        assertTrue(tree.search(15))
        assertFalse(tree.search(20))
    }

    @Test
    @DisplayName("Вставка в возрастающем порядке")
    fun testInsertAscending() {
        (1..10).forEach { tree.insert(it) }

        assertEquals(10, tree.size())
        assertEquals((1..10).toList(), tree.toList())
    }

    @Test
    @DisplayName("Вставка в убывающем порядке")
    fun testInsertDescending() {
        (10 downTo 1).forEach { tree.insert(it) }

        assertEquals(10, tree.size())
        assertEquals((1..10).toList(), tree.toList())
    }

    @Test
    @DisplayName("Вставка дубликатов не увеличивает размер")
    fun testInsertDuplicates() {
        tree.insert(10)
        tree.insert(10)
        tree.insert(10)

        assertEquals(1, tree.size())
        assertTrue(tree.search(10))
    }

    @Test
    @DisplayName("Удаление единственного элемента")
    fun testDeleteSingleElement() {
        tree.insert(10)
        tree.delete(10)

        assertTrue(tree.isEmpty())
        assertFalse(tree.search(10))
    }

    @Test
    @DisplayName("Удаление листового узла")
    fun testDeleteLeafNode() {
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        tree.insert(3)
        tree.insert(7)

        tree.delete(3)

        assertEquals(4, tree.size())
        assertFalse(tree.search(3))
        assertEquals(listOf(5, 7, 10, 15), tree.toList())
    }

    @Test
    @DisplayName("Удаление узла с одним ребенком")
    fun testDeleteNodeWithOneChild() {
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        tree.insert(3)

        tree.delete(5)

        assertEquals(3, tree.size())
        assertFalse(tree.search(5))
        assertTrue(tree.search(3))
    }

    @Test
    @DisplayName("Удаление узла с двумя детьми")
    fun testDeleteNodeWithTwoChildren() {
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)
        tree.insert(3)
        tree.insert(7)
        tree.insert(13)
        tree.insert(17)

        tree.delete(5)

        assertEquals(6, tree.size())
        assertFalse(tree.search(5))
        assertEquals(listOf(3, 7, 10, 13, 15, 17), tree.toList())
    }

    @Test
    @DisplayName("Удаление корня")
    fun testDeleteRoot() {
        tree.insert(10)
        tree.insert(5)
        tree.insert(15)

        tree.delete(10)

        assertEquals(2, tree.size())
        assertFalse(tree.search(10))
        assertEquals(listOf(5, 15), tree.toList())
    }

    @Test
    @DisplayName("Удаление всех элементов по одному")
    fun testDeleteAllElements() {
        val values = (1..10).toList()
        values.forEach { tree.insert(it) }

        values.forEach { tree.delete(it) }

        assertTrue(tree.isEmpty())
    }

    @Test
    @DisplayName("Сложная последовательность операций")
    fun testComplexSequence() {
        tree.insert(50)
        tree.insert(25)
        tree.insert(75)
        tree.insert(10)
        tree.insert(30)
        tree.insert(60)
        tree.insert(80)

        assertEquals(7, tree.size())

        tree.delete(25)
        assertEquals(6, tree.size())
        assertFalse(tree.search(25))

        tree.insert(25)
        assertEquals(7, tree.size())
        assertTrue(tree.search(25))

        tree.delete(50)
        assertEquals(6, tree.size())
        assertFalse(tree.search(50))

        val expected = listOf(10, 25, 30, 60, 75, 80)
        assertEquals(expected, tree.toList())
    }

    @Test
    @DisplayName("Стресс тест: массовые операции")
    fun testStressMassiveOperations() {
        val values = (1..100).toList()
        values.forEach { tree.insert(it) }

        values.filter { it % 3 == 0 }.forEach { tree.delete(it) }

        val remaining = values.filter { it % 3 != 0 }
        assertEquals(remaining.size, tree.size())
        assertEquals(remaining, tree.toList())
    }

    @Test
    @DisplayName("Балансировка: сложные случаи удаления")
    fun testBalancingComplexDeletions() {
        val values = listOf(
            50, 25, 75, 12, 37, 62, 87,
            6, 18, 31, 43, 56, 68, 81, 93,
            3, 9, 15, 21, 28, 34, 40, 46,
            53, 59, 65, 71, 78, 84, 90, 96,
        )
        values.forEach { tree.insert(it) }

        listOf(3, 96, 6, 93, 9, 90, 12, 87).forEach { tree.delete(it) }

        val result = tree.toList()
        assertEquals(result.sorted(), result)
    }

    @Test
    @DisplayName("Последовательные вставки для балансировки")
    fun testSequentialInsertsForBalancing() {
        val sequence = listOf(
            40, 20, 60, 10, 30, 50, 70,
            5, 15, 25, 35, 45, 55, 65, 75,
            3, 7, 12, 17, 22, 27, 32, 37,
        )
        sequence.forEach { tree.insert(it) }

        assertEquals(23, tree.size())
        assertEquals(sequence.sorted(), tree.toList())
    }

    @Test
    @DisplayName("Удаление минимального элемента много раз")
    fun testDeleteMinimumRepeatedly() {
        val values = (1..10).toList()
        values.forEach { tree.insert(it) }

        values.forEach { tree.delete(it) }

        assertTrue(tree.isEmpty())
    }

    @Test
    @DisplayName("Удаление максимального элемента много раз")
    fun testDeleteMaximumRepeatedly() {
        val values = (1..10).toList()
        values.forEach { tree.insert(it) }

        values.reversed().forEach { tree.delete(it) }

        assertTrue(tree.isEmpty())
    }

    @Test
    @DisplayName("Удаление несуществующего элемента")
    fun testDelete() {
        tree.insert(1)

        tree.delete(10)

        assertTrue(tree.size() == 1)
    }

    @Test
    @DisplayName("Каскадные удаления")
    fun testCascadingDeletions() {
        listOf(50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85).forEach { tree.insert(it) }

        tree.delete(10)
        tree.delete(25)
        tree.delete(20)

        assertEquals(12, tree.size())
        val result = tree.toList()
        assertEquals(result.sorted(), result)
    }

    @Test
    @DisplayName("Большое дерево с удалениями слева")
    fun testLargeTreeWithLeftDeletions() {
        val values = (1..63).toList()
        values.forEach { tree.insert(it) }

        listOf(1, 2, 3, 4, 5, 6, 7, 8).forEach { tree.delete(it) }

        assertEquals(55, tree.size())
        val result = tree.toList()
        assertEquals(result.sorted(), result)
    }

    @Test
    @DisplayName("Большое дерево с удалениями справа")
    fun testLargeTreeWithRightDeletions() {
        val values = (1..63).toList()
        values.forEach { tree.insert(it) }

        listOf(63, 62, 61, 60, 59, 58, 57, 56).forEach { tree.delete(it) }

        assertEquals(55, tree.size())
        val result = tree.toList()
        assertEquals(result.sorted(), result)
    }

    @Test
    @DisplayName("Балансировка при удалении с левой стороны")
    fun testBalancingWithLeftSideDeletions() {
        listOf(20, 10, 30, 5, 15, 25, 35, 3, 7, 12, 17, 22, 27, 33, 40).forEach { tree.insert(it) }

        tree.delete(3)
        tree.delete(7)
        tree.delete(5)

        assertEquals(12, tree.size())
        val result = tree.toList()
        assertEquals(result.sorted(), result)
    }

    @Test
    @DisplayName("Балансировка при удалении с правой стороны")
    fun testBalancingWithRightSideDeletions() {
        listOf(50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 90).forEach { tree.insert(it) }

        tree.delete(90)
        tree.delete(75)
        tree.delete(80)

        assertEquals(12, tree.size())
        val result = tree.toList()
        assertEquals(result.sorted(), result)
    }

    @Test
    @DisplayName("Массовые удаления для покрытия различных случаев")
    fun testMassiveDeletionsForCoverage() {
        val values = (10..100 step 5).toList()
        values.forEach { tree.insert(it) }

        listOf(10, 100, 15, 95, 20, 90).forEach { tree.delete(it) }

        val result = tree.toList()
        assertEquals(result.sorted(), result)
    }

    @Test
    @DisplayName("Отрицательные числа")
    fun testNegativeNumbers() {
        listOf(-10, -5, 0, 5, 10).forEach { tree.insert(it) }

        assertEquals(5, tree.size())
        assertEquals(listOf(-10, -5, 0, 5, 10), tree.toList())
    }

    @Test
    @DisplayName("Минимальное и максимальное значение Int")
    fun testIntBoundaries() {
        tree.insert(Int.MAX_VALUE)
        tree.insert(Int.MIN_VALUE)
        tree.insert(0)

        assertEquals(3, tree.size())
        assertTrue(tree.search(Int.MAX_VALUE))
        assertTrue(tree.search(Int.MIN_VALUE))
    }

    @Test
    @DisplayName("Брат черный, левый ребенок красный, правый черный")
    fun testDeleteLeftCase3() {
        val insertions = listOf(41, 19, 8, 39, 21, 97, 109, 82, 58, 72)
        val deletions = listOf(39, 8, 19, 41, 82)

        insertions.forEach { tree.insert(it) }
        deletions.forEach { tree.delete(it) }

        assertEquals(listOf(21, 58, 72, 97, 109), tree.toList())
        assertEquals(5, tree.size())
    }

    @Test
    @DisplayName("Брат красный (зеркальный)")
    fun testDeleteRightCase1() {
        val insertions = listOf(53, 68, 77, 102, 27, 31, 119, 101, 94, 76, 83, 54, 95, 3, 1, 52, 26, 112, 104, 92)
        val deletions = listOf(68, 31, 101, 104, 95, 53, 102, 94, 119, 26)

        insertions.forEach { tree.insert(it) }
        deletions.forEach { tree.delete(it) }

        assertEquals(listOf(1, 3, 27, 52, 54, 76, 77, 83, 92, 112), tree.toList())
        assertEquals(10, tree.size())
    }

    @Test
    @DisplayName("Брат черный, правый ребенок красный, левый черный (зеркальный)")
    fun testDeleteRightCase3() {
        val insertions = listOf(57, 109, 103, 116, 107, 8, 99, 87, 94, 118, 28, 33, 21, 55)
        val deletions = listOf(99, 107, 94, 21, 8, 118, 28, 87, 55, 109, 57)

        insertions.forEach { tree.insert(it) }
        deletions.forEach { tree.delete(it) }

        assertEquals(listOf(33, 103, 116), tree.toList())
        assertEquals(3, tree.size())
    }

    // Синтетические тесты
    @Test
    @DisplayName("Проверка enum Color")
    fun testColorEnum() {
        val colors = Color.entries
        assertEquals(2, colors.size)
        assertTrue(colors.contains(Color.RED))
        assertTrue(colors.contains(Color.BLACK))
    }
}
