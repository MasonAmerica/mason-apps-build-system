buildscript {
    repositories {
        google()
        jcenter()
    }
}

plugins {
    `lifecycle-base`
    id("com.github.ben-manes.versions") version "0.27.0"
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
