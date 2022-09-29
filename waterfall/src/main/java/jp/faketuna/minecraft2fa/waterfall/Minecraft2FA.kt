package jp.faketuna.minecraft2fa.waterfall

import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.shared.database.MySQL
import jp.faketuna.minecraft2fa.waterfall.discord.Bot
import jp.faketuna.minecraft2fa.waterfall.manager.PluginInstanceManager
import net.dv8tion.jda.api.exceptions.InvalidTokenException
import net.md_5.bungee.api.plugin.Plugin

class Minecraft2FA: Plugin() {
    private val manager = PluginInstanceManager()
    private var pluginUnloaded = false

    override fun onEnable() {
        logger.info("loading plugin")
        manager.setConfigManager(ConfigManager())
        val config = manager.getConfigManager(this)
        val token = config.getToken()
        try{
            manager.setDiscordBotInstance(Bot(token))
        } catch (e: InvalidTokenException){
            e.printStackTrace()
            logger.info("§4Your Token is invalid! Check your config.")
            logger.info("§4Plugin will not start.")
            onDisable()
            return
        }
        try{
            manager.setMySQLInstance(MySQL(config.getMySQLServerAddress(), config.getMySQLUserID(), config.getMySQLUserPassword()))
            manager.getMySQLInstance().isDatabaseExists()
        } catch (e: Exception){
            logger.info("§4Cannot connect to MySQL database! Check your config.")
            logger.info("§4Plugin will not start.")
            onDisable()
            pluginUnloaded = true
            return
        }
        manager.setPlugin(this)
    }

    override fun onDisable() {
        logger.info("Unloading plugin...")
        if (manager.isDiscordBotInitialized() && !pluginUnloaded) {
            manager.getDiscordBotInstance().getJDAInstance().awaitReady().shutdown()
            logger.info("Shutdown Discord bot.")
        }
        logger.info("Plugin unloaded.")
    }
}