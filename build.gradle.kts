plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "nl.jjkester"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.kotlinx.multik)

    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.mockito)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
