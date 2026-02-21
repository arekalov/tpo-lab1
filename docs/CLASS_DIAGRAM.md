# UML Диаграмма классов доменной модели "Вселенная"

```mermaid
classDiagram
    class Universe {
        +String name
        +Long age
        +Supercomputer? supercomputer
        +Int totalInhabitants
        +Int planetsCount
        +Boolean isTiredOfDebates
        +List~String~ history
        +List~Planet~ planets
        +List~Habitant~ allInhabitants
        +addPlanet(planet: Planet)
        +getPlanetByName(name: String): Planet?
        +getInhabitantCountByRace(race: Race): Int
        +buildSupercomputer(name: String): String
        +startSearchingForAnswer(): String
        +conductUniversalUltraCricketTournament(): String
        +passTime(timeUnits: Long): String
        +getStatus(): String
    }

    class Planet {
        +String name
        +Double mass
        +MutableList~Habitant~ inhabitants
        +Int inhabitantCount
        +List~Race~ inhabitedRaces
        +Boolean hasFrustratedInhabitants
        +Double averageFrustrationLevel
        +addInhabitant(habitant: Habitant)
        +getInhabitantCountByRace(race: Race): Int
        +conductUltraCricketTournament(): String
    }

    class Habitant {
        +String name
        +Race race
        +TypeOfActivity favoriteActivity
        +Int frustrationLevel
        +Int knowledgeLevel
        +Boolean isTiredOfDebates
        +Boolean canBuildSupercomputer
        +playBrockianUltraCricket(target: Habitant): String
        +rest()
        +contemplate()
    }

    class Supercomputer {
        +String name
        +Double progressPercentage
        +Boolean isCalculationComplete
        +Boolean isCurrentlyCalculating
        +startCalculation(): String
        +tick(timeUnits: Int): Int
        +getAnswer(): Int?
        +reset()
        +askQuestion(question: String): String
    }

    class Debate {
        +String topic
        +MutableList~Habitant~ participants
        +Boolean isActive
        +Int roundsCompleted
        +Int participantCount
        +Boolean needsDrasticMeasures
        +Map~TypeOfActivity, Int~ favoriteActivitiesStats
        +addParticipant(habitant: Habitant): String
        +start(): String
        +conductRound(): String
        +end(): String
    }

    class Race {
        <<enumeration>>
        HUMAN
        HYPERINTELLIGENT_PANDIMENSIONAL_BEINGS
        VOGON
        BETELGEUSIAN
        DOLPHIN
        MOUSE
    }

    class TypeOfActivity {
        <<enumeration>>
        CONTEMPLATING
        RESTING
        BUILDING
        STUDYING
        PLAYING_BROCKIAN_ULTRA_CRICKET
    }

    Universe "1" *-- "0..*" Planet
    Universe "1" o-- "0..1" Supercomputer
    Planet "1" *-- "0..*" Habitant
    Habitant "1" --> "1" Race
    Habitant "1" --> "1" TypeOfActivity
    Debate "1" o-- "0..*" Habitant
    
    Universe ..> Habitant
    Universe ..> Race
    Debate ..> TypeOfActivity
```

## Описание связей

### Композиция (◆—)
- **Universe → Planet**: Вселенная содержит планеты. При удалении вселенной удаляются и все планеты.
- **Planet → Habitant**: Планета населена обитателями. При удалении планеты удаляются все её обитатели.

### Агрегация (◇—)
- **Universe → Supercomputer**: Вселенная может иметь суперкомпьютер, но суперкомпьютер может существовать независимо.
- **Debate → Habitant**: Дискуссия включает участников, но обитатели существуют независимо от дискуссий.

### Ассоциация (—)
- **Habitant → Race**: Каждый обитатель принадлежит к определённой расе.
- **Habitant → TypeOfActivity**: Каждый обитатель имеет любимый вид деятельности.

### Зависимость (- - >)
- **Universe ..> Habitant**: Universe использует методы Habitant для проверки возможностей.
- **Universe ..> Race**: Universe подсчитывает обитателей по расам.
- **Debate ..> TypeOfActivity**: Debate анализирует статистику любимых занятий.
