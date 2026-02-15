package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Тесты Дискуссии")
class DebateTest {

    private lateinit var debate: Debate
    private lateinit var being1: Habitant
    private lateinit var being2: Habitant

    @BeforeEach
    fun setup() {
        debate = Debate()
        being1 = Habitant("Philosopher1", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS)
        being2 = Habitant("Philosopher2", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS)
    }

    @Test
    @DisplayName("Создание дискуссии")
    fun testCreateDebate() {
        assertEquals("Смысл жизни, вселенной и всего такого", debate.topic)
        assertEquals(0, debate.participantCount)
        assertFalse(debate.isActive)
    }

    @Test
    @DisplayName("Добавление участника")
    fun testAddParticipant() {
        val result = debate.addParticipant(being1)

        assertTrue(result.contains("присоединился"))
        assertEquals(1, debate.participantCount)
    }

    @Test
    @DisplayName("Добавление дубликата участника")
    fun testAddDuplicateParticipant() {
        debate.addParticipant(being1)
        val result = debate.addParticipant(being1)

        assertTrue(result.contains("уже участвует"))
        assertEquals(1, debate.participantCount)
    }

    @Test
    @DisplayName("Начало дискуссии без участников")
    fun testStartDebateWithoutParticipants() {
        val result = debate.start()

        assertTrue(result.contains("Нет участников"))
        assertFalse(debate.isActive)
    }

    @Test
    @DisplayName("Начало дискуссии")
    fun testStartDebate() {
        debate.addParticipant(being1)
        debate.addParticipant(being2)

        val result = debate.start()

        assertTrue(result.contains("началась"))
        assertTrue(debate.isActive)
        assertEquals(TypeOfActivity.CONTEMPLATING, being1.favoriteActivity)
        assertEquals(TypeOfActivity.CONTEMPLATING, being2.favoriteActivity)
    }

    @Test
    @DisplayName("Повторный запуск активной дискуссии")
    fun testRestartActiveDebate() {
        debate.addParticipant(being1)
        debate.start()

        val result = debate.start()

        assertTrue(result.contains("уже идет"))
    }

    @Test
    @DisplayName("Проведение раунда дискуссии")
    fun testConductRound() {
        debate.addParticipant(being1)
        debate.addParticipant(being2)
        debate.start()

        val initialFrustration1 = being1.frustrationLevel
        val initialKnowledge1 = being1.knowledgeLevel

        debate.conductRound()

        assertEquals(1, debate.roundsCompleted)
        assertTrue(being1.frustrationLevel > initialFrustration1)
        assertTrue(being1.knowledgeLevel > initialKnowledge1)
    }

    @Test
    @DisplayName("Проведение раунда неактивной дискуссии")
    fun testConductRoundInactive() {
        debate.addParticipant(being1)

        val result = debate.conductRound()

        assertTrue(result.contains("не активна"))
    }

    @Test
    @DisplayName("Завершение дискуссии")
    fun testEndDebate() {
        debate.addParticipant(being1)
        debate.addParticipant(being2)
        debate.start()
        debate.conductRound()

        val result = debate.end()

        assertTrue(result.contains("завершена"))
        assertFalse(debate.isActive)
        assertEquals(TypeOfActivity.RESTING, being1.favoriteActivity)
        assertEquals(TypeOfActivity.RESTING, being2.favoriteActivity)
    }

    @Test
    @DisplayName("Завершение неактивной дискуссии")
    fun testEndInactiveDebate() {
        debate.addParticipant(being1)

        val result = debate.end()

        assertTrue(result.contains("уже завершена"))
    }

    @Test
    @DisplayName("Проверка необходимости кардинальных мер")
    fun testNeedsDrasticMeasures() {
        debate.addParticipant(being1)
        debate.start()

        assertFalse(debate.needsDrasticMeasures())

        // Проводим много раундов, чтобы участники устали
        repeat(15) { debate.conductRound() }

        assertTrue(debate.needsDrasticMeasures())
    }

    @Test
    @DisplayName("Множественные раунды дискуссии")
    fun testMultipleRounds() {
        debate.addParticipant(being1)
        debate.addParticipant(being2)
        debate.start()

        repeat(5) { debate.conductRound() }

        assertEquals(5, debate.roundsCompleted)
    }

    @Test
    @DisplayName("Получение списка участников")
    fun testGetParticipants() {
        debate.addParticipant(being1)
        debate.addParticipant(being2)

        val participants = debate.participants

        assertEquals(2, participants.size)
        assertTrue(participants.contains(being1))
        assertTrue(participants.contains(being2))
    }

    @Test
    @DisplayName("Дискуссия с кастомной темой")
    fun testCustomTopic() {
        val customDebate = Debate("Почему небо голубое?")

        assertEquals("Почему небо голубое?", customDebate.topic)
    }

    @Test
    @DisplayName("Влияние длительной дискуссии на участников")
    fun testLongDebateEffect() {
        debate.addParticipant(being1)
        debate.start()

        val initialFrustration = being1.frustrationLevel
        val initialKnowledge = being1.knowledgeLevel

        repeat(10) { debate.conductRound() }

        assertTrue(being1.frustrationLevel > initialFrustration + 40)
        assertTrue(being1.knowledgeLevel > initialKnowledge + 5)
    }
}
