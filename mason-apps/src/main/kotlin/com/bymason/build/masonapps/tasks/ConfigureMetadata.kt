package com.bymason.build.masonapps.tasks

import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import com.bymason.build.masonapps.MasonAppsExtension
import org.ajoberstar.grgit.Grgit
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class ConfigureMetadata @Inject constructor(
        @get:Nested protected val extension: MasonAppsExtension,
        private val variant: ApplicationVariant
) : DefaultTask() {
    @TaskAction
    fun configure() {
        Grgit.open {
            dir = project.rootDir
        }.use { git ->
            val versionCode = git.log().size
            val tagName = git.describe { tags = true }
            val versionName = if (git.status().isClean) tagName else "$tagName-dirty"

            variant.outputs.filterIsInstance<ApkVariantOutput>().forEach { output ->
                output.versionCodeOverride = versionCode
                output.versionNameOverride = versionName
                output.outputFileName = extension.appName + "-" + versionName + ".apk"
            }
        }
    }
}
