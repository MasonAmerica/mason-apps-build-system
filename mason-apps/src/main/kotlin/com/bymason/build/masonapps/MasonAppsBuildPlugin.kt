package com.bymason.build.masonapps

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.bymason.build.masonapps.tasks.ConfigureMetadata
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType

@Suppress("unused") // Used by Gradle
class MasonAppsBuildPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.withType<AppPlugin> {
            applyInternal(project)
        }
    }

    private fun applyInternal(project: Project) {
        val android = project.the<AppExtension>()
        val mason = project.extensions.create<MasonAppsExtension>("masonApps")

        android.applicationVariants.configureEach {
            val configMetadata = project.tasks.register<ConfigureMetadata>(
                    "configure${name.capitalize()}Metadata",
                    mason,
                    this
            )
            checkManifestProvider { dependsOn(configMetadata) }
        }
    }
}
