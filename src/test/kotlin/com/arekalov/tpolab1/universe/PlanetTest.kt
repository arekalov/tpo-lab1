package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PlanetTest {

    private fun createPlanet(
        name: String = "Test Planet",
        mass: Double = 1.0e24,
    ): Planet = Planet(name = name, mass = mass)

    private fun createHabitant(
        name: String = "Habitant",
        race: Race = Race.HUMAN,
        favoriteActivity: TypeOfActivity = TypeOfActivity.CONTEMPLATING,
        frustrationLevel: Int = 0,
        knowledgeLevel: Int = 0,
    ): Habitant = Habitant(
        name = name,
        race = race,
        favoriteActivity = favoriteActivity,
        frustrationLevel = frustrationLevel,
        knowledgeLevel = knowledgeLevel,
    )

    @Test
    @DisplayName("Создание планеты с параметрами")
    fun testCreatePlanet() {
        val planet = createPlanet()

        assertEquals("Test Planet", planet.name)
        assertEquals(1.0e24, planet.mass)
        assertEquals(0, planet.inhabitantCount)
    }

    @Test
    @DisplayName("Добавление обитателя на планету")
    fun testAddInhabitant() {
        val planet = createPlanet()
        val habitant = createHabitant()

        planet.addInhabitant(habitant = habitant)

        assertEquals(1, planet.inhabitantCount)
        assertTrue(planet.inhabitants.contains(habitant))
    }

    @Test
    @DisplayName("Добавление нескольких обитателей")
    fun testAddMultipleInhabitants() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "H1")
        val h2 = createHabitant(name = "H2")
        val h3 = createHabitant(name = "H3", race = Race.VOGON)

        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)
        planet.addInhabitant(habitant = h3)

        assertEquals(3, planet.inhabitantCount)
    }

    @Test
    @DisplayName("inhabitedRaces возвращает список уникальных рас")
    fun testInhabitedRaces() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "H1", race = Race.HUMAN)
        val h2 = createHabitant(name = "H2", race = Race.HUMAN)
        val h3 = createHabitant(name = "H3", race = Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS)

        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)
        planet.addInhabitant(habitant = h3)
        val races = planet.inhabitedRaces

        assertEquals(2, races.size)
        assertTrue(races.contains(Race.HUMAN))
        assertTrue(races.contains(Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS))
    }

    @Test
    @DisplayName("inhabitedRaces пустой для планеты без обитателей")
    fun testInhabitedRacesEmpty() {
        val planet = createPlanet()

        assertTrue(planet.inhabitedRaces.isEmpty())
    }

    @Test
    @DisplayName("hasFrustratedInhabitants = false когда нет уставших")
    fun testNoFrustratedInhabitants() {
        val planet = createPlanet()
        planet.addInhabitant(habitant = createHabitant(name = "H1", frustrationLevel = 30))
        planet.addInhabitant(habitant = createHabitant(name = "H2", frustrationLevel = 50))

        assertFalse(planet.hasFrustratedInhabitants)
    }

    @Test
    @DisplayName("hasFrustratedInhabitants = true когда есть уставшие")
    fun testHasFrustratedInhabitants() {
        val planet = createPlanet()
        planet.addInhabitant(habitant = createHabitant(name = "H1", frustrationLevel = 30))
        planet.addInhabitant(habitant = createHabitant(name = "H2", frustrationLevel = 60))

        assertTrue(planet.hasFrustratedInhabitants)
    }

    @Test
    @DisplayName("averageFrustrationLevel = 0.0 для пустой планеты")
    fun testAverageFrustrationEmpty() {
        val planet = createPlanet()

        assertEquals(0.0, planet.averageFrustrationLevel)
    }

    @Test
    @DisplayName("averageFrustrationLevel вычисляется корректно")
    fun testAverageFrustration() {
        val planet = createPlanet()
        planet.addInhabitant(habitant = createHabitant(name = "H1", frustrationLevel = 10))
        planet.addInhabitant(habitant = createHabitant(name = "H2", frustrationLevel = 20))
        planet.addInhabitant(habitant = createHabitant(name = "H3", frustrationLevel = 30))

        assertEquals(20.0, planet.averageFrustrationLevel)
    }

    @Test
    @DisplayName("getInhabitantCountByRace считает правильно")
    fun testGetInhabitantCountByRace() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "H1", race = Race.HUMAN)
        val h2 = createHabitant(name = "H2", race = Race.HUMAN)
        val h3 = createHabitant(name = "H3", race = Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS)
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)
        planet.addInhabitant(habitant = h3)

        assertEquals(2, planet.getInhabitantCountByRace(race = Race.HUMAN))
        assertEquals(1, planet.getInhabitantCountByRace(race = Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS))
        assertEquals(0, planet.getInhabitantCountByRace(race = Race.VOGON))
    }

    @Test
    @DisplayName("conductUltraCricketTournament требует минимум 2 участников")
    fun testTournamentNotEnoughParticipants() {
        val planet = createPlanet()
        val habitant = createHabitant()
        planet.addInhabitant(habitant = habitant)

        val result = planet.conductUltraCricketTournament()

        assertEquals("Недостаточно участников для турнира", result)
    }

    @Test
    @DisplayName("conductUltraCricketTournament с двумя участниками")
    fun testTournamentTwoParticipants() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "H1")
        val h2 = createHabitant(name = "H2")
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)

        val result = planet.conductUltraCricketTournament()

        assertTrue(result.contains("Турнир завершен!"))
        assertTrue(result.contains("События: 2"))
        assertTrue(result.contains("Победитель"))
    }

    @Test
    @DisplayName("conductUltraCricketTournament подсчитывает события правильно")
    fun testTournamentEventsCount() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "H1", knowledgeLevel = 10)
        val h2 = createHabitant(name = "H2", knowledgeLevel = 5)
        val h3 = createHabitant(name = "H3", race = Race.VOGON, knowledgeLevel = 3)
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)
        planet.addInhabitant(habitant = h3)

        val result = planet.conductUltraCricketTournament()

        assertTrue(result.contains("События: 6"))
    }

    @Test
    @DisplayName("conductUltraCricketTournament показывает таблицу результатов")
    fun testTournamentResultsTable() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "Human1")
        val h2 = createHabitant(name = "Human2")
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)

        val result = planet.conductUltraCricketTournament()

        assertTrue(result.contains("Таблица результатов:"))
        assertTrue(result.contains("Human1"))
        assertTrue(result.contains("Human2"))
        assertTrue(result.contains("побед"))
    }

    @Test
    @DisplayName("conductUltraCricketTournament влияет на frustration участников")
    fun testTournamentAffectsFrustration() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "H1", frustrationLevel = 20, knowledgeLevel = 10)
        val h2 = createHabitant(name = "H2", frustrationLevel = 20, knowledgeLevel = 5)
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)

        planet.conductUltraCricketTournament()

        assertEquals(18, h1.frustrationLevel)
        assertEquals(18, h2.frustrationLevel)
    }

    @Test
    @DisplayName("conductUltraCricketTournament с тремя участниками")
    fun testTournamentWithThreeParticipants() {
        val planet = createPlanet()
        val h1 = createHabitant(name = "Human1", knowledgeLevel = 15)
        val h2 = createHabitant(name = "Human2", knowledgeLevel = 10)
        val h3 = createHabitant(name = "Human3", favoriteActivity = TypeOfActivity.STUDYING, knowledgeLevel = 5)
        planet.addInhabitant(habitant = h1)
        planet.addInhabitant(habitant = h2)
        planet.addInhabitant(habitant = h3)

        val result = planet.conductUltraCricketTournament()

        assertTrue(result.contains("Турнир завершен!"))
        assertTrue(result.contains("События: 6"))
        assertTrue(result.contains("🏆 Победитель:"))
        assertTrue(result.contains("Human1"))
        assertTrue(result.contains("Human2"))
        assertTrue(result.contains("Human3"))
    }

    @Test
    @DisplayName("conductUltraCricketTournament с большим количеством участников")
    fun testTournamentWithManyParticipants() {
        val planet = createPlanet()
        val participants = List(5) { i -> createHabitant(name = "Human$i", knowledgeLevel = 10 - i) }
        participants.forEach { planet.addInhabitant(habitant = it) }

        val result = planet.conductUltraCricketTournament()

        assertTrue(result.contains("Турнир завершен!"))
        assertTrue(result.contains("События: 20"))
        assertTrue(result.contains("🏆 Победитель:"))
        participants.forEach { habitant ->
            assertTrue(result.contains(habitant.name))
        }
    }
}
