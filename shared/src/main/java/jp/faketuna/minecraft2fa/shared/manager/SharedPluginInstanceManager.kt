package jp.faketuna.minecraft2fa.shared.manager

import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.shared.config.PaperConfigManager
import jp.faketuna.minecraft2fa.shared.config.WaterfallConfigManager
import jp.faketuna.minecraft2fa.shared.database.MySQL
import jp.faketuna.minecraft2fa.shared.discord.DiscordBot
import jp.faketuna.minecraft2fa.shared.objcets.InstanceManager
import net.md_5.bungee.api.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

open class SharedPluginInstanceManager: InstanceManager {
    object Manager{
        private lateinit var discordBot: DiscordBot
        private lateinit var configManager: ConfigManager
        private lateinit var MySQL: MySQL

        fun getDiscordBotInstance(): DiscordBot{
            return this.discordBot
        }
        fun setDiscordBotInstance(discordBot: DiscordBot){
            this.discordBot = discordBot
        }
        fun isDiscordBotInitialized(): Boolean{
            return this::discordBot.isInitialized
        }

        fun getConfigManager(): ConfigManager{
            return this.configManager
        }
        fun setConfigManager(configManager: ConfigManager){
            this.configManager = configManager
        }
        fun isConfigManagerInitialized(): Boolean{
            return this::configManager.isInitialized
        }

        fun getMySQLInstance(): MySQL{
            return this.MySQL
        }
        fun setMySQLInstance(mySQL: MySQL){
            this.MySQL = mySQL
        }
        fun isMySQLInstanceInitialized(): Boolean{
            return this::MySQL.isInitialized
        }
    }
    override fun getDiscordBotInstance(): DiscordBot {
        return Manager.getDiscordBotInstance()
    }

    override fun setDiscordBotInstance(discordBot: DiscordBot) {
        Manager.setDiscordBotInstance(discordBot)
    }

    override fun isDiscordBotInitialized(): Boolean {
        return Manager.isDiscordBotInitialized()
    }

    override fun getConfigManager(plugin: JavaPlugin): PaperConfigManager {
        return Manager.getConfigManager().getConfigManager(plugin)
    }

    override fun getConfigManager(plugin: Plugin): WaterfallConfigManager {
        return Manager.getConfigManager().getConfigManager(plugin)
    }

    override fun setConfigManager(configManager: ConfigManager) {
        Manager.setConfigManager(configManager)
    }

    override fun isConfigManagerInitialized(): Boolean {
        return Manager.isConfigManagerInitialized()
    }

    override fun getMySQLInstance(): MySQL {
        return Manager.getMySQLInstance()
    }

    override fun setMySQLInstance(mySQL: MySQL) {
        Manager.setMySQLInstance(mySQL)
    }

    override fun isMySQLInstanceInitialized(): Boolean {
        return Manager.isMySQLInstanceInitialized()
    }
}