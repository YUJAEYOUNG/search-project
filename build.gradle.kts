import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

allprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "global.search.pretask"
    version = "1.0.0"
    java.sourceCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_MAC)) {
            implementation("io.netty:netty-resolver-dns-native-macos:4.1.87.Final:osx-aarch_64")
        }

        // faker
        testImplementation(rootProject.libs.faker)
        // mockk
        testImplementation(rootProject.libs.mockk)
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${rootProject.libs.versions.springCloud.get()}")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.withType<BootJar> {
    enabled = false
}
tasks.withType<Jar> {
    enabled = false
}
