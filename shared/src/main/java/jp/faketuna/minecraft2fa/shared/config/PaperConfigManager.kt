package jp.faketuna.minecraft2fa.shared.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin


class PaperConfigManager(private val plugin: JavaPlugin): Config() {
    private var token: String?
    private var roleID: Long?

    init {
        writeDefault()
        token = plugin.config.getString("token")
        roleID = plugin.config.getLong("connectableRoleID")
        Config.setToken(token.toString())
        Config.setRoleID(plugin.config.getLong("connectableRoleID"))
    }

    fun getToken(): String{
        return Config.getToken()
    }

    fun getRoleID(): Long{
        return Config.getRoleID()
    }

    fun getConfig(): FileConfiguration {
        return plugin.config
    }

    private fun writeDefault(){
        if (!plugin.config.contains("token")){
            plugin.config.set("token", "justpasteyourtoken32234235lk34j5lk")
        }
        if (!plugin.config.contains("connectableRoleID")) {
            plugin.config.set("connectableRoleID", "209348572902897")
        }
        plugin.saveConfig()
    }
}