package com.arekalov.tpolab1.universe

/**
 * Планета во вселенной
 */
data class Planet(
    val name: String,
    val mass: Double,
    val inhabitants: MutableList<Habitant> = mutableListOf(),
) {
    val inhabitantCount: Int
        get() = inhabitants.size

    val inhabitedRaces: List<Race>
        get() = inhabitants.map { it.race }.distinct()

    val hasFrustratedInhabitants: Boolean
        get() = inhabitants.any { it.isTiredOfDebates }

    val averageFrustrationLevel: Double
        get() = if (inhabitants.isEmpty()) {
            0.0
        } else {
            inhabitants.map { it.frustrationLevel }.average()
        }

    /**
     * Добавить обитателя на планету
     */
    fun addInhabitant(habitant: Habitant) {
        inhabitants.add(habitant)
    }

    /**
     * Получить количество обитателей определенной расы
     */
    fun getInhabitantCountByRace(race: Race): Int =
        inhabitants.count { it.race == race }

    /**
     * Провести турнир по брокианскому ультра-крикету
     */
    fun conductUltraCricketTournament(): String {
        if (inhabitants.size < 2) {
            return "Недостаточно участников для турнира"
        }

        // Подсчет побед для каждого участника
        val wins = mutableMapOf<Habitant, Int>()
        inhabitants.forEach { wins[it] = 0 }

        var events = 0
        for (i in inhabitants.indices) {
            for (j in inhabitants.indices) {
                if (i != j) {
                    inhabitants[i].playBrockianUltraCricket(inhabitants[j])
                    wins[inhabitants[i]] = (wins[inhabitants[i]] ?: 0) + 1
                    events++
                }
            }
        }

        // Определяем победителя
        val winner = wins.maxByOrNull { it.value }

        return buildString {
            appendLine("Турнир завершен! События: $events")
            if (winner != null) {
                appendLine("🏆 Победитель: ${winner.key.name} с ${winner.value} победами!")
                appendLine("\nТаблица результатов:")
                wins.entries.sortedByDescending { it.value }.forEach { (habitant, count) ->
                    appendLine("  ${habitant.name}: $count побед")
                }
            }
        }
    }
}
