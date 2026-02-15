package com.arekalov.tpolab1.universe

/**
 * Альтернативный сценарий: отвлечение от споров через игру
 */
fun alternativeScenario() {
    println("\n" + "=".repeat(60))
    println("  Альтернативная история: Брокианский ультра-крикет")
    println("=".repeat(60))
    println()

    // Создаем простую вселенную
    val universe = Universe("Веселая Вселенная")
    val earth = Planet("Земля", 5.972e24)
    universe.addPlanet(earth)

    // Добавляем разных существ с разными предпочтениями
    println("👥 Создание обитателей...")
    val arthur = Habitant("Arthur Dent", Race.HUMAN, TypeOfActivity.RESTING)
    val ford = Habitant("Ford Prefect", Race.HUMAN, TypeOfActivity.STUDYING)
    val zaphod = Habitant("Zaphod Beeblebrox", Race.HUMAN, TypeOfActivity.PLAYING_BROCKIAN_ULTRA_CRICKET)

    listOf(arthur, ford, zaphod).forEach {
        earth.addInhabitant(it)
        println("   + ${it.name} (любит: ${it.favoriteActivity})")
    }
    println()

    // Начинаем спорить через дебаты
    println("💬 Начинается спор о смысле жизни...")
    val debate = Debate("Почему мы здесь?")
    listOf(arthur, ford, zaphod).forEach { debate.addParticipant(it) }
    debate.start()

    repeat(8) { debate.conductRound() }

    println("   Arthur: frustration = ${arthur.frustrationLevel}")
    println("   Ford: frustration = ${ford.frustrationLevel}")
    println("   Zaphod: frustration = ${zaphod.frustrationLevel}")
    println()

    // Играем в крикет!
    println("🏏 Давайте сыграем в брокианский ультра-крикет!")
    println()

    val result1 = arthur.playBrockianUltraCricket(ford)
    println("   $result1")

    val result2 = ford.playBrockianUltraCricket(zaphod)
    println("   $result2")

    val result3 = zaphod.playBrockianUltraCricket(arthur)
    println("   $result3")

    println()
    println("😊 После игры:")
    println("   Arthur: frustration = ${arthur.frustrationLevel}")
    println("   Ford: frustration = ${ford.frustrationLevel}")
    println("   Zaphod: frustration = ${zaphod.frustrationLevel}")
    println()

    // Отдыхаем
    println("💤 Все отдыхают...")
    repeat(15) {
        arthur.rest()
        ford.rest()
        zaphod.rest()
    }

    println("   Arthur: frustration = ${arthur.frustrationLevel}")
    println("   Ford: frustration = ${ford.frustrationLevel}")
    println("   Zaphod: frustration = ${zaphod.frustrationLevel}")
    println()

    println("=".repeat(60))
}

/**
 * Третий сценарий: развитие через размышления
 */
fun contemplationScenario() {
    println("\n" + "=".repeat(60))
    println("  Путь знания: от невежества к мудрости")
    println("=".repeat(60))
    println()

    val philosopher = Habitant("Сократ", Race.HUMAN, TypeOfActivity.CONTEMPLATING, knowledgeLevel = 5)

    println("🧠 ${philosopher.name} начинает размышлять о природе вещей...")
    println("   Начальный уровень знаний: ${philosopher.knowledgeLevel}")
    println("   Любимое занятие: ${philosopher.favoriteActivity}")
    println()

    repeat(10) { round ->
        philosopher.contemplate()
        if ((round + 1) % 3 == 0) {
            println("   День ${round + 1}: уровень знаний = ${philosopher.knowledgeLevel}")
        }
    }

    println()
    println("✨ Финальный уровень знаний: ${philosopher.knowledgeLevel}")

    if (philosopher.race == Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS &&
        philosopher.canBuildSupercomputer()
    ) {
        println("🎓 ${philosopher.name} теперь может построить суперкомпьютер!")
    } else {
        println("📚 ${philosopher.name} продолжает путь познания...")
    }

    println()
    println("=".repeat(60))
}

/**
 * Главная функция с выбором сценария
 */
fun runDemo() {
    println("\n╔════════════════════════════════════════════════════════════╗")
    println("║   Доменная модель: Вселенная и поиск смысла жизни       ║")
    println("╚════════════════════════════════════════════════════════════╝")

    // Основной сценарий
    main()

    // Дополнительные сценарии
    alternativeScenario()
    contemplationScenario()

    println("\n" + "=".repeat(60))
    println("  Спасибо за внимание!")
    println("=".repeat(60))
}
