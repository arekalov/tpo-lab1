package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Тесты доменной модели Вселенной")
class UniverseTest {

    private fun createUniverse(name: String = "Multidimensional Universe"): Universe =
        Universe(name)

    private fun createPlanet(
        name: String = "Magrathea",
        mass: Double = 5.972e24,
    ): Planet = Planet(name, mass)

    private fun createHabitant(
        name: String = "Slartibartfast",
        race: Race = Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
        favoriteActivity: TypeOfActivity = TypeOfActivity.RESTING,
        frustrationLevel: Int = 0,
        knowledgeLevel: Int = 15,
    ): Habitant = Habitant(name, race, favoriteActivity, frustrationLevel, knowledgeLevel)

    @Test
    @DisplayName("Создание новой вселенной")
    fun testCreateUniverse() {
        val universe = createUniverse()

        assertEquals("Multidimensional Universe", universe.name)
        assertEquals(0, universe.age)
        assertTrue(universe.planets.isEmpty())
    }

    @Test
    @DisplayName("Добавление планеты во вселенную")
    fun testAddPlanet() {
        val universe = createUniverse()
        val planet = createPlanet()

        universe.addPlanet(planet = planet)

        assertEquals(1, universe.planets.size)
        assertEquals("Magrathea", universe.planets[0].name)
    }

    @Test
    @DisplayName("Добавление обитателя на планету")
    fun testAddInhabitantToPlanet() {
        val planet = createPlanet()
        val habitant = createHabitant()

        planet.addInhabitant(habitant = habitant)

        assertEquals(1, planet.inhabitants.size)
        assertEquals("Slartibartfast", planet.inhabitants[0].name)
    }

    @Test
    @DisplayName("Обитатель размышляет")
    fun testHabitantStartsDebating() {
        val habitant = createHabitant(knowledgeLevel = 10)
        val initialKnowledge = habitant.knowledgeLevel

        habitant.contemplate()

        assertEquals(TypeOfActivity.RESTING, habitant.favoriteActivity)
        assertTrue(habitant.knowledgeLevel > initialKnowledge)
    }

    @Test
    @DisplayName("Игра в брокианский ультра-крикет")
    fun testBrockianUltraCricket() {
        val attacker = createHabitant(name = "Attacker")
        val target = createHabitant(name = "Arthur", race = Race.HUMAN, frustrationLevel = 10)
        val initialFrustration = target.frustrationLevel

        val result = attacker.playBrockianUltraCricket(target)

        assertEquals(TypeOfActivity.RESTING, attacker.favoriteActivity)
        assertTrue(target.frustrationLevel > initialFrustration)
        assertTrue(result.contains("неожиданно ударяет"))
    }

    @Test
    @DisplayName("Обитатель устает от споров")
    fun testHabitantGetsTired() {
        val habitant = createHabitant(frustrationLevel = 60)

        assertTrue(habitant.isTiredOfDebates)
    }

    @Test
    @DisplayName("Проверка способности построить суперкомпьютер")
    fun testCanBuildSupercomputer() {
        val smartBeing = createHabitant(knowledgeLevel = 15)
        val human = createHabitant(name = "Ford", race = Race.HUMAN, knowledgeLevel = 20)

        assertTrue(smartBeing.canBuildSupercomputer)
        assertFalse(human.canBuildSupercomputer)
    }

    @Test
    @DisplayName("Турнир по брокианскому ультра-крикету на планете")
    fun testPlanetUltraCricketTournament() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "H1")
        val h2 = createHabitant(name = "H2", race = Race.HUMAN)
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)

        val result = planet.conductUltraCricketTournament()

        assertTrue(result.contains("Турнир завершен"))
    }

    @Test
    @DisplayName("Турнир с недостаточным количеством участников")
    fun testTournamentWithInsufficientParticipants() {
        val planet = createPlanet()
        val habitant = createHabitant()
        planet.addInhabitant(habitant = habitant)

        val result = planet.conductUltraCricketTournament()

        assertEquals("Недостаточно участников для турнира", result)
    }

    @Test
    @DisplayName("Получение обитателей по расе")
    fun testGetInhabitantsByRace() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "H1")
        val h2 = createHabitant(name = "H2", race = Race.HUMAN)
        val h3 = createHabitant(name = "H3", race = Race.HUMAN)
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)
        planet.addInhabitant(habitant = h3)

        assertEquals(1, planet.getInhabitantCountByRace(Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS))
        assertEquals(2, planet.getInhabitantCountByRace(Race.HUMAN))
    }

    @Test
    @DisplayName("Проверка обитаемых рас на планете")
    fun testGetInhabitedRaces() {
        val planet = createPlanet()
        val h1 = createHabitant()
        val h2 = createHabitant(name = "Arthur", race = Race.HUMAN)
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)

        val races = planet.inhabitedRaces

        assertEquals(2, races.size)
        assertTrue(races.contains(Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS))
        assertTrue(races.contains(Race.HUMAN))
    }

    @Test
    @DisplayName("Построение суперкомпьютера")
    fun testBuildSupercomputer() {
        val universe = createUniverse()
        val planet = createPlanet()
        val habitant = createHabitant()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = habitant)

        val result = universe.buildSupercomputer(computerName = "Deep Thought")

        assertTrue(result.contains("успешно построен"))
        assertNotNull(universe.supercomputer)
        assertEquals("Deep Thought", universe.supercomputer?.name)
    }

    @Test
    @DisplayName("Попытка построить суперкомпьютер без способных существ")
    fun testBuildSupercomputerWithoutCapableBeings() {
        val universe = createUniverse()
        val planet = createPlanet()
        val human = createHabitant(name = "Human", race = Race.HUMAN)
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = human)

        val result = universe.buildSupercomputer(computerName = "Deep Thought")

        assertTrue(result.contains("Нет существ"))
        assertNull(universe.supercomputer)
    }

    @Test
    @DisplayName("Запуск вычисления ответа")
    fun testStartSearchingForAnswer() {
        val universe = createUniverse()
        val planet = createPlanet()
        val habitant = createHabitant()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = habitant)
        universe.buildSupercomputer(computerName = "Deep Thought")

        val result = universe.startSearchingForAnswer()

        assertTrue(result.contains("размышления"))
        assertTrue(universe.supercomputer!!.isCurrentlyCalculating)
    }

    @Test
    @DisplayName("Проверка вселенной на усталость от споров")
    fun testUniverseTiredOfDebates() {
        val universe = createUniverse()
        val planet = createPlanet()
        universe.addPlanet(planet = planet)
        repeat(10) { i ->
            val hab = createHabitant(name = "Being$i", frustrationLevel = 60)
            planet.addInhabitant(habitant = hab)
        }

        assertTrue(universe.isTiredOfDebates)
    }

    @Test
    @DisplayName("Прохождение времени во вселенной")
    fun testPassTime() {
        val universe = createUniverse()
        val initialAge = universe.age

        universe.passTime(timeUnits = 1000)

        assertEquals(initialAge + 1000, universe.age)
    }

    @Test
    @DisplayName("Получение статуса вселенной")
    fun testGetUniverseStatus() {
        val universe = createUniverse()
        val planet = createPlanet()
        val habitant = createHabitant()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = habitant)

        val status = universe.getStatus()

        assertTrue(status.contains("Multidimensional Universe"))
        assertTrue(status.contains("Планет: 1"))
        assertTrue(status.contains("Обитателей: 1"))
    }

    @Test
    @DisplayName("isTiredOfDebates для пустой вселенной")
    fun testIsTiredOfDebatesEmptyUniverse() {
        val universe = createUniverse()

        assertFalse(universe.isTiredOfDebates)
    }

    @Test
    @DisplayName("isTiredOfDebates когда не устали")
    fun testIsTiredOfDebatesNotTired() {
        val universe = createUniverse()
        val planet = createPlanet()
        universe.addPlanet(planet = planet)
        repeat(10) { i ->
            val hab = createHabitant(name = "Being$i", race = Race.HUMAN, frustrationLevel = 20)
            planet.addInhabitant(habitant = hab)
        }

        assertFalse(universe.isTiredOfDebates)
    }

    @Test
    @DisplayName("getPlanetByName возвращает null для несуществующей планеты")
    fun testGetPlanetByNameNotFound() {
        val universe = createUniverse()
        val planet = createPlanet()
        universe.addPlanet(planet = planet)

        assertNull(universe.getPlanetByName(name = "NonExistent"))
    }

    @Test
    @DisplayName("getPlanetByName находит планету")
    fun testGetPlanetByNameFound() {
        val universe = createUniverse()
        val planet = createPlanet()
        universe.addPlanet(planet = planet)

        val foundPlanet = universe.getPlanetByName(name = "Magrathea")

        assertEquals(planet, foundPlanet)
    }

    @Test
    @DisplayName("getInhabitantCountByRace подсчитывает правильно")
    fun testGetInhabitantCountByRace() {
        val universe = createUniverse()
        val planet = createPlanet()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = createHabitant(name = "H1", race = Race.HUMAN))
        planet.addInhabitant(habitant = createHabitant(name = "H2", race = Race.HUMAN))
        planet.addInhabitant(habitant = createHabitant(name = "A1", race = Race.VOGON))

        assertEquals(2, universe.getInhabitantCountByRace(race = Race.HUMAN))
        assertEquals(1, universe.getInhabitantCountByRace(race = Race.VOGON))
        assertEquals(0, universe.getInhabitantCountByRace(race = Race.DOLPHIN))
    }

    @Test
    @DisplayName("startSearchingForAnswer без суперкомпьютера")
    fun testStartSearchingForAnswerWithoutComputer() {
        val universe = createUniverse()

        val result = universe.startSearchingForAnswer()

        assertTrue(result.contains("построить суперкомпьютер"))
    }

    @Test
    @DisplayName("conductUniversalUltraCricketTournament проводит турнир")
    fun testConductUniversalUltraCricketTournament() {
        val universe = createUniverse()
        val planet = createPlanet()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = createHabitant(name = "P1", race = Race.HUMAN))
        planet.addInhabitant(habitant = createHabitant(name = "P2", race = Race.HUMAN))

        val result = universe.conductUniversalUltraCricketTournament()

        assertTrue(result.contains("Турнир завершен"))
        assertTrue(result.contains("1 планет"))
    }

    @Test
    @DisplayName("getStatus для пустой вселенной")
    fun testGetStatusEmptyUniverse() {
        val universe = createUniverse()

        val status = universe.getStatus()

        assertTrue(status.contains("Планет: 0"))
        assertTrue(status.contains("Обитателей: 0"))
        assertTrue(status.contains("0.00"))
    }

    @Test
    @DisplayName("getStatus с суперкомпьютером без ответа")
    fun testGetStatusWithComputerNoAnswer() {
        val universe = createUniverse()
        val planet = createPlanet()
        val habitant = createHabitant()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = habitant)
        universe.buildSupercomputer(computerName = "Deep Thought")

        universe.startSearchingForAnswer()
        val status = universe.getStatus()

        assertTrue(status.contains("Суперкомпьютер: Есть"))
        assertTrue(status.contains("Прогресс вычислений"))
        assertTrue(status.contains("Ответ: Еще не готов"))
    }

    @Test
    @DisplayName("getStatus с завершенным суперкомпьютером")
    fun testGetStatusWithCompletedComputer() {
        val universe = createUniverse()
        val planet = createPlanet()
        val habitant = createHabitant()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = habitant)
        universe.buildSupercomputer(computerName = "Deep Thought")
        universe.startSearchingForAnswer()

        universe.passTime(timeUnits = 7_500_000)
        val status = universe.getStatus()

        assertTrue(status.contains("Суперкомпьютер: Есть"))
        assertTrue(status.contains("100.00%"))
        assertTrue(status.contains("Ответ: 42"))
    }

    @Test
    @DisplayName("Создание вселенной с возрастом")
    fun testCreateUniverseWithAge() {
        val universe = Universe(name = "Old Universe", age = 1_000_000)
        universe.age = 1
        assertEquals(1, universe.age)
    }

    @Test
    @DisplayName("history возвращает список событий")
    fun testHistory() {
        val universe = createUniverse()
        val planet = createPlanet()

        universe.addPlanet(planet = planet)
        val history = universe.history

        assertEquals(1, history.size)
        assertTrue(history[0].contains("Планета"))
    }

    @Test
    @DisplayName("buildSupercomputer когда компьютер уже существует")
    fun testBuildSupercomputerAlreadyExists() {
        val universe = createUniverse()
        val planet = createPlanet()
        val habitant = createHabitant()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = habitant)
        universe.buildSupercomputer(computerName = "Deep Thought")

        val result = universe.buildSupercomputer(computerName = "Another Computer")

        assertTrue(result.contains("уже существует"))
    }

    @Test
    @DisplayName("getStatus с разными типами активностей (сортировка)")
    fun testGetStatusWithMultipleActivities() {
        val universe = createUniverse()
        val planet = createPlanet()
        universe.addPlanet(planet = planet)
        planet.addInhabitant(habitant = createHabitant(name = "H1", favoriteActivity = TypeOfActivity.STUDYING))
        planet.addInhabitant(habitant = createHabitant(name = "H2", favoriteActivity = TypeOfActivity.STUDYING))
        planet.addInhabitant(habitant = createHabitant(name = "H3", favoriteActivity = TypeOfActivity.STUDYING))
        planet.addInhabitant(habitant = createHabitant(name = "H4", favoriteActivity = TypeOfActivity.CONTEMPLATING))
        planet.addInhabitant(habitant = createHabitant(name = "H5", favoriteActivity = TypeOfActivity.RESTING))

        val status = universe.getStatus()

        assertTrue(status.contains("Любимые занятия обитателей:"))
        assertTrue(status.contains("STUDYING: 3"))
    }
}
