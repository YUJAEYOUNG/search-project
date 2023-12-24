rootProject.name = "search-project"

include(":app")
include(":common")
include(":domain")
include(":persistence")
include(":service")
include(":api")

// 플러그인 버전 관리
pluginManagement {

    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }

    plugins {
        id("org.springframework.boot") version "3.1.2"
        id("io.spring.dependency-management") version "1.1.2"
        kotlin("jvm") version "1.9.0"
        kotlin("plugin.spring") version "1.9.0"
        kotlin("plugin.jpa") version "1.9.0"
        kotlin("plugin.allopen") version "1.9.0"
        kotlin("plugin.noarg") version "1.9.0"
        kotlin("kapt") version "1.9.0"
        id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    }
}

// 모듈 의존성 버전 관리
dependencyResolutionManagement {
    versionCatalogs {
        // 라이브러리 버전
        create("libs") {
            library("faker", "com.github.javafaker:javafaker:1.0.2")
            library("mockk", "io.mockk:mockk:1.12.3")
            library("mockWebServer", "com.squareup.okhttp3:mockwebserver:4.11.0")
            library("swaggerAnnotation", "io.swagger.core.v3:swagger-annotations:2.2.15")
            library("swaggerAnnotationJakarta", "io.swagger.core.v3:swagger-annotations-jakarta:2.2.15")
            version("openapi", "2.2.0")
            version("querydsl", "5.0.0")
            version("springCloud", "2022.0.4")
        }
    }
}