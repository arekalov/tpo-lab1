package com.arekalov.tpolab1.universe

/**
 * Вселенная, в которой происходят события
 */
class Universe(
    val name: String,
    var age: Long = 0,
    private val _planets: MutableList<Planet> = mutableListOf(),
) {
    var supercomputer: Supercomputer? = null
        private set

    private val events: MutableList<String> = mutableListOf()

    val totalInhabitants: Int
        get() = _planets.sumOf { it.inhabitantCount }

    val planetsCount: Int
        get() = _planets.size

    val isTiredOfDebates: Boolean
        get() {
            val allInhabitants = _planets.flatMap { it.inhabitants }
            if (allInhabitants.isEmpty()) return false
            val frustratedCount = allInhabitants.count { it.isTiredOfDebates }
            return frustratedCount.toDouble() / allInhabitants.size > 0.5
        }

    val history: List<String>
        get() = events.toList()

    val planets: List<Planet>
        get() = _planets.toList()

    val allInhabitants: List<Habitant>
        get() = _planets.flatMap { it.inhabitants }

    /**
     * Добавить планету во вселенную
     */
    fun addPlanet(planet: Planet) {
        _planets.add(planet)
        logEvent("Планета ${planet.name} добавлена во вселенную")
    }

    /**
     * Получить планету по имени
     */
    fun getPlanetByName(name: String): Planet? =
        _planets.find { it.name == name }

    /**
     * Получить количество обитателей определенной расы во всей вселенной
     */
    fun getInhabitantCountByRace(race: Race): Int =
        allInhabitants.count { it.race == race }

    /**
     * Создать суперкомпьютер для решения вопроса о смысле жизни
     */
    fun buildSupercomputer(computerName: String): String {
        if (supercomputer != null) {
            return "Суперкомпьютер уже существует!"
        }

        val capableBeings = allInhabitants.filter { it.canBuildSupercomputer }
        if (capableBeings.isEmpty()) {
            return "Нет существ, способных построить суперкомпьютер"
        }

        supercomputer = Supercomputer(computerName)

        logEvent("Построен суперкомпьютер '$computerName'")
        return "Суперкомпьютер '$computerName' успешно построен!"
    }

    /**
     * Запустить поиск ответа на главный вопрос
     */
    fun startSearchingForAnswer(): String {
        if (supercomputer == null) {
            return "Сначала нужно построить суперкомпьютер!"
        }

        val result = supercomputer!!.startCalculation()

        logEvent("Начат поиск ответа на главный вопрос жизни, вселенной и всего такого")
        return result
    }

    /**
     * Провести глобальный турнир по брокианскому ультра-крикету
     */
    fun conductUniversalUltraCricketTournament(): String {
        _planets.forEach { it.conductUltraCricketTournament() }
        logEvent("Проведен вселенский турнир по брокианскому ультра-крикету")
        return "Турнир завершен на $planetsCount планетах"
    }

    /**
     * Симулировать прохождение времени
     */
    fun passTime(timeUnits: Long): String {
        age += timeUnits
        supercomputer?.tick(timeUnits.toInt())

        return "Прошло $timeUnits единиц времени. Возраст вселенной: $age"
    }

    /**
     * Получить состояние вселенной
     */
    fun getStatus(): String {
        val avgFrustration = if (allInhabitants.isNotEmpty()) {
            allInhabitants.map { it.frustrationLevel }.average()
        } else {
            0.0
        }

        return buildString {
            appendLine("=== Состояние вселенной '$name' ===")
            appendLine("Возраст: $age")
            appendLine("Планет: $planetsCount")
            appendLine("Обитателей: $totalInhabitants")
            appendLine("Средняя степень разочарования: ${"%.2f".format(avgFrustration)}")

            val favoriteActivities = allInhabitants.groupBy { it.favoriteActivity }
                .mapValues { it.value.size }
                .toList()
                .sortedByDescending { it.second }

            if (favoriteActivities.isNotEmpty()) {
                appendLine("\nЛюбимые занятия обитателей:")
                favoriteActivities.forEach { (activity, count) ->
                    appendLine("  - $activity: $count")
                }
            }

            appendLine("\nСуперкомпьютер: ${if (supercomputer != null) "Есть" else "Нет"}")
            if (supercomputer != null) {
                appendLine("Прогресс вычислений: ${"%.2f".format(supercomputer!!.progressPercentage)}%")
                appendLine("Ответ: ${supercomputer!!.getAnswer() ?: "Еще не готов"}")
            }
        }
    }

    /**
     * Логировать событие
     */
    private fun logEvent(event: String) {
        events.add("[$age] $event")
    }
}
