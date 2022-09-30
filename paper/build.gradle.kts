import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm") version "1.7.10"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

val mcVersion = "1.19"


dependencies {
    implementation(project(":minecraft2FA-shared"))
}

bukkit {
    main = "jp.faketuna.minecraft2fa.paper.Minecraft2FA"

    apiVersion = "1.19"

    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    author = "ft"
    prefix = "Minecraft2FA"


    commands {
        register("connectdiscord") {
            description = "Used for integrating discord and minecraft account"
            permission = "mc2fa.connect"
            usage = "/connect"
            permissionMessage = "You don't have permission to perform this command."
        }
    }


    permissions {
        register("mc2fa.*") {
            children = listOf("mc2fa.connect")

        }

        register("mc2fa.connect") {
            description = "Discord integration command"
            default = BukkitPluginDescription.Permission.Default.OP
        }
    }
}