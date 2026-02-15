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

    var inhabitedRaces: List<Race> = emptyList()
        private set

    val hasFrustratedInhabitants: Boolean
        get() = inhabitants.any { it.frustrationLevel > 0.5 }

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
        updateStats()
    }

    /**
     * Обновить статистику планеты
     */
    private fun updateStats() {
        inhabitedRaces = inhabitants.map { it.race }.distinct()
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

        var events = 0
        for (i in inhabitants.indices) {
            for (j in inhabitants.indices) {
                if (i != j) {
                    inhabitants[i].playBrockianUltraCricket(inhabitants[j])
                    events++
                }
            }
        }

        updateStats()
        return "Турнир завершен! События: $events"
    }
}
