import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.withType<BootJar> {
    enabled = false
}