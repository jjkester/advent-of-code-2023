import info.solidsoft.gradle.pitest.PitestPluginExtension

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.pitest)
}

group = "nl.jjkester"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.kotlinx.multik)
    implementation(libs.apache.commons.math)

    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.mockito)
}

tasks.test {
    useJUnitPlatform()
}

configure<PitestPluginExtension> {
    pitestVersion = "1.15.3"
    junit5PluginVersion = "1.2.1"
    outputFormats = setOf("HTML")
}

kotlin {
    jvmToolchain(17)
}
