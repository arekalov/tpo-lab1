plugins {
    kotlin("jvm") version "2.0.21"
    jacoco
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