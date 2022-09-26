package jp.faketuna.minecraft2fa.shared.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin


class PaperConfigManager(private val plugin: JavaPlugin): Config() {
    private var token: String? = plugin.config.getString("token")

    init {
        if (token == null){
            writeDefault()
            token = plugin.config.getString("token")
        }
        Config.setToken(token.toString())

    }

    fun getToken(): String{
        return Config.getToken()
    }

    fun getConfig(): FileConfiguration {
        return plugin.config
    }

    private fun writeDefault(){
        plugin.config.set("token", "justpasteyourtoken32234235lk34j5lk")
        plugin.saveConfig()
    }
}