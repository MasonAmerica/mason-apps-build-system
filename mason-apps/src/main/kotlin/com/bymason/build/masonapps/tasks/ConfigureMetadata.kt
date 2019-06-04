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
        Grgit.open().use {
            val headCommit = it.head()
            val headTag = it.tag.list().orEmpty().sortedBy { it.commit.dateTime }.lastOrNull()

            val isRelease = headCommit.id == headTag?.commit?.id && it.status().isClean
            val baseVersionName =
                    headTag?.name?.removePrefix("v")?.substringBeforeLast("-") ?: "1.0.0"
            val (major, minor, patch) = baseVersionName.split(".").map { it.toInt() }

            val versionCode = extension.majorShift * major +
                    extension.minorShift * minor +
                    extension.patchShift * patch
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
