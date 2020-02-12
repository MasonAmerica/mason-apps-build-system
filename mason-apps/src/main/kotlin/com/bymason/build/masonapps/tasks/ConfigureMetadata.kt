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
            val headCommit = git.head()
            val headTag = git.tag.list().orEmpty().maxBy { it.commit.dateTime }

            val isRelease = headCommit.id == headTag?.commit?.id && git.status().isClean
            val baseVersionName =
                    headTag?.name?.removePrefix("v")?.substringBeforeLast("-") ?: "1.0.0"

            val versionCode = if (project.hasProperty("relBuild")) {
                (System.currentTimeMillis() / 1000).toInt()
            } else {
                1
            }
            val versionName = baseVersionName + "-" +
                    if (isRelease) headCommit.abbreviatedId else "dev"

            variant.outputs.filterIsInstance<ApkVariantOutput>().forEach { output ->
                output.versionCodeOverride = versionCode
                output.versionNameOverride = versionName
                output.outputFileName = extension.appName + "-" + versionName + ".apk"
            }
        }
    }
}
