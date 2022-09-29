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
        plugin.saveConfig()
    }
}