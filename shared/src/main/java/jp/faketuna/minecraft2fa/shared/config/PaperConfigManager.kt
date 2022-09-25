package jp.faketuna.minecraft2fa.shared.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin


class PaperConfigManager(private val plugin: JavaPlugin): Config() {
    private var token: String? = plugin.config.getString("token")

    init {
        if (token == null){
            plugin.config.set("token", "justpasteyourtoken32234235lk34j5lk")
            plugin.saveConfig()
            token = "justpasteyourtoken32234235lk34j5lk"
        }
        Config.setToken(plugin.config.getString("token").toString())

    }

    fun getToken(): String{
        return Config.getToken()
    }

    fun getConfig(): FileConfiguration {
        return plugin.config
    }
}