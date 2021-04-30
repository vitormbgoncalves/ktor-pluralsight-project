val kotlin_version: String by project
val logback_version: String by project
val kluent_version: String by project
val spek_version: String by project
val mockk_version: String by project
val koin_version: String by project

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }
}

plugins {
    java
    jacoco
}

jacoco {
    toolVersion = "0.8.6"
}

allprojects {
    group = "com.github.vitormbgoncalves"
    version = "0.0.1"

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "jacoco")

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
        implementation("org.koin:koin-ktor:$koin_version")

        testImplementation("org.amshove.kluent:kluent:$kluent_version")
        testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
        testImplementation("io.mockk:mockk:$mockk_version")

        testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version")
    }

    tasks {
        withType<Test> {
            useJUnitPlatform {
                includeEngines("spek2")
            }
        }
        test {
            finalizedBy(jacocoTestReport) // report is always generated after tests run
        }
        jacocoTestReport {
            dependsOn(test) // tests are required to run before generating the report
        }
    }
}

subprojects {
    version = "1.0"
}

project(":todolist-shared") {
}

project(":oauth-client") {
}

project(":todolist-restapi") {
    dependencies {
        implementation(project(":todolist-shared"))
        implementation(project(":dataaccess-service"))
        implementation(project(":repository"))
    }
}

project(":todolist-web") {
    dependencies {
        implementation(project(":todolist-shared"))
        implementation(project(":dataaccess-service"))
        implementation(project(":repository"))
        implementation(project(":oauth-client"))
    }
}

project(":dataaccess-service") {
    dependencies {
        implementation(project(":todolist-shared"))
        implementation(project(":repository"))
        implementation(project(":oauth-client"))
    }
}