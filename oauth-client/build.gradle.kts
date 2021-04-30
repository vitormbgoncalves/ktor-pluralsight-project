import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by project
val fuel_version: String by project
val klaxon_version: String by project
val arrow_version: String by project

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.github.kittinunf.fuel:fuel:$fuel_version")
    implementation("com.beust:klaxon:$klaxon_version")
    implementation("io.arrow-kt:arrow-core:$arrow_version")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}