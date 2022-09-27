package jp.faketuna.minecraft2fa.shared.objcets

import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.shared.config.PaperConfigManager
import jp.faketuna.minecraft2fa.shared.config.WaterfallConfigManager
import jp.faketuna.minecraft2fa.shared.discord.DiscordBot
import net.md_5.bungee.api.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

interface InstanceManager {

    /**
     * Get a DiscordBot Instance
     * @return DiscordBot instance
     */
    fun getDiscordBotInstance(): DiscordBot

    /**
     * Sets a DiscordBot Instance
     * @param DiscordBot
     */
    fun setDiscordBotInstance(discordBot: DiscordBot)

    /**
     * Check Bot instance is initialized
     * @return Boolean
     */
    fun isDiscordBotInitialized(): Boolean

    /**
     * Get a config manager
     * @param JavaPlugin
     */
    fun getConfigManager(plugin: JavaPlugin): PaperConfigManager

    /**
     * Get a config manager
     * @param Plugin
     */
    fun getConfigManager(plugin: Plugin): WaterfallConfigManager

    /**
     * Sets a config manager it needs in getConfigManager() Method
     * @param ConfigManager
     */
    fun setConfigManager(configManager: ConfigManager)

    /**
     * Check ConfigManager instance is initialized
     * @return Boolean
     */
    fun isConfigManagerInitialized(): Boolean
}