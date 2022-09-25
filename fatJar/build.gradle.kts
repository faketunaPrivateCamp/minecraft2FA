import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":minecraft2FA-paper"))
    implementation(project(":minecraft2FA-waterfall"))
}

tasks {
    "shadowJar"(ShadowJar::class) {
        archiveBaseName.set("Minecraft2FA")
        archiveClassifier.set("universal")
        archiveVersion.set(version)
    }
}