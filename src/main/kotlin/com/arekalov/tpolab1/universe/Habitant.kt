package com.arekalov.tpolab1.universe

/**
 * Обитатель вселенной
 */
data class Habitant(
    val name: String,
    val race: Race,
    val favoriteActivity: TypeOfActivity = TypeOfActivity.CONTEMPLATING,
    var frustrationLevel: Int = 0,
    var knowledgeLevel: Int = 0,
) {
    /**
     * Играть в брокианский ультра-крикет
     */
    fun playBrockianUltraCricket(target: Habitant): String {
        frustrationLevel = maxOf(0, frustrationLevel - 5)
        target.getHitUnexpectedly()
        return "$name неожиданно ударяет ${target.name} без видимой причины и убегает!"
    }

    /**
     * Получить неожиданный удар
     */
    private fun getHitUnexpectedly() {
        frustrationLevel += 3
    }

    /**
     * Отдохнуть
     */
    fun rest() {
        frustrationLevel = maxOf(0, frustrationLevel - 2)
    }

    /**
     * Размышлять
     */
    fun contemplate() {
        knowledgeLevel += 1
    }

    /**
     * Устал ли обитатель от споров?
     */
    fun isTiredOfDebates(): Boolean = frustrationLevel > 50

    /**
     * Может ли участвовать в создании суперкомпьютера?
     */
    fun canBuildSupercomputer(): Boolean =
        race == Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS && knowledgeLevel >= 10
}
