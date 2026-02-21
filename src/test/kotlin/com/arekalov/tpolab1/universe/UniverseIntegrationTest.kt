package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Интеграционные тесты: Полный сценарий вселенной")
class UniverseIntegrationTest {

    @Test
    @DisplayName("Полный сценарий: от дискуссий до создания суперкомпьютера")
    fun testFullScenario() {
        // 1. Создаем вселенную
        val universe = Universe("The Multidimensional Universe")

        // 2. Создаем планету
        val magrathea = Planet("Magrathea", 5.972e24)
        universe.addPlanet(magrathea)

        // 3. Добавляем гиперразумных существ
        val beings = listOf(
            Habitant("Slartibartfast", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 15),
            Habitant("Frankie", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 12),
            Habitant("Benjy", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 13),
        )

        beings.forEach { magrathea.addInhabitant(it) }

        // 4. Начинается дискуссия о смысле жизни
        val debate = Debate()
        beings.forEach { debate.addParticipant(it) }
        debate.start()

        // 5. Дискуссия продолжается много раундов
        repeat(12) { debate.conductRound() }

        // 6. Существа устают от споров
        assertTrue(debate.needsDrasticMeasures)
        assertTrue(universe.isTiredOfDebates)

        // 7. Они решают создать суперкомпьютер
        debate.end()
        val buildResult = universe.buildSupercomputer("Deep Thought")
        assertTrue(buildResult.contains("успешно"))

        // 8. Запускают вычисление
        universe.startSearchingForAnswer()

        // 9. favoriteActivity больше не меняется - это константа для каждого существа

        // 10. Время идет...
        universe.passTime(1_000_000)

        // 11. Еще больше времени...
        universe.passTime(6_500_000)

        // 12. Получаем ответ!
        val computer = universe.supercomputer!!
        assertTrue(computer.isCalculationComplete)
        assertEquals(42, computer.getAnswer())

        // 13. Проверяем статус
        val status = universe.getStatus()
        assertTrue(status.contains("42"))
    }

    @Test
    @DisplayName("Сценарий: Брокианский ультра-крикет отвлекает от споров")
    fun testUltraCricketScenario() {
        val universe = Universe("Fun Universe")
        val planet = Planet("Earth", 5.972e24)
        universe.addPlanet(planet)

        // Добавляем существ
        val players = (1..5).map {
            Habitant("Player$it", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 10)
        }
        players.forEach { planet.addInhabitant(it) }

        // Начинаем дискуссию
        val debate = Debate()
        players.forEach { debate.addParticipant(it) }
        debate.start()

        // Много раундов - все устали
        repeat(10) { debate.conductRound() }

        // Турнир по брокианскому ультра-крикету!
        planet.conductUltraCricketTournament()

        // favoriteActivity больше не меняется - это константа для каждого существа
        // но турнир все равно влияет на frustration
    }

    @Test
    @DisplayName("Сценарий: Взаимодействие разных рас")
    fun testMultiRaceScenario() {
        val universe = Universe("Diverse Universe")
        val earth = Planet("Earth", 5.972e24)
        universe.addPlanet(earth)

        // Разные расы
        earth.addInhabitant(Habitant("Arthur", Race.HUMAN))
        earth.addInhabitant(Habitant("Ford", Race.HUMAN))
        earth.addInhabitant(
            Habitant("Philosopher", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 15),
        )
        earth.addInhabitant(Habitant("Flipper", Race.DOLPHIN))

        assertEquals(4, earth.inhabitants.size)
        assertEquals(3, earth.inhabitedRaces.size)

        // Только гиперразумные существа могут построить суперкомпьютер
        val buildResult = universe.buildSupercomputer("Deep Thought")
        assertTrue(buildResult.contains("успешно"))
    }

    @Test
    @DisplayName("Сценарий: История событий вселенной")
    fun testUniverseHistory() {
        val universe = Universe("Historical Universe")
        val planet = Planet("Magrathea", 5.972e24)

        universe.addPlanet(planet)
        planet.addInhabitant(
            Habitant("Being", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 15),
        )

        universe.buildSupercomputer("Deep Thought")
        universe.startSearchingForAnswer()
        universe.passTime(1000)

        val history = universe.history

        assertTrue(history.isNotEmpty())
        assertTrue(history.any { it.contains("Планета") })
        assertTrue(history.any { it.contains("суперкомпьютер") })
        assertTrue(history.any { it.contains("поиск ответа") })
    }

    @Test
    @DisplayName("Сценарий: Попытка построить второй суперкомпьютер")
    fun testBuildSecondSupercomputer() {
        val universe = Universe("Universe")
        val planet = Planet("Planet", 1e24)
        universe.addPlanet(planet)

        planet.addInhabitant(
            Habitant("Being", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 15),
        )

        universe.buildSupercomputer("Deep Thought")
        val secondAttempt = universe.buildSupercomputer("Deep Thought 2")

        assertTrue(secondAttempt.contains("уже существует"))
    }

    @Test
    @DisplayName("Сценарий: Вопросы к суперкомпьютеру на разных этапах")
    fun testAskingQuestionsScenario() {
        val universe = Universe("Questioning Universe")
        val planet = Planet("Planet", 1e24)
        universe.addPlanet(planet)

        planet.addInhabitant(
            Habitant("Being", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 15),
        )

        universe.buildSupercomputer("Deep Thought")
        val computer = universe.supercomputer!!

        // До запуска
        val response1 = computer.askQuestion("What is the answer?")
        assertTrue(response1.contains("не начал"))

        // Запускаем
        computer.startCalculation()
        computer.tick(100)

        // Во время вычисления
        val response2 = computer.askQuestion("What is the answer?")
        assertTrue(response2.contains("думаю"))

        // После завершения
        computer.tick(10_000_000)
        val response3 = computer.askQuestion("What is the answer?")
        assertTrue(response3.contains("42"))
    }

    @Test
    @DisplayName("Сценарий: Отдых восстанавливает силы")
    fun testRestingScenario() {
        val being = Habitant("Tired Being", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS)

        // Устаем от споров
        repeat(10) { being.contemplate() }
        val highFrustration = being.frustrationLevel

        // Отдыхаем
        repeat(20) { being.rest() }

        assertTrue(being.frustrationLevel < highFrustration)
        // favoriteActivity не меняется от rest() - это константа
    }

    @Test
    @DisplayName("Сценарий: Размышления повышают уровень знаний")
    fun testContemplationScenario() {
        val being = Habitant("Thinker", Race.HUMAN, knowledgeLevel = 5)

        val initialKnowledge = being.knowledgeLevel

        repeat(10) { being.contemplate() }

        assertTrue(being.knowledgeLevel > initialKnowledge)
        // favoriteActivity задается при создании, contemplate() его не меняет
    }

    @Test
    @DisplayName("Сценарий: Получение планеты по имени")
    fun testGetPlanetByName() {
        val universe = Universe("Universe")
        val earth = Planet("Earth", 5.972e24)
        val mars = Planet("Mars", 6.39e23)

        universe.addPlanet(earth)
        universe.addPlanet(mars)

        val foundPlanet = universe.getPlanetByName("Earth")

        assertNotNull(foundPlanet)
        assertEquals("Earth", foundPlanet?.name)
        assertEquals(5.972e24, foundPlanet?.mass)
    }

    @Test
    @DisplayName("Сценарий: Средняя степень разочарования на планете")
    fun testAverageFrustrationOnPlanet() {
        val planet = Planet("Frustrated Planet", 1e24)

        planet.addInhabitant(Habitant("Being1", Race.HUMAN).apply { frustrationLevel = 10 })
        planet.addInhabitant(Habitant("Being2", Race.HUMAN).apply { frustrationLevel = 20 })
        planet.addInhabitant(Habitant("Being3", Race.HUMAN).apply { frustrationLevel = 30 })

        val avgFrustration = planet.averageFrustrationLevel

        assertEquals(20.0, avgFrustration, 0.01)
    }
}
