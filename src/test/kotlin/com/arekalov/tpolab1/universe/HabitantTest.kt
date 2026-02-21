package com.arekalov.tpolab1.universe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class HabitantTest {

    private fun createHabitant(
        name: String = "Test Habitant",
        race: Race = Race.HUMAN,
        favoriteActivity: TypeOfActivity = TypeOfActivity.CONTEMPLATING,
        frustrationLevel: Int = 10,
        knowledgeLevel: Int = 5,
    ): Habitant = Habitant(
        name = name,
        race = race,
        favoriteActivity = favoriteActivity,
        frustrationLevel = frustrationLevel,
        knowledgeLevel = knowledgeLevel,
    )

    @Test
    @DisplayName("Создание обитателя с заданными параметрами")
    fun testCreateHabitant() {
        val habitant = createHabitant()

        assertEquals("Test Habitant", habitant.name)
        assertEquals(Race.HUMAN, habitant.race)
        assertEquals(TypeOfActivity.CONTEMPLATING, habitant.favoriteActivity)
        assertEquals(10, habitant.frustrationLevel)
        assertEquals(5, habitant.knowledgeLevel)
    }

    @Test
    @DisplayName("Создание обитателя с дефолтными значениями")
    fun testCreateHabitantWithDefaults() {
        val habitant = Habitant(name = "Default", race = Race.HUMAN)

        assertEquals(TypeOfActivity.CONTEMPLATING, habitant.favoriteActivity)
        assertEquals(0, habitant.frustrationLevel)
        assertEquals(0, habitant.knowledgeLevel)
    }

    @Test
    @DisplayName("isTiredOfDebates = false при frustration <= 50")
    fun testNotTiredOfDebates() {
        val habitant = createHabitant(frustrationLevel = 50)

        assertFalse(habitant.isTiredOfDebates)
    }

    @Test
    @DisplayName("isTiredOfDebates = true при frustration > 50")
    fun testTiredOfDebates() {
        val habitant = createHabitant(frustrationLevel = 51)

        assertTrue(habitant.isTiredOfDebates)
    }

    @Test
    @DisplayName("canBuildSupercomputer = true для гиперразумных существ с knowledge >= 10")
    fun testCanBuildSupercomputer() {
        val habitant = createHabitant(
            race = Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
            knowledgeLevel = 10,
        )

        assertTrue(habitant.canBuildSupercomputer)
    }

    @Test
    @DisplayName("canBuildSupercomputer = false для гиперразумных существ с knowledge < 10")
    fun testCannotBuildSupercomputerLowKnowledge() {
        val habitant = createHabitant(
            race = Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
            knowledgeLevel = 9,
        )

        assertFalse(habitant.canBuildSupercomputer)
    }

    @Test
    @DisplayName("canBuildSupercomputer = false для не гиперразумных рас")
    fun testCannotBuildSupercomputerWrongRace() {
        val human = createHabitant(race = Race.HUMAN, knowledgeLevel = 20)

        assertFalse(human.canBuildSupercomputer)
    }

    @Test
    @DisplayName("playBrockianUltraCricket уменьшает frustration атакующего на 5")
    fun testPlayCricketReducesFrustration() {
        val habitant = createHabitant(frustrationLevel = 20)
        val target = createHabitant(name = "Target")

        habitant.playBrockianUltraCricket(target = target)

        assertEquals(15, habitant.frustrationLevel)
    }

    @Test
    @DisplayName("playBrockianUltraCricket не делает frustration отрицательным")
    fun testPlayCricketMinimumFrustration() {
        val habitant = createHabitant(frustrationLevel = 3)
        val target = createHabitant(name = "Target")

        habitant.playBrockianUltraCricket(target = target)

        assertEquals(0, habitant.frustrationLevel)
    }

    @Test
    @DisplayName("playBrockianUltraCricket увеличивает frustration цели на 3")
    fun testPlayCricketIncreasesTargetFrustration() {
        val habitant = createHabitant()
        val target = createHabitant(name = "Target", frustrationLevel = 10)

        habitant.playBrockianUltraCricket(target = target)

        assertEquals(13, target.frustrationLevel)
    }

    @Test
    @DisplayName("playBrockianUltraCricket возвращает корректное сообщение")
    fun testPlayCricketMessage() {
        val habitant = createHabitant(name = "Attacker")
        val target = createHabitant(name = "Target")

        val result = habitant.playBrockianUltraCricket(target = target)

        assertEquals("Attacker неожиданно ударяет Target без видимой причины и убегает!", result)
    }

    @Test
    @DisplayName("rest уменьшает frustration на 2")
    fun testRestReducesFrustration() {
        val habitant = createHabitant(frustrationLevel = 20)

        habitant.rest()

        assertEquals(18, habitant.frustrationLevel)
    }

    @Test
    @DisplayName("rest не делает frustration отрицательным")
    fun testRestMinimumFrustration() {
        val habitant = createHabitant(frustrationLevel = 1)

        habitant.rest()

        assertEquals(0, habitant.frustrationLevel)
    }

    @Test
    @DisplayName("contemplate увеличивает knowledgeLevel на 1")
    fun testContemplateIncreasesKnowledge() {
        val habitant = createHabitant(knowledgeLevel = 5)

        habitant.contemplate()

        assertEquals(6, habitant.knowledgeLevel)
    }

    @Test
    @DisplayName("contemplate увеличивает frustration на 1")
    fun testContemplateIncreasesFrustration() {
        val habitant = createHabitant(frustrationLevel = 10)

        habitant.contemplate()

        assertEquals(11, habitant.frustrationLevel)
    }

    @Test
    @DisplayName("contemplate изменяет оба параметра одновременно")
    fun testContemplateBothEffects() {
        val habitant = createHabitant(knowledgeLevel = 5, frustrationLevel = 10)

        habitant.contemplate()

        assertEquals(6, habitant.knowledgeLevel)
        assertEquals(11, habitant.frustrationLevel)
    }

    @Test
    @DisplayName("Несколько вызовов contemplate накапливают эффект")
    fun testMultipleContemplates() {
        val habitant = createHabitant(knowledgeLevel = 0, frustrationLevel = 0)

        repeat(5) { habitant.contemplate() }

        assertEquals(5, habitant.knowledgeLevel)
        assertEquals(5, habitant.frustrationLevel)
    }

    @Test
    @DisplayName("Комбинация действий: contemplate и rest")
    fun testCombinedActions() {
        val habitant = createHabitant(knowledgeLevel = 5, frustrationLevel = 10)

        habitant.contemplate()
        habitant.contemplate()
        habitant.rest()

        assertEquals(7, habitant.knowledgeLevel)
        assertEquals(10, habitant.frustrationLevel)
    }
}
