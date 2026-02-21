package com.arekalov.tpolab1.universe

/**
 * Дискуссия о смысле жизни
 */
class Debate(
    val topic: String = "Смысл жизни, вселенной и всего такого",
) {
    val participants: MutableList<Habitant> = mutableListOf()

    var isActive: Boolean = false
        private set

    var roundsCompleted: Int = 0
        private set

    val participantCount: Int
        get() = participants.size

    val needsDrasticMeasures: Boolean
        get() = isActive && participants.any { it.isTiredOfDebates }

    val favoriteActivitiesStats: Map<TypeOfActivity, Int>
        get() = participants.groupBy { it.favoriteActivity }
            .mapValues { it.value.size }

    /**
     * Добавить участника дискуссии
     */
    fun addParticipant(habitant: Habitant): String {
        if (habitant in participants) {
            return "${habitant.name} уже участвует в дискуссии"
        }
        participants.add(habitant)
        return "${habitant.name} присоединился к дискуссии о теме: $topic"
    }

    /**
     * Начать дискуссию
     */
    fun start(): String {
        if (participants.isEmpty()) {
            return "Нет участников для дискуссии"
        }
        if (isActive) {
            return "Дискуссия уже идет"
        }

        isActive = true
        return "Дискуссия о теме '$topic' началась с $participantCount участниками"
    }

    /**
     * Провести один раунд дискуссии
     */
    fun conductRound(): String {
        if (!isActive) {
            return "Дискуссия не активна"
        }

        roundsCompleted++

        // Каждый раунд повышает уровень разочарования и знаний
        participants.forEach {
            it.frustrationLevel += 5
            it.knowledgeLevel += 1
        }

        return "Раунд $roundsCompleted завершен. Участники все больше устают..."
    }

    /**
     * Завершить дискуссию
     */
    fun end(): String {
        if (!isActive) {
            return "Дискуссия уже завершена"
        }

        isActive = false
        val averageFrustration = if (participants.isNotEmpty()) {
            participants.map { it.frustrationLevel }.average()
        } else {
            0.0
        }

        return "Дискуссия завершена после $roundsCompleted раундов. " +
            "Средняя степень разочарования: ${"%.2f".format(averageFrustration)}"
    }
}
