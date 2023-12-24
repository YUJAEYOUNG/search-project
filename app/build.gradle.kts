plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("kapt")
}

dependencies {
	implementation(project(":service"))
	implementation(project(":common"))
	implementation(project(":persistence"))

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// DB
	runtimeOnly("com.h2database:h2")
	testRuntimeOnly("com.h2database:h2")

	// redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// actuator
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// openApi
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.0.2")
	implementation("org.springdoc:springdoc-openapi-starter-common:2.0.2")

	// test
	testImplementation("io.rest-assured:rest-assured")
	testImplementation("io.rest-assured:spring-mock-mvc")
	testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
	testImplementation("org.testcontainers:junit-jupiter")
}
