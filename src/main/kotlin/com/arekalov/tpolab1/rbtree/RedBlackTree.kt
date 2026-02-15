@file:Suppress("ForbiddenComment", "TooManyFunctions")

package com.arekalov.tpolab1.rbtree

/**
 * Цвет узла красно-черного дерева
 */
enum class Color {
    RED,
    BLACK,
}

/**
 * Узел красно-черного дерева
 */
data class Node(
    val value: Int,
    var color: Color = Color.RED,
    var left: Node? = null,
    var right: Node? = null,
    var parent: Node? = null,
)

/**
 * Красно-черное дерево для целых чисел
 *
 * Свойства:
 * 1. Каждый узел либо красный, либо черный
 * 2. Корень всегда черный
 * 3. Все листья (NULL) черные
 * 4. Красный узел не может иметь красных детей
 * 5. Все пути от узла до листьев содержат одинаковое количество черных узлов
 */
class RedBlackTree {
    private var root: Node? = null

    /**
     * Вставка элемента в дерево
     */
    fun insert(value: Int) {
        val newNode = Node(value)

        val currentRoot = root
        if (currentRoot == null) {
            root = newNode
            newNode.color = Color.BLACK // Корень всегда черный
            return
        }

        var current: Node? = currentRoot
        var parent: Node? = null

        while (current != null) {
            parent = current
            current = when {
                value < current.value -> current.left
                value > current.value -> current.right
                else -> return // Значение уже существует
            }
        }

        newNode.parent = parent
        parent?.let {
            if (value < it.value) {
                it.left = newNode
            } else {
                it.right = newNode
            }
        }

        // Балансировка после вставки
        balanceAfterInsert(newNode)
    }

    /**
     * Балансировка после вставки узла
     */
    @Suppress("CyclomaticComplexMethod", "NestedBlockDepth")
    private fun balanceAfterInsert(node: Node) {
        var current = node

        while (current != root && current.parent?.color == Color.RED) {
            val parent = current.parent ?: break
            val grandparent = parent.parent ?: break

            if (parent == grandparent.left) {
                val uncle = grandparent.right

                // Случай 1: Дядя красный
                if (uncle?.color == Color.RED) {
                    parent.color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandparent.color = Color.RED
                    current = grandparent
                } else {
                    // Случай 2: Дядя черный и узел - правый ребенок
                    if (current == parent.right) {
                        current = parent
                        rotateLeft(current)
                    }

                    // Случай 3: Дядя черный и узел - левый ребенок
                    val currentParent = current.parent
                    if (currentParent != null) {
                        currentParent.color = Color.BLACK
                        currentParent.parent?.let {
                            it.color = Color.RED
                            rotateRight(it)
                        }
                    }
                }
            } else {
                val uncle = grandparent.left

                // Случай 1: Дядя красный (зеркальный)
                if (uncle?.color == Color.RED) {
                    parent.color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandparent.color = Color.RED
                    current = grandparent
                } else {
                    // Случай 2: Дядя черный и узел - левый ребенок (зеркальный)
                    if (current == parent.left) {
                        current = parent
                        rotateRight(current)
                    }

                    // Случай 3: Дядя черный и узел - правый ребенок (зеркальный)
                    val currentParent = current.parent
                    if (currentParent != null) {
                        currentParent.color = Color.BLACK
                        currentParent.parent?.let {
                            it.color = Color.RED
                            rotateLeft(it)
                        }
                    }
                }
            }
        }

        root?.color = Color.BLACK
    }

    /**
     * Левый поворот
     */
    private fun rotateLeft(node: Node) {
        val rightChild = node.right ?: return

        node.right = rightChild.left
        rightChild.left?.parent = node

        rightChild.parent = node.parent

        val nodeParent = node.parent
        when {
            nodeParent == null -> root = rightChild
            node == nodeParent.left -> nodeParent.left = rightChild
            else -> nodeParent.right = rightChild
        }

        rightChild.left = node
        node.parent = rightChild
    }

    /**
     * Правый поворот
     */
    private fun rotateRight(node: Node) {
        val leftChild = node.left ?: return

        node.left = leftChild.right
        leftChild.right?.parent = node

        leftChild.parent = node.parent

        val nodeParent = node.parent
        when {
            nodeParent == null -> root = leftChild
            node == nodeParent.right -> nodeParent.right = leftChild
            else -> nodeParent.left = leftChild
        }

        leftChild.right = node
        node.parent = leftChild
    }

    /**
     * Поиск элемента в дереве
     */
    fun search(value: Int): Boolean {
        var current = root

        while (current != null) {
            current = when {
                value < current.value -> current.left
                value > current.value -> current.right
                else -> return true
            }
        }

        return false
    }

    /**
     * Удаление элемента из дерева
     */
    fun delete(value: Int) {
        val nodeToDelete = findNode(value) ?: return

        var replacementNode: Node?
        var nodeToFix: Node?
        val originalColor = nodeToDelete.color

        when {
            nodeToDelete.left == null -> {
                replacementNode = nodeToDelete.right
                transplant(nodeToDelete, nodeToDelete.right)
                nodeToFix = replacementNode
            }
            nodeToDelete.right == null -> {
                replacementNode = nodeToDelete.left
                transplant(nodeToDelete, nodeToDelete.left)
                nodeToFix = replacementNode
            }
            else -> {
                replacementNode = minimum(nodeToDelete.right)
                if (replacementNode == null) return

                val replacementOriginalColor = replacementNode.color
                nodeToFix = replacementNode.right

                if (replacementNode.parent == nodeToDelete) {
                    nodeToFix?.parent = replacementNode
                } else {
                    transplant(replacementNode, replacementNode.right)
                    replacementNode.right = nodeToDelete.right
                    replacementNode.right?.parent = replacementNode
                }

                transplant(nodeToDelete, replacementNode)
                replacementNode.left = nodeToDelete.left
                replacementNode.left?.parent = replacementNode
                replacementNode.color = nodeToDelete.color

                if (replacementOriginalColor == Color.BLACK) {
                    nodeToFix?.let { balanceAfterDelete(it) }
                }
                return
            }
        }

        if (originalColor == Color.BLACK) {
            nodeToFix?.let { balanceAfterDelete(it) }
        }
    }

    /**
     * Поиск узла с заданным значением
     */
    private fun findNode(value: Int): Node? {
        var current = root

        while (current != null) {
            current = when {
                value < current.value -> current.left
                value > current.value -> current.right
                else -> return current
            }
        }

        return null
    }

    /**
     * Поиск минимального узла в поддереве
     */
    private fun minimum(node: Node?): Node? {
        var current = node
        while (current?.left != null) {
            current = current.left
        }
        return current
    }

    /**
     * Замена одного поддерева другим
     */
    private fun transplant(oldNode: Node, newNode: Node?) {
        val oldNodeParent = oldNode.parent
        when {
            oldNodeParent == null -> root = newNode
            oldNode == oldNodeParent.left -> oldNodeParent.left = newNode
            else -> oldNodeParent.right = newNode
        }
        newNode?.parent = oldNodeParent
    }

    /**
     * Балансировка после удаления узла
     */
    @Suppress("CyclomaticComplexMethod", "NestedBlockDepth", "LongMethod")
    private fun balanceAfterDelete(node: Node) {
        var current = node

        while (current != root && current.color == Color.BLACK) {
            val currentParent = current.parent ?: break

            if (current == currentParent.left) {
                var sibling = currentParent.right

                // Случай 1: Брат красный
                if (sibling?.color == Color.RED) {
                    sibling.color = Color.BLACK
                    currentParent.color = Color.RED
                    rotateLeft(currentParent)
                    sibling = currentParent.right
                }

                // Случай 2: Брат черный и оба его ребенка черные
                if (sibling?.left?.color == Color.BLACK && sibling.right?.color == Color.BLACK) {
                    sibling.color = Color.RED
                    current = currentParent
                } else {
                    sibling?.let { s ->
                        // Случай 3: Брат черный, левый ребенок красный, правый черный
                        if (s.right?.color == Color.BLACK) {
                            s.left?.color = Color.BLACK
                            s.color = Color.RED
                            rotateRight(s)
                            sibling = currentParent.right
                        }

                        // Случай 4: Брат черный, правый ребенок красный
                        sibling?.let { finalSibling ->
                            finalSibling.color = currentParent.color
                            currentParent.color = Color.BLACK
                            finalSibling.right?.color = Color.BLACK
                            rotateLeft(currentParent)
                            root?.let { current = it }
                        }
                    }
                }
            } else {
                var sibling = currentParent.left

                // Случай 1: Брат красный (зеркальный)
                if (sibling?.color == Color.RED) {
                    sibling.color = Color.BLACK
                    currentParent.color = Color.RED
                    rotateRight(currentParent)
                    sibling = currentParent.left
                }

                // Случай 2: Брат черный и оба его ребенка черные (зеркальный)
                if (sibling?.right?.color == Color.BLACK && sibling.left?.color == Color.BLACK) {
                    sibling.color = Color.RED
                    current = currentParent
                } else {
                    sibling?.let { s ->
                        // Случай 3: Брат черный, правый ребенок красный, левый черный (зеркальный)
                        if (s.left?.color == Color.BLACK) {
                            s.right?.color = Color.BLACK
                            s.color = Color.RED
                            rotateLeft(s)
                            sibling = currentParent.left
                        }

                        // Случай 4: Брат черный, левый ребенок красный (зеркальный)
                        sibling?.let { finalSibling ->
                            finalSibling.color = currentParent.color
                            currentParent.color = Color.BLACK
                            finalSibling.left?.color = Color.BLACK
                            rotateRight(currentParent)
                            root?.let { current = it }
                        }
                    }
                }
            }
        }

        current.color = Color.BLACK
    }

    /**
     * Получить все элементы в отсортированном порядке (In-Order обход)
     */
    fun toList(): List<Int> {
        val result = mutableListOf<Int>()
        inOrderTraversal(root, result)
        return result
    }

    private fun inOrderTraversal(node: Node?, result: MutableList<Int>) {
        if (node == null) return

        inOrderTraversal(node.left, result)
        result.add(node.value)
        inOrderTraversal(node.right, result)
    }

    /**
     * Проверить, пустое ли дерево
     */
    fun isEmpty(): Boolean = root == null

    /**
     * Получить размер дерева
     */
    fun size(): Int = countNodes(root)

    private fun countNodes(node: Node?): Int {
        if (node == null) return 0
        return 1 + countNodes(node.left) + countNodes(node.right)
    }
}
