package com.bymason.build.masonapps

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType

@Suppress("unused") // Used by Gradle
class MasonAppsBuildPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.withType<AppPlugin> {
            applyInternal(project, project.the())
        }
    }

    private fun applyInternal(project: Project, android: AppExtension) {
        println("Hello World! " + project.name)
    }
}
