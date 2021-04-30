val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

plugins {
    application
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-mustache:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("com.auth0:java-jwt:3.15.0")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    implementation(kotlin("stdlib-jdk8"))
}