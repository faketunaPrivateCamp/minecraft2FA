package jp.faketuna.minecraft2fa.shared.config

import net.md_5.bungee.api.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin


open class ConfigManager() {
    fun getConfigManager(plugin: JavaPlugin): PaperConfigManager{
        return PaperConfigManager(plugin)
    }
    fun getConfigManager(plugin: Plugin): WaterfallConfigManager{
        return WaterfallConfigManager(plugin)
    }
}