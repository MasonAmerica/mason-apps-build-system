import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    groovy
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
}

dependencies {
    compileOnly("com.android.tools.build:gradle:3.6.0-alpha02")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
    }
}

tasks.withType<ValidateTaskProperties>().configureEach {
    enableStricterValidation = true
    failOnWarning = true
}

group = "com.bymason.build"
version = "1.0.0"

gradlePlugin {
    plugins.register("mason-apps") {
        id = "mason-apps"
        implementationClass = "com.bymason.build.masonapps.MasonAppsBuildPlugin"
    }
}
