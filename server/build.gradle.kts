import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.springframework.boot") version "2.3.5.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.4.20"
    jacoco
}
apply(plugin = "io.spring.dependency-management")

group = "org.itmodreamteam.myrest"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

repositories {
    mavenCentral()
    maven(url = "https://kotlin.bintray.com/kotlinx/")
}

dependencies {
    implementation(project(":shared"))  // for gradle build
    implementation(files("../shared/build/libs/shared-jvm-${project.version}.jar"))  // for IDEA

    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.0")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")

    implementation("org.apache.commons:commons-lang3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.itextpdf:itextpdf:5.5.13.2")
    implementation("com.itextpdf.tool:xmlworker:5.5.13.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:3.6.0")
    testImplementation("org.mockito:mockito-inline:3.6.0")
    testImplementation("com.h2database:h2")
}
