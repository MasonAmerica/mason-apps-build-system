package com.bymason.build.masonapps

import org.gradle.api.tasks.Internal

open class MasonAppsExtension @JvmOverloads constructor(
        @get:Internal internal val name: String = "default" // Needed for Gradle
) {
    @get:Internal
    var appName = "app"
}
