import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.springframework.data:spring-data-commons")
}

tasks.withType<BootJar> {
    enabled = false
}