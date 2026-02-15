package com.arekalov.tpolab1.universe

/**
 * Вселенная, в которой происходят события
 */
class Universe(
    val name: String,
    var age: Long = 0,
    private val planets: MutableList<Planet> = mutableListOf(),
) {
    private var supercomputer: Supercomputer? = null
    private val events: MutableList<String> = mutableListOf()

    var totalInhabitants: Int = 0
        private set

    var planetsCount: Int = 0
        private set

    var isTiredOfDebates: Boolean = false
        private set

    /**
     * Добавить планету во вселенную
     */
    fun addPlanet(planet: Planet) {
        planets.add(planet)
        updateStats()
        logEvent("Планета ${planet.name} добавлена во вселенную")
    }

    /**
     * Обновить статистику вселенной
     */
    fun updateStats() {
        planetsCount = planets.size
        totalInhabitants = planets.sumOf { it.inhabitantCount }

        val allInhabitants = planets.flatMap { it.inhabitants }
        if (allInhabitants.isNotEmpty()) {
            val frustratedCount = allInhabitants.count { it.isTiredOfDebates() }
            isTiredOfDebates = frustratedCount.toDouble() / allInhabitants.size > 0.5
        }

        // Обновляем статистику планет
        planets.forEach { it.updateStats() }
    }

    /**
     * Получить список планет
     */
    fun getPlanets(): List<Planet> = planets.toList()

    /**
     * Получить планету по имени
     */
    fun getPlanetByName(name: String): Planet? =
        planets.find { it.name == name }

    /**
     * Получить всех обитателей вселенной
     */
    fun getAllInhabitants(): List<Habitant> =
        planets.flatMap { it.inhabitants }

    /**
     * Получить количество обитателей определенной расы во всей вселенной
     */
    fun getInhabitantCountByRace(race: Race): Int =
        getAllInhabitants().count { it.race == race }

    /**
     * Создать суперкомпьютер для решения вопроса о смысле жизни
     */
    fun buildSupercomputer(computerName: String): String {
        if (supercomputer != null) {
            return "Суперкомпьютер уже существует!"
        }

        val capableBeings = getAllInhabitants().filter { it.canBuildSupercomputer() }
        if (capableBeings.isEmpty()) {
            return "Нет существ, способных построить суперкомпьютер"
        }

        supercomputer = Supercomputer(computerName)

        logEvent("Построен суперкомпьютер '$computerName'")
        return "Суперкомпьютер '$computerName' успешно построен!"
    }

    /**
     * Получить суперкомпьютер
     */
    fun getSupercomputer(): Supercomputer? = supercomputer

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
        planets.forEach { it.conductUltraCricketTournament() }
        updateStats()
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
        val allInhabitants = getAllInhabitants()
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

            // Статистика любимых занятий
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
                appendLine("Прогресс вычислений: ${"%.2f".format(supercomputer!!.getProgressPercentage())}%")
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

    /**
     * Получить историю событий
     */
    fun getHistory(): List<String> = events.toList()
}
