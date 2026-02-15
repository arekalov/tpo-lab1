package com.arekalov.tpolab1.universe

/**
 * Дискуссия о смысле жизни
 */
class Debate(
    val topic: String = "Смысл жизни, вселенной и всего такого",
) {
    private val _participants: MutableList<Habitant> = mutableListOf()
    val participants: List<Habitant>
        get() = _participants

    var isActive: Boolean = false
        private set

    var roundsCompleted: Int = 0
        private set

    var participantCount: Int = 0
        private set

    /**
     * Добавить участника дискуссии
     */
    fun addParticipant(habitant: Habitant): String {
        if (habitant in _participants) {
            return "${habitant.name} уже участвует в дискуссии"
        }
        _participants.add(habitant)
        participantCount = _participants.size
        return "${habitant.name} присоединился к дискуссии о теме: $topic"
    }

    /**
     * Начать дискуссию
     */
    fun start(): String {
        if (_participants.isEmpty()) {
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
        _participants.forEach {
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
        val averageFrustration = if (_participants.isNotEmpty()) {
            _participants.map { it.frustrationLevel }.average()
        } else {
            0.0
        }

        return "Дискуссия завершена после $roundsCompleted раундов. " +
            "Средняя степень разочарования: ${"%.2f".format(averageFrustration)}"
    }

    /**
     * Проверить, нужно ли принимать кардинальные меры
     */
    fun needsDrasticMeasures(): Boolean =
        isActive && _participants.any { it.isTiredOfDebates() }

    /**
     * Получить статистику любимых занятий участников
     */
    fun getFavoriteActivitiesStats(): Map<TypeOfActivity, Int> =
        _participants.groupBy { it.favoriteActivity }
            .mapValues { it.value.size }
}
