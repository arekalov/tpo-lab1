plugins {
    kotlin("jvm") version "2.0.21"
    jacoco
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

group = "com.arekalov.tpolab1"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    
    // Detekt
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "1.00".toBigDecimal()  // 100% покрытие
            }
        }
    }
}

// Настройка Detekt
detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/detekt.yml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html {
            required.set(true)
            outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.html"))
        }
        txt {
            required.set(true)
            outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.txt"))
        }
        xml.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
    
    jvmTarget = "17"
}

tasks.named("check") {
    setDependsOn(dependsOn.filterNot { it.toString().contains("detekt") })
}

tasks.register("reports") {
    group = "reporting"
    description = "Генерирует JUnit и JaCoCo отчёты и открывает их в браузере"
    
    dependsOn(tasks.test, tasks.jacocoTestReport)
    
    doLast {
        val junitReport = file("build/reports/tests/test/index.html")
        val jacocoReport = file("build/reports/jacoco/test/html/index.html")
        
        if (junitReport.exists()) {
            println("📊 Открываю JUnit отчёт...")
            exec {
                commandLine("open", junitReport.absolutePath)
            }
        } else {
            println("⚠️  JUnit отчёт не найден: ${junitReport.absolutePath}")
        }
        
        if (jacocoReport.exists()) {
            println("📈 Открываю JaCoCo отчёт...")
            exec {
                commandLine("open", jacocoReport.absolutePath)
            }
        } else {
            println("⚠️  JaCoCo отчёт не найден: ${jacocoReport.absolutePath}")
        }
        
        println("✅ Отчёты открыты в браузере!")
    }
}

kotlin {
    jvmToolchain(17)
}