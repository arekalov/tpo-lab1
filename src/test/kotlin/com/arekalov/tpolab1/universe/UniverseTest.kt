package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Тесты доменной модели Вселенной")
class UniverseTest {

    private lateinit var universe: Universe
    private lateinit var planet: Planet
    private lateinit var being: Habitant

    @BeforeEach
    fun setup() {
        universe = Universe("Multidimensional Universe")
        planet = Planet("Magrathea", 5.972e24)
        being = Habitant(
            "Slartibartfast",
            Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
            TypeOfActivity.RESTING,
            0,
            15,
        )
    }

    @Test
    @DisplayName("Создание новой вселенной")
    fun testCreateUniverse() {
        assertEquals("Multidimensional Universe", universe.name)
        assertEquals(0, universe.age)
        assertTrue(universe.planets.isEmpty())
    }

    @Test
    @DisplayName("Добавление планеты во вселенную")
    fun testAddPlanet() {
        universe.addPlanet(planet)

        assertEquals(1, universe.planets.size)
        assertEquals("Magrathea", universe.planets[0].name)
    }

    @Test
    @DisplayName("Добавление обитателя на планету")
    fun testAddInhabitantToPlanet() {
        planet.addInhabitant(being)

        assertEquals(1, planet.inhabitants.size)
        assertEquals("Slartibartfast", planet.inhabitants[0].name)
    }

    @Test
    @DisplayName("Обитатель размышляет")
    fun testHabitantStartsDebating() {
        val initialKnowledge = being.knowledgeLevel
        being.contemplate()

        assertEquals(TypeOfActivity.RESTING, being.favoriteActivity) // не меняется
        assertTrue(being.knowledgeLevel > initialKnowledge)
    }

    @Test
    @DisplayName("Игра в брокианский ультра-крикет")
    fun testBrockianUltraCricket() {
        val target = Habitant("Arthur", Race.HUMAN)
        val targetInitialFrustration = target.frustrationLevel

        val result = being.playBrockianUltraCricket(target)

        assertEquals(TypeOfActivity.RESTING, being.favoriteActivity) // не меняется
        assertTrue(target.frustrationLevel > targetInitialFrustration)
        assertTrue(result.contains("неожиданно ударяет"))
    }

    @Test
    @DisplayName("Обитатель устает от споров")
    fun testHabitantGetsTired() {
        being.frustrationLevel = 60

        assertTrue(being.isTiredOfDebates)
    }

    @Test
    @DisplayName("Проверка способности построить суперкомпьютер")
    fun testCanBuildSupercomputer() {
        assertTrue(being.canBuildSupercomputer)

        val human = Habitant("Ford", Race.HUMAN, knowledgeLevel = 20)
        assertFalse(human.canBuildSupercomputer)
    }

    @Test
    @DisplayName("Турнир по брокианскому ультра-крикету на планете")
    fun testPlanetUltraCricketTournament() {
        planet.addInhabitant(being)
        planet.addInhabitant(Habitant("Zaphod", Race.HUMAN))

        val result = planet.conductUltraCricketTournament()

        assertTrue(result.contains("Турнир завершен"))
    }

    @Test
    @DisplayName("Турнир с недостаточным количеством участников")
    fun testTournamentWithInsufficientParticipants() {
        planet.addInhabitant(being)

        val result = planet.conductUltraCricketTournament()

        assertEquals("Недостаточно участников для турнира", result)
    }

    @Test
    @DisplayName("Получение обитателей по расе")
    fun testGetInhabitantsByRace() {
        planet.addInhabitant(being)
        planet.addInhabitant(Habitant("Trillian", Race.HUMAN))
        planet.addInhabitant(Habitant("Ford", Race.HUMAN))

        assertEquals(
            1,
            planet.getInhabitantCountByRace(Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS),
        )
        assertEquals(2, planet.getInhabitantCountByRace(Race.HUMAN))
    }

    @Test
    @DisplayName("Проверка обитаемых рас на планете")
    fun testGetInhabitedRaces() {
        planet.addInhabitant(being)
        planet.addInhabitant(Habitant("Arthur", Race.HUMAN))

        val races = planet.inhabitedRaces

        assertEquals(2, races.size)
        assertTrue(races.contains(Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS))
        assertTrue(races.contains(Race.HUMAN))
    }

    @Test
    @DisplayName("Построение суперкомпьютера")
    fun testBuildSupercomputer() {
        universe.addPlanet(planet)
        planet.addInhabitant(being)

        val result = universe.buildSupercomputer("Deep Thought")

        assertTrue(result.contains("успешно построен"))
        assertNotNull(universe.supercomputer)
        assertEquals("Deep Thought", universe.supercomputer?.name)
    }

    @Test
    @DisplayName("Попытка построить суперкомпьютер без способных существ")
    fun testBuildSupercomputerWithoutCapableBeings() {
        universe.addPlanet(planet)
        planet.addInhabitant(Habitant("Human", Race.HUMAN))

        val result = universe.buildSupercomputer("Deep Thought")

        assertTrue(result.contains("Нет существ"))
        assertNull(universe.supercomputer)
    }

    @Test
    @DisplayName("Запуск вычисления ответа")
    fun testStartSearchingForAnswer() {
        universe.addPlanet(planet)
        planet.addInhabitant(being)
        universe.buildSupercomputer("Deep Thought")

        val result = universe.startSearchingForAnswer()

        assertTrue(result.contains("размышления"))
        assertTrue(universe.supercomputer!!.isCurrentlyCalculating)
    }

    @Test
    @DisplayName("Проверка вселенной на усталость от споров")
    fun testUniverseTiredOfDebates() {
        universe.addPlanet(planet)

        // Добавляем несколько существ
        repeat(10) { i ->
            val hab = Habitant("Being$i", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS)
            hab.frustrationLevel = 60 // Все устали
            planet.addInhabitant(hab)
        }

        assertTrue(universe.isTiredOfDebates)
    }

    @Test
    @DisplayName("Прохождение времени во вселенной")
    fun testPassTime() {
        val initialAge = universe.age
        universe.passTime(1000)

        assertEquals(initialAge + 1000, universe.age)
    }

    @Test
    @DisplayName("Получение статуса вселенной")
    fun testGetUniverseStatus() {
        universe.addPlanet(planet)
        planet.addInhabitant(being)

        val status = universe.getStatus()

        assertTrue(status.contains("Multidimensional Universe"))
        assertTrue(status.contains("Планет: 1"))
        assertTrue(status.contains("Обитателей: 1"))
    }
}
