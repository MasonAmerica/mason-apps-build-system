import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    groovy
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

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
    dependsOn(tasks["classes"])
}

afterEvaluate {
    publishing.publications.named<MavenPublication>("pluginMaven") {
        artifactId = "mason-apps"
        artifact(sourcesJar.get())
    }
}
