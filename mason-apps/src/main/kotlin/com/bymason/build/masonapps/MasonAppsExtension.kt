package com.bymason.build.masonapps

import org.gradle.api.tasks.Internal

open class MasonAppsExtension @JvmOverloads constructor(
        @get:Internal internal val name: String = "default" // Needed for Gradle
) {
    @get:Internal
    var appName = "app"

    @get:Internal
    var majorShift = 10_000
    @get:Internal
    var minorShift = 100
    @get:Internal
    var patchShift = 1
}
