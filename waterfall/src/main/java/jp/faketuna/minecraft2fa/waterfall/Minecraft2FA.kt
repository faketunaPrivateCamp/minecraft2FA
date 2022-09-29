package jp.faketuna.minecraft2fa.waterfall

import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.waterfall.discord.Bot
import jp.faketuna.minecraft2fa.waterfall.manager.PluginInstanceManager
import net.dv8tion.jda.api.exceptions.InvalidTokenException
import net.md_5.bungee.api.plugin.Plugin

class Minecraft2FA: Plugin() {
    private val manager = PluginInstanceManager()

    override fun onEnable() {
        logger.info("loading plugin")
        manager.setConfigManager(ConfigManager())
        val token = manager.getConfigManager(this).getToken()
        try{
            manager.setDiscordBotInstance(Bot(token))
        } catch (e: InvalidTokenException){
            e.printStackTrace()
            logger.info("§4Your Token is invalid! Check your config.")
            logger.info("§4Plugin will not start.")
            return
        }
        manager.setPlugin(this)
    }

    override fun onDisable() {
        logger.info("plugin unloaded")
        if (manager.isDiscordBotInitialized()) manager.getDiscordBotInstance().shutdownBot()
    }
}