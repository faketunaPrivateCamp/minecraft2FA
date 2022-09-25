import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import java.net.URI

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

    /*
    commands {
        register("command") {
            description = "Just a command!"
            aliases = listOf("cmd")
            permission = "command.children"
            usage = "/command"
            permissionMessage = "no perm"
        }
    }


    permissions {
        register("command.*") {
            children = listOf("ktt.children")

        }

        register("command.children") {
            description = "the childlen command"
            default = BukkitPluginDescription.Permission.Default.OP
        }
    }
    */
}