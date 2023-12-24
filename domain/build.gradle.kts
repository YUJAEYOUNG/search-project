import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") // allOpen 포함
    kotlin("plugin.jpa") // no-arg 포함
    kotlin("kapt")
}

dependencies {
    implementation("org.springframework:spring-web")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    api(libs.swaggerAnnotation)
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:${libs.versions.querydsl.get()}:jakarta")
    implementation("com.querydsl:querydsl-core:${libs.versions.querydsl.get()}")
    implementation("com.querydsl:querydsl-sql:${libs.versions.querydsl.get()}")
    kapt("com.querydsl:querydsl-apt:${libs.versions.querydsl.get()}:jakarta")
    kapt("com.querydsl:querydsl-kotlin-codegen:${libs.versions.querydsl.get()}")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
    annotation("org.springframework.stereotype.Repository")
}

noArg {
    annotation("jakarta.persistence.Entity")
}

tasks.withType<BootJar> {
    enabled = false
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
