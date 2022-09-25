import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm") version "1.7.10"
    id("net.minecrell.plugin-yml.bungee") version "0.5.2"
}

val proxyVersion = "1.19"


dependencies {
    implementation(project(":minecraft2FA-shared"))
}

bungee {
    main = "jp.faketuna.minecraft2fa.waterfall.Minecraft2FA"
    author = "ft"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}