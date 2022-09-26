package jp.faketuna.minecraft2fa.waterfall

import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.waterfall.discord.Bot
import jp.faketuna.minecraft2fa.waterfall.manager.PluginInstanceManager
import net.md_5.bungee.api.plugin.Plugin

class Minecraft2FA: Plugin() {
    private val manager = PluginInstanceManager()

    override fun onEnable() {
        logger.info("loading plugin")
        val token = ConfigManager().getConfigManager(this).getToken()
        manager.setDiscordBotInstance(Bot(token , 0))
    }

    override fun onDisable() {
        logger.info("plugin unloaded")
        if (manager.isDiscordBotInitialized()) manager.getDiscordBotInstance().shutdownBot()
    }
}