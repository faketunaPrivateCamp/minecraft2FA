package jp.faketuna.minecraft2fa.paper

import jp.faketuna.minecraft2fa.paper.commands.ConnectCommand
import jp.faketuna.minecraft2fa.paper.discord.Bot
import jp.faketuna.minecraft2fa.paper.manager.PluginInstanceManager
import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import net.dv8tion.jda.api.exceptions.InvalidTokenException
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Minecraft2FA: JavaPlugin() {
    private val manager = PluginInstanceManager()

    override fun onEnable() {
        logger.info("Loading plugin")
        manager.setConfigManager(ConfigManager())
        if(!Bukkit.spigot().config.getBoolean("settings.bungeecord")){
            val token = manager.getConfigManager(this).getToken()
            try{
                manager.setDiscordBotInstance(Bot(token))
            } catch (e: InvalidTokenException){
                logger.info("§4Your Token is invalid! Check your config.")
                logger.info("§4Plugin will not start.")
                Bukkit.getPluginManager().disablePlugin(this)
                return
            }
            this.getCommand("connectdiscord")!!.setExecutor(ConnectCommand())
        }
        manager.setPlugin(this)
    }

    override fun onDisable() {
        logger.info("plugin unloaded")
        if (manager.isDiscordBotInitialized()) manager.getDiscordBotInstance().shutdownBot()
    }
}