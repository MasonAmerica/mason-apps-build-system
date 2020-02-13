package com.bymason.build.masonapps.tasks

import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import com.bymason.build.masonapps.MasonAppsExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.support.serviceOf
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import javax.inject.Inject

open class ConfigureMetadata @Inject constructor(
        @get:Nested protected val extension: MasonAppsExtension,
        private val variant: ApplicationVariant
) : DefaultTask() {
    @TaskAction
    fun configure() {
        val execOps = project.serviceOf<ExecOperations>()

        val versionCode = run {
            val output = ByteArrayOutputStream()
            execOps.exec {
                commandLine("git", "rev-list", "--count", "HEAD")
                standardOutput = output
            }
            output.toString().trim().toInt()
        }
        val versionName = run {
            val output = ByteArrayOutputStream()
            execOps.exec {
                commandLine("git", "describe", "--tags", "--dirty", "--always")
                standardOutput = output
            }
            output.toString().trim()
        }

        variant.outputs.filterIsInstance<ApkVariantOutput>().forEach { output ->
            output.versionCodeOverride = versionCode
            output.versionNameOverride = versionName
            output.outputFileName = extension.appName + "-" + versionName + ".apk"
        }
    }
}
