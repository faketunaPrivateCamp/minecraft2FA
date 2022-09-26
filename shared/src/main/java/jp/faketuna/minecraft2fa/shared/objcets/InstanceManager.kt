package jp.faketuna.minecraft2fa.shared.objcets

import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.shared.config.PaperConfigManager
import jp.faketuna.minecraft2fa.shared.config.WaterfallConfigManager
import jp.faketuna.minecraft2fa.shared.discord.DiscordBot
import net.md_5.bungee.api.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

interface InstanceManager {

    fun getDiscordBotInstance(): DiscordBot

    fun setDiscordBotInstance(discordBot: DiscordBot)

    fun isDiscordBotInitialized(): Boolean

    fun getConfigManager(plugin: JavaPlugin): PaperConfigManager

    fun getConfigManager(plugin: Plugin): WaterfallConfigManager

    fun setConfigManager(configManager: ConfigManager)

    fun isConfigManagerInitialized(): Boolean
}