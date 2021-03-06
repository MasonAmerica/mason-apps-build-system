plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

dependencies {
    compileOnly("com.android.tools.build:gradle:4.0.0-alpha09")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

tasks.withType<ValidatePlugins>().configureEach {
    enableStricterValidation.set(true)
}

group = "com.bymason.build"
version = "1.0.0"

gradlePlugin {
    plugins.create("mason-apps") {
        id = "mason-apps"
        implementationClass = "com.bymason.build.masonapps.MasonAppsBuildPlugin"
    }
}
