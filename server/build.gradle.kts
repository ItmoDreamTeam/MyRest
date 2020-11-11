import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.springframework.boot") version "2.3.5.RELEASE"
}
apply(plugin = "io.spring.dependency-management")

group = "org.itmodreamteam.myrest"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}
