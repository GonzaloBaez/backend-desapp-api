import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id ("org.sonarqube") version "3.3"
	id("jacoco")
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
tasks.test {
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
}

sonarqube {
	properties {
		property ("sonar.projectKey", "GonzaloBaez_backend-desapp-api")
		property ("sonar.organization", "gonzalobaez")
		property ("sonar.host.url", "https://sonarcloud.io")
		property ("sonar.login","795ec1c68f8a2a0cb50f4cd4ce1082b3d4e6ccba")
		property ("sonar.coverage.jacoco.xmlReportPaths", "build/jacocoXML")
		property ("sonar.exclusions","src/main/kotlin/ar/edu/unq/desapp/grupoG/backenddesappapi/securityConfig/*")
		property ("sonar.exclusions","src/main/kotlin/ar/edu/unq/desapp/grupoG/backenddesappapi/cache/*")
		property ("sonar.exclusions","src/main/kotlin/ar/edu/unq/desapp/grupoG/backenddesappapi/swagger/*")
	}
}
jacoco {
	toolVersion = "0.8.7"
	reportsDirectory.set(layout.buildDirectory.dir("build/reports/jacoco"))
}
tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
		csv.required.set(false)
		xml.outputLocation.set(File("build/jacocoXML"))
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("org.springframework.boot:spring-boot-starter-security:2.5.5")
	implementation("io.springfox", "springfox-swagger2", "3.0.0")
	implementation("io.springfox", "springfox-swagger-ui", "3.0.0")
	implementation("io.springfox", "springfox-boot-starter", "3.0.0")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.junit.vintage:junit-vintage-engine:5.8.0")
	implementation("log4j:log4j:1.2.17")
	implementation("org.springframework.boot:spring-boot-starter-data-redis:2.5.5")

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
