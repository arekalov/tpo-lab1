package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DebateTest {

    private fun createDebate(topic: String = "Смысл жизни, вселенной и всего такого"): Debate =
        Debate(topic)

    private fun createHabitant(
        name: String = "Philosopher",
        race: Race = Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
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
    @DisplayName("Создание дискуссии")
    fun testCreateDebate() {
        val debate = createDebate()

        assertEquals("Смысл жизни, вселенной и всего такого", debate.topic)
        assertEquals(0, debate.participantCount)
        assertFalse(debate.isActive)
    }

    @Test
    @DisplayName("Добавление участника")
    fun testAddParticipant() {
        val debate = createDebate()
        val habitant = createHabitant()

        val result = debate.addParticipant(habitant = habitant)

        assertTrue(result.contains("присоединился"))
        assertEquals(1, debate.participantCount)
    }

    @Test
    @DisplayName("Добавление дубликата участника")
    fun testAddDuplicateParticipant() {
        val debate = createDebate()
        val habitant = createHabitant()

        debate.addParticipant(habitant = habitant)
        val result = debate.addParticipant(habitant = habitant)

        assertTrue(result.contains("уже участвует"))
        assertEquals(1, debate.participantCount)
    }

    @Test
    @DisplayName("Начало дискуссии без участников")
    fun testStartDebateWithoutParticipants() {
        val debate = createDebate()

        val result = debate.start()

        assertTrue(result.contains("Нет участников"))
        assertFalse(debate.isActive)
    }

    @Test
    @DisplayName("Начало дискуссии")
    fun testStartDebate() {
        val debate = createDebate()
        val being1 = createHabitant(name = "Being1")
        val being2 = createHabitant(name = "Being2")
        debate.addParticipant(habitant = being1)
        debate.addParticipant(habitant = being2)

        val result = debate.start()

        assertTrue(result.contains("началась"))
        assertTrue(debate.isActive)
    }

    @Test
    @DisplayName("Повторный запуск активной дискуссии")
    fun testRestartActiveDebate() {
        val debate = createDebate()
        val habitant = createHabitant()
        debate.addParticipant(habitant = habitant)
        debate.start()

        val result = debate.start()

        assertTrue(result.contains("уже идет"))
    }

    @Test
    @DisplayName("Проведение раунда дискуссии")
    fun testConductRound() {
        val debate = createDebate()
        val habitant = createHabitant()
        debate.addParticipant(habitant = habitant)
        debate.start()
        val initialFrustration = habitant.frustrationLevel
        val initialKnowledge = habitant.knowledgeLevel

        debate.conductRound()

        assertEquals(1, debate.roundsCompleted)
        assertEquals(initialFrustration + 5, habitant.frustrationLevel)
        assertEquals(initialKnowledge + 1, habitant.knowledgeLevel)
    }

    @Test
    @DisplayName("Проведение раунда неактивной дискуссии")
    fun testConductRoundInactive() {
        val debate = createDebate()
        val habitant = createHabitant()
        debate.addParticipant(habitant = habitant)

        val result = debate.conductRound()

        assertTrue(result.contains("не активна"))
    }

    @Test
    @DisplayName("Завершение дискуссии")
    fun testEndDebate() {
        val debate = createDebate()
        val habitant = createHabitant()
        debate.addParticipant(habitant = habitant)
        debate.start()
        debate.conductRound()

        val result = debate.end()

        assertTrue(result.contains("завершена"))
        assertFalse(debate.isActive)
    }

    @Test
    @DisplayName("Завершение неактивной дискуссии")
    fun testEndInactiveDebate() {
        val debate = createDebate()
        val habitant = createHabitant()
        debate.addParticipant(habitant = habitant)

        val result = debate.end()

        assertTrue(result.contains("уже завершена"))
    }

    @Test
    @DisplayName("Проверка необходимости кардинальных мер")
    fun testNeedsDrasticMeasures() {
        val debate = createDebate()
        val habitant = createHabitant()
        debate.addParticipant(habitant = habitant)
        debate.start()

        assertFalse(debate.needsDrasticMeasures)

        repeat(15) { debate.conductRound() }

        assertTrue(debate.needsDrasticMeasures)
    }

    @Test
    @DisplayName("Множественные раунды дискуссии")
    fun testMultipleRounds() {
        val debate = createDebate()
        val being1 = createHabitant(name = "Being1")
        val being2 = createHabitant(name = "Being2")
        debate.addParticipant(habitant = being1)
        debate.addParticipant(habitant = being2)
        debate.start()

        repeat(5) { debate.conductRound() }

        assertEquals(5, debate.roundsCompleted)
    }

    @Test
    @DisplayName("Получение списка участников")
    fun testGetParticipants() {
        val debate = createDebate()
        val being1 = createHabitant(name = "Being1")
        val being2 = createHabitant(name = "Being2")

        debate.addParticipant(habitant = being1)
        debate.addParticipant(habitant = being2)
        val participants = debate.participants

        assertEquals(2, participants.size)
        assertTrue(participants.contains(being1))
        assertTrue(participants.contains(being2))
    }

    @Test
    @DisplayName("Дискуссия с кастомной темой")
    fun testCustomTopic() {
        val debate = createDebate(topic = "Почему небо голубое?")

        assertEquals("Почему небо голубое?", debate.topic)
    }

    @Test
    @DisplayName("Влияние длительной дискуссии на участников")
    fun testLongDebateEffect() {
        val debate = createDebate()
        val habitant = createHabitant()
        debate.addParticipant(habitant = habitant)
        debate.start()
        val initialFrustration = habitant.frustrationLevel
        val initialKnowledge = habitant.knowledgeLevel

        repeat(10) { debate.conductRound() }

        assertTrue(habitant.frustrationLevel > initialFrustration + 40)
        assertTrue(habitant.knowledgeLevel > initialKnowledge + 5)
    }

    @Test
    @DisplayName("needsDrasticMeasures = false когда дискуссия неактивна")
    fun testNeedsDrasticMeasuresInactive() {
        val debate = createDebate()
        val habitant = createHabitant(frustrationLevel = 60)
        debate.addParticipant(habitant = habitant)

        val result = debate.needsDrasticMeasures

        assertFalse(result)
    }

    @Test
    @DisplayName("favoriteActivitiesStats возвращает статистику")
    fun testFavoriteActivitiesStats() {
        val debate = createDebate()
        val h1 = createHabitant(name = "H1", favoriteActivity = TypeOfActivity.CONTEMPLATING)
        val h2 = createHabitant(name = "H2", favoriteActivity = TypeOfActivity.CONTEMPLATING)
        val h3 = createHabitant(name = "H3", favoriteActivity = TypeOfActivity.STUDYING)
        debate.addParticipant(habitant = h1)
        debate.addParticipant(habitant = h2)
        debate.addParticipant(habitant = h3)

        val stats = debate.favoriteActivitiesStats

        assertEquals(2, stats[TypeOfActivity.CONTEMPLATING])
        assertEquals(1, stats[TypeOfActivity.STUDYING])
    }

    @Test
    @DisplayName("end() с пустым списком участников")
    fun testEndWithEmptyParticipants() {
        val debate = createDebate()
        val temp = createHabitant(name = "Temp")
        debate.addParticipant(habitant = temp)
        debate.start()
        debate.participants.clear()

        val result = debate.end()

        assertTrue(result.contains("0 раундов"))
        assertTrue(result.contains("0.00"))
    }

    @Test
    @DisplayName("Создание дискуссии с дефолтными параметрами")
    fun testCreateDebateWithDefaults() {
        val debate = Debate()

        assertEquals("Смысл жизни, вселенной и всего такого", debate.topic)
        assertEquals(0, debate.participantCount)
    }
}
