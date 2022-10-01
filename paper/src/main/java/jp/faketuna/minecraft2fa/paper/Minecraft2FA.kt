package jp.faketuna.minecraft2fa.paper

import jp.faketuna.minecraft2fa.paper.commands.ConnectCommand
import jp.faketuna.minecraft2fa.paper.discord.Bot
import jp.faketuna.minecraft2fa.paper.event.AuthSuccessEventListener
import jp.faketuna.minecraft2fa.paper.event.CommandExecuteEventListener
import jp.faketuna.minecraft2fa.paper.event.PlayerJoinListener
import jp.faketuna.minecraft2fa.paper.event.PluginMessageEventListener
import jp.faketuna.minecraft2fa.paper.manager.PluginInstanceManager
import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.shared.database.MySQL
import net.dv8tion.jda.api.exceptions.InvalidTokenException
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Minecraft2FA: JavaPlugin() {
    private val manager = PluginInstanceManager()

    override fun onEnable() {
        logger.info("Loading plugin")
        manager.setConfigManager(ConfigManager())
        val isBungee = Bukkit.spigot().config.getBoolean("settings.bungeecord")
        if(!isBungee){
            logger.info("Running in standalone mode.")
            val config = manager.getConfigManager(this)
            val token = config.getToken()
            try{
                manager.setDiscordBotInstance(Bot(token))
            } catch (e: InvalidTokenException){
                logger.info("§4Your Token is invalid! Check your config.")
                logger.info("§4Plugin will not start.")
                Bukkit.getPluginManager().disablePlugin(this)
                return
            }
            try{
                manager.setMySQLInstance(MySQL(config.getMySQLServerAddress(), config.getMySQLUserID(), config.getMySQLUserPassword()))
                manager.getMySQLInstance().isDatabaseExists()
            } catch (e: Exception){
                logger.info("§4Cannot connect to MySQL database! Check your config.")
                logger.info("§4Plugin will not start.")
                Bukkit.getPluginManager().disablePlugin(this)
                return
            }
            val sql = manager.getMySQLInstance()
            if(sql.isTablesExists()){
                if (!sql.is2FATableExists()){
                    sql.create2FATable()
                }
                if (!sql.isDiscordIntegrationTableExists()){
                    sql.createDiscordIntegrationTable()
                }
            }
            Bukkit.getPluginManager().registerEvents(AuthSuccessEventListener(), this)
            this.getCommand("connectdiscord")!!.setExecutor(ConnectCommand())
        } else {
            logger.info("Running in bungeecord mode.")
            server.messenger.registerOutgoingPluginChannel(this, "mc2fa:authentication")
            server.messenger.registerIncomingPluginChannel(this, "mc2fa:authentication", PluginMessageEventListener())
            Bukkit.getPluginManager().registerEvents(PlayerJoinListener(), this)
        }
        Bukkit.getPluginManager().registerEvents(CommandExecuteEventListener(isBungee), this)
        manager.setPlugin(this)
    }

    override fun onDisable() {
        logger.info("plugin unloaded")
        server.messenger.unregisterIncomingPluginChannel(this)
        server.messenger.unregisterOutgoingPluginChannel(this)
        if (manager.isDiscordBotInitialized()) manager.getDiscordBotInstance().shutdownBot()
    }
}