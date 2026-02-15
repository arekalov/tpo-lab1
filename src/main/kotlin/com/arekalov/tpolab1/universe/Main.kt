package com.arekalov.tpolab1.universe

/**
 * Пример использования доменной модели "Вселенная"
 */
@Suppress("LongMethod", "MagicNumber")
fun main() {
    println("=".repeat(60))
    println("  История о гиперразумных существах и смысле жизни")
    println("=".repeat(60))
    println()

    // 1. Создаем вселенную
    println("📍 Создание вселенной...")
    val universe = Universe("Всемерная Вселенная")
    println("✓ Создана вселенная '${universe.name}'")
    println()

    // 2. Создаем планету Magrathea
    println("🌍 Создание планеты Magrathea...")
    val magrathea = Planet("Magrathea", 5.972e24)
    universe.addPlanet(magrathea)
    println("✓ Планета добавлена во вселенную")
    println()

    // 3. Добавляем гиперразумных существ с разными предпочтениями
    println("👥 Создание гиперразумных существ...")
    val slartibartfast = Habitant(
        "Slartibartfast",
        Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
        TypeOfActivity.BUILDING,
        knowledgeLevel = 15,
    )
    val frankie = Habitant(
        "Frankie",
        Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
        TypeOfActivity.CONTEMPLATING,
        knowledgeLevel = 12,
    )
    val benjy = Habitant(
        "Benjy",
        Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
        TypeOfActivity.STUDYING,
        knowledgeLevel = 13,
    )

    magrathea.addInhabitant(slartibartfast)
    magrathea.addInhabitant(frankie)
    magrathea.addInhabitant(benjy)
    println("✓ Добавлено ${magrathea.inhabitantCount} существ на планету")

    // Показываем любимые занятия
    println()
    println("   Любимые занятия:")
    listOf(slartibartfast, frankie, benjy).forEach {
        println("   - ${it.name}: ${it.favoriteActivity}")
    }
    println()

    // 4. Существа начинают спорить о смысле жизни
    println("💬 Начинается дискуссия о смысле жизни...")
    val debate = Debate("Смысл жизни, вселенной и всего такого")

    listOf(slartibartfast, frankie, benjy).forEach { being ->
        debate.addParticipant(being)
        println("   - ${being.name} присоединился к дискуссии")
    }

    debate.start()
    println("✓ Дискуссия началась!")
    println()

    // 5. Дискуссия продолжается много раундов
    println("⏳ Дискуссия продолжается раунд за раундом...")
    repeat(12) { round ->
        debate.conductRound()
        if ((round + 1) % 4 == 0) {
            val avgFrustration = magrathea.inhabitants.map { it.frustrationLevel }.average()
            println("   Раунд ${round + 1} завершен. Средний frustration: ${"%.1f".format(avgFrustration)}")
        }
    }
    println()

    // 6. Проверяем, устали ли существа
    println("😓 Проверка состояния существ...")
    magrathea.inhabitants.forEach { being ->
        val tired = if (being.isTiredOfDebates()) "устал" else "не устал"
        println(
            "   ${being.name}: frustration=${being.frustrationLevel}, " +
                "knowledge=${being.knowledgeLevel}, $tired",
        )
    }
    println()

    // Показываем статистику любимых занятий из дебатов
    println("📊 Статистика любимых занятий участников дискуссии:")
    debate.getFavoriteActivitiesStats().forEach { (activity, count) ->
        println("   $activity: $count чел.")
    }
    println()

    // 7. Существа устали! Нужно принять кардинальные меры
    if (debate.needsDrasticMeasures()) {
        println("⚠️  Существа устали от бесконечных споров!")
        println("💡 Решение: построить суперкомпьютер для расчета ответа")
        println()

        debate.end()
        println("✓ Дискуссия завершена")
        println()

        // 8. Строим суперкомпьютер
        println("🖥️  Строительство суперкомпьютера...")
        val buildResult = universe.buildSupercomputer("Deep Thought")
        println("✓ $buildResult")
        println()

        // 9. Запускаем вычисление
        println("🔍 Запуск вычисления ответа на главный вопрос...")
        universe.startSearchingForAnswer()
        val computer = universe.getSupercomputer()!!
        println("✓ ${computer.name} начал размышления")
        println()

        // 10. Симулируем прохождение времени
        println("⏰ Проходят миллионы лет...")
        println()

        // Первые 2 миллиона лет
        universe.passTime(2_000_000)
        println("   Прошло 2 млн лет. Прогресс: ${"%.1f".format(computer.getProgressPercentage())}%")

        // Еще 3 миллиона лет
        universe.passTime(3_000_000)
        println("   Прошло 5 млн лет. Прогресс: ${"%.1f".format(computer.getProgressPercentage())}%")

        // Остаток времени
        universe.passTime(2_500_000)
        println("   Прошло 7.5 млн лет. Прогресс: ${"%.1f".format(computer.getProgressPercentage())}%")
        println()

        // 11. Получаем ответ!
        if (computer.isCalculationComplete()) {
            println("🎉 Вычисление завершено!")
            println()

            val question = "Каков ответ на главный вопрос жизни, вселенной и всего такого?"
            val response = computer.askQuestion(question)

            println("❓ Вопрос: $question")
            println("💬 ${computer.name}: $response")
            println()
        }

        // 12. Финальный статус вселенной
        println("=".repeat(60))
        println("ФИНАЛЬНОЕ СОСТОЯНИЕ ВСЕЛЕННОЙ")
        println("=".repeat(60))
        println(universe.getStatus())

        // 13. История событий
        println()
        println("📜 ИСТОРИЯ СОБЫТИЙ:")
        println("-".repeat(60))
        universe.getHistory().takeLast(5).forEach { event ->
            println(event)
        }
    }

    println()
    println("=".repeat(60))
    println("  Конец истории")
    println("=".repeat(60))
}
