import java.net.URI

plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
}

repositories {
    mavenCentral()
}


val proxyVersion = "1.19"
val mcVersion = "1.19"

subprojects{
    apply(plugin = "java")
    java.sourceCompatibility=JavaVersion.VERSION_17
    group = "jp.faketuna"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        //implementation("org.yaml:snakeyaml:1.32")
        implementation("net.dv8tion:JDA:5.0.0-alpha.20") {
            exclude("opus-java")
        }
    }
}

project(":minecraft2FA-paper"){
    repositories {
        maven { url = URI("https://papermc.io/repo/repository/maven-public/") }
    }
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
        implementation(kotlin("stdlib-jdk8"))
        compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
    }
}
project(":minecraft2FA-waterfall"){
    repositories {
        maven { url = URI("https://papermc.io/repo/repository/maven-public/") }
    }
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
        implementation(kotlin("stdlib-jdk8"))
        compileOnly("io.github.waterfallmc:waterfall-api:${proxyVersion}-R0.1-SNAPSHOT")
    }
}
project(":minecraft2FA-shared"){
    repositories {
        maven { url = URI("https://papermc.io/repo/repository/maven-public/") }
    }
    dependencies{
        compileOnly("io.github.waterfallmc:waterfall-api:${proxyVersion}-R0.1-SNAPSHOT")
        compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
        implementation("mysql:mysql-connector-java:8.0.15")
    }

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}