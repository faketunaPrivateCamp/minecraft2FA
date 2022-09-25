package jp.faketuna.minecraft2fa.shared.config

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.IOException


class WaterfallConfigManager(private val plugin: Plugin): Config() {
    private var pluginConfig: Configuration

    init {
        var exists = false
        val folder: File = plugin.dataFolder
        if (!folder.exists()){
            folder.mkdir()
        }
        val configFile = File(folder, "config.yml")
        exists = configFile.exists()
        if (!exists){
            plugin.logger.info(ChatColor.DARK_GREEN.toString() + "config.yml is not exists! Creating one!")
            try {
                configFile.createNewFile()
            } catch (e: IOException){
                plugin.logger.warning(ChatColor.DARK_RED.toString() + "Failed to create config.yml!")
                e.printStackTrace()
            }
        }

        pluginConfig = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(configFile)

        if (!exists){
            pluginConfig.set("token", "justpasteyourtoken32234235lk34j5lk")
            saveConfig(pluginConfig)
        }
        Config.setToken(pluginConfig.getString("token"))
        plugin.logger.info("Config loaded.")
    }


    fun getToken(): String{
        return Config.getToken()
    }

    fun getConfig(): Configuration{
        return pluginConfig
    }

    private fun saveConfig(config:Configuration){
        val folder: File = plugin.dataFolder
        val configFile = File(folder, "config.yml")
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(config, configFile)
    }
}