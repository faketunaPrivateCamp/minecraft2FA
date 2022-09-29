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
        val exists: Boolean
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

        writeDefault()

        Config.setToken(pluginConfig.getString("token"))
        Config.setRoleID(pluginConfig.getLong("connectableRoleID"))
        plugin.logger.info("Config loaded.")
    }


    fun getToken(): String{
        return Config.getToken()
    }

    fun getConfig(): Configuration{
        return pluginConfig
    }

    fun getMySQLServerAddress(): String{
        return Config.getMySQLServerAddress()
    }

    fun getMySQLUserID(): String{
        return Config.getMySQLUserID()
    }

    fun getMySQLUserPassword(): String{
        return Config.getMySQLUserPassword()
    }

    private fun writeDefault(){
        val conf = getConfig()
        if (!conf.contains("token")){
            conf.set("token", "justpasteyourtoken32234235lk34j5lk")
        }
        if (!conf.contains("connectableRoleID")) {
            conf.set("connectableRoleID", "209348572902897")
        }
        if (!conf.contains("mysql.serverAddress")){
            conf.set("mysql.serverAddress", "127.0.0.1")
        }
        if (!conf.contains("mysql.userID")){
            conf.set("mysql.userID", "")
        }
        if (!conf.contains("mysql.userPassword")){
            conf.set("mysql.userPassword", "")
        }
        saveConfig(pluginConfig)
    }

    private fun saveConfig(config:Configuration){
        val folder: File = plugin.dataFolder
        val configFile = File(folder, "config.yml")
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(config, configFile)
    }
}