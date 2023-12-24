import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
    kotlin("plugin.spring")
    kotlin("kapt")
    id("com.ewerk.gradle.plugins.querydsl")
}

dependencies {
    api(project(":domain"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-databind")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:${libs.versions.querydsl.get()}:jakarta")
    implementation("com.querydsl:querydsl-core:${libs.versions.querydsl.get()}")
    implementation("com.querydsl:querydsl-sql:${libs.versions.querydsl.get()}")
    kapt("com.querydsl:querydsl-apt:${libs.versions.querydsl.get()}:jakarta")
    kapt("com.querydsl:querydsl-kotlin-codegen:${libs.versions.querydsl.get()}")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

tasks.withType<BootJar> {
    enabled = false
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
}
