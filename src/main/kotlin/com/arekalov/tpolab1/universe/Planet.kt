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

        val wins = mutableMapOf<Habitant, Int>()
        inhabitants.forEach { wins[it] = 0 }

        var events = 0
        for (attacker in inhabitants) {
            events += processTournamentMatches(attacker = attacker, wins = wins)
        }

        return buildTournamentReport(wins = wins, events = events)
    }

    private fun processTournamentMatches(attacker: Habitant, wins: MutableMap<Habitant, Int>): Int {
        var matchesCount = 0
        for (defender in inhabitants) {
            if (attacker != defender) {
                attacker.playBrockianUltraCricket(target = defender)
                if (attacker.knowledgeLevel >= defender.knowledgeLevel) {
                    wins[attacker] = (wins[attacker] ?: 0) + 1
                }
                matchesCount++
            }
        }
        return matchesCount
    }

    private fun buildTournamentReport(wins: Map<Habitant, Int>, events: Int): String {
        val winner = wins.maxBy { it.value }
        return buildString {
            appendLine("Турнир завершен! События: $events")
            appendLine("🏆 Победитель: ${winner.key.name} с ${winner.value} победами!")
            appendLine("\nТаблица результатов:")
            wins.entries.sortedByDescending { it.value }.forEach { (habitant, count) ->
                appendLine("  ${habitant.name}: $count побед")
            }
        }
    }
}
