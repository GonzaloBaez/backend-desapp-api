import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id ("org.sonarqube") version "3.3"
	war
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
	kotlin("plugin.jpa") version "1.5.21"
}

group = "ar.edu.unq.desapp.grupoG"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}


sonarqube {
	properties {
		property ("sonar.projectKey", "GonzaloBaez_backend-desapp-api")
		property ("sonar.organization", "gonzalobaez")
		property ("sonar.host.url", "https://sonarcloud.io")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("log4j:log4j:1.2.17")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
