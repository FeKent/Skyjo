import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
}

subprojects {
    // Ensures the right jvmTarget for kotlin related tasks
    // It needs to be done here, instead of each project, otherwise ksp will
    // fail to resolve the right version.
    // -- https://youtrack.jetbrains.com/issue/KT-55947
    tasks.withType<KotlinCompile> {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
}
