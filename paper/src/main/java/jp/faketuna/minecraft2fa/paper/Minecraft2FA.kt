package jp.faketuna.minecraft2fa.paper

import jp.faketuna.minecraft2fa.paper.discord.Bot
import jp.faketuna.minecraft2fa.paper.manager.PluginInstanceManager
import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Minecraft2FA: JavaPlugin() {
    private val manager = PluginInstanceManager()

    override fun onEnable() {
        logger.info("Loading plugin")
        if(!Bukkit.spigot().config.getBoolean("settings.bungeecord")){
            val token = ConfigManager().getConfigManager(this).getToken()
            manager.setDiscordBotInstance(Bot(token, 0))
        }
    }

    override fun onDisable() {
        logger.info("plugin unloaded")
        if (manager.isDiscordBotInitialized()) manager.getDiscordBotInstance().shutdownBot()
    }
}