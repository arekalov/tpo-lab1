# Примеры использования доменной модели "Вселенная"

## Быстрый старт

### Пример 1: Минимальный сценарий

```kotlin
// Создаем вселенную
val universe = Universe("Моя Вселенная")

// Создаем планету
val planet = Planet("Земля", 5.972e24)
universe.addPlanet(planet)

// Создаем обитателя
val being = Habitant(
    "Философ",
    Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS,
    knowledgeLevel = 15
)
planet.addInhabitant(being)

// Строим суперкомпьютер
universe.buildSupercomputer("Deep Thought")
universe.startSearchingForAnswer()

// Симулируем время
universe.passTime(7_500_000)

// Получаем ответ
val computer = universe.getSupercomputer()!!
println(computer.getAnswer()) // 42
```

### Пример 2: Дискуссия и усталость

```kotlin
// Создаем участников
val alice = Habitant("Alice", Race.HUMAN)
val bob = Habitant("Bob", Race.HUMAN)

// Начинаем дискуссию
val debate = Debate("Смысл жизни")
debate.addParticipant(alice)
debate.addParticipant(bob)
debate.start()

// Проводим раунды
repeat(10) { debate.conductRound() }

// Проверяем усталость
if (debate.needsDrasticMeasures()) {
    println("Участники устали!")
    debate.end()
}

println("Alice frustration: ${alice.frustrationLevel}")
println("Bob frustration: ${bob.frustrationLevel}")
```

### Пример 3: Брокианский ультра-крикет

```kotlin
val player1 = Habitant("Игрок 1", Race.HUMAN)
val player2 = Habitant("Игрок 2", Race.HUMAN)

// Спорим (повышаем frustration)
repeat(5) { player1.startDebating() }
println("До игры: ${player1.frustrationLevel}")

// Играем в крикет (снижаем frustration)
val result = player1.playBrockianUltraCricket(player2)
println(result) // "Игрок 1 неожиданно ударяет Игрок 2 без видимой причины и убегает!"

println("После игры: ${player1.frustrationLevel}")
println("Активность: ${player1.currentActivity}") // PLAYING_BROCKIAN_ULTRA_CRICKET
```

### Пример 4: Развитие через размышления

```kotlin
val philosopher = Habitant("Сократ", Race.HUMAN, knowledgeLevel = 5)

println("Начальный уровень: ${philosopher.knowledgeLevel}")

// Размышляем
repeat(10) { philosopher.contemplate() }

println("Финальный уровень: ${philosopher.knowledgeLevel}") // 15

// Проверяем возможность построить суперкомпьютер
if (philosopher.canBuildSupercomputer()) {
    println("Может построить суперкомпьютер!")
}
```

### Пример 5: Турнир на планете

```kotlin
val planet = Planet("Арена", 1e24)

// Добавляем участников
repeat(5) { i ->
    planet.addInhabitant(Habitant("Участник $i", Race.HUMAN))
}

// Проводим турнир
val result = planet.conductUltraCricketTournament()
println(result) // "Турнир завершен! События: 20"

// Все участники теперь играют в крикет
planet.inhabitants.forEach { inhabitant ->
    println("${inhabitant.name}: ${inhabitant.currentActivity}")
}
```

### Пример 6: Отдых и восстановление

```kotlin
val tiredBeing = Habitant("Уставший", Race.HUMAN)

// Устаем
repeat(20) { tiredBeing.startDebating() }
println("Frustration: ${tiredBeing.frustrationLevel}") // Высокий уровень

// Отдыхаем
repeat(30) { tiredBeing.rest() }
println("После отдыха: ${tiredBeing.frustrationLevel}") // Снизился

// Проверяем состояние
println("Устал от споров: ${tiredBeing.isTiredOfDebates()}")
```

### Пример 7: Статистика планеты

```kotlin
val planet = Planet("Многонаселенная", 1e24)

// Добавляем разные расы
planet.addInhabitant(Habitant("Human", Race.HUMAN))
planet.addInhabitant(Habitant("Dolphin", Race.DOLPHIN))
planet.addInhabitant(Habitant("Being", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS))

// Получаем статистику
println("Всего обитателей: ${planet.inhabitants.size}")
println("Обитаемые расы: ${planet.getInhabitedRaces()}")
println("Людей: ${planet.getInhabitantCountByRace(Race.HUMAN)}")
println("Средний frustration: ${planet.getAverageFrustrationLevel()}")
```

### Пример 8: История событий вселенной

```kotlin
val universe = Universe("История")
val planet = Planet("Планета", 1e24)

universe.addPlanet(planet)
planet.addInhabitant(Habitant("Существо", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 15))

universe.buildSupercomputer("Computer")
universe.startSearchingForAnswer()
universe.passTime(1000)

// Получаем историю
val history = universe.getHistory()
history.forEach { event ->
    println(event)
}
```

### Пример 9: Вопросы к суперкомпьютеру

```kotlin
val computer = Supercomputer("Deep Thought")

// До запуска
println(computer.askQuestion("Что такое жизнь?"))
// "Я еще не начал вычисления..."

// Запускаем
computer.startCalculation()
computer.tick(1000)

// Во время вычисления
println(computer.askQuestion("Что такое жизнь?"))
// "Я все еще думаю... Прогресс: X%"

// После завершения
computer.tick(10_000_000)
println(computer.askQuestion("Что такое жизнь?"))
// "Ответ на вопрос 'Что такое жизнь?' равен 42"
```

### Пример 10: Полный цикл с проверками

```kotlin
fun fullCycle() {
    val universe = Universe("Полный цикл")
    val planet = Planet("Планета", 1e24)
    universe.addPlanet(planet)

    // Добавляем существ
    val beings = (1..3).map { i ->
        Habitant("Существо $i", Race.HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS, knowledgeLevel = 15)
    }
    beings.forEach { planet.addInhabitant(it) }

    // Дискуссия
    val debate = Debate()
    beings.forEach { debate.addParticipant(it) }
    debate.start()

    repeat(15) { debate.conductRound() }

    // Проверяем усталость
    if (universe.isTiredOfDebates()) {
        debate.end()

        // Строим компьютер
        val buildResult = universe.buildSupercomputer("Deep Thought")
        require(buildResult.contains("успешно"))

        // Запускаем вычисление
        universe.startSearchingForAnswer()

        // Ждем результата
        universe.passTime(7_500_000)

        val computer = universe.getSupercomputer()!!
        require(computer.isCalculationComplete())
        require(computer.getAnswer() == 42)

        println("✓ Успешно получен ответ: ${computer.getAnswer()}")
    }

    // Финальный статус
    println(universe.getStatus())
}
```

## Основные паттерны использования

### 1. Управление состоянием

```kotlin
// Проверка состояния
if (being.isTiredOfDebates()) {
    being.rest()
}

if (being.canBuildSupercomputer()) {
    universe.buildSupercomputer("Computer")
}
```

### 2. Цепочки действий

```kotlin
being.contemplate()
    .also { println("Knowledge: ${being.knowledgeLevel}") }

if (being.knowledgeLevel >= 10) {
    being.startDebating()
}
```

### 3. Временная симуляция

```kotlin
// Короткие интервалы
universe.passTime(1000)

// Длинные периоды
universe.passTime(1_000_000)

// С проверкой прогресса
val computer = universe.getSupercomputer()!!
while (!computer.isCalculationComplete()) {
    universe.passTime(100_000)
    println("Прогресс: ${computer.getProgressPercentage()}%")
}
```

## Запуск демонстрации

```bash
# Основная демонстрация
./gradlew run

# Тихий режим (без gradle логов)
./gradlew run -q
```

## Тестирование

Все примеры покрыты тестами в:
- `UniverseTest.kt` - базовые операции
- `SupercomputerTest.kt` - работа компьютера
- `DebateTest.kt` - дискуссии
- `UniverseIntegrationTest.kt` - полные сценарии
