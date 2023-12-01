plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "nl.jjkester"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.test.junit)
    testImplementation(libs.test.assertk)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
