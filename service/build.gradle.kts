import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":domain"))
    api(project(":persistence"))
    api(project(":api"))
    api(project(":common"))
}

tasks.withType<BootJar> {
    enabled = false
}