package jp.faketuna.minecraft2fa.paper.commands

import jp.faketuna.minecraft2fa.paper.manager.PluginInstanceManager
import jp.faketuna.minecraft2fa.shared.auth.AccountConnection
import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ConnectCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (command.name.equals("connectdiscord", ignoreCase = true)){
            val prefix = ConfigManager().getConfigManager(PluginInstanceManager().getPlugin()).getPluginPrefix()
            if (sender !is Player){
                sender.sendMessage("$prefix This command can only executed from player!")
                return true
            }
            val p: Player = sender
            if (args.isNullOrEmpty()){
                p.sendMessage("$prefix Not enough arguments!")
            } else if(args.size >= 2){
                p.sendMessage("$prefix Too many arguments!")
            } else{
                val ac = AccountConnection(0)
                if (ac.isValidToken(args[0])){
                    try{
                        val sql = PluginInstanceManager().getMySQLInstance()
                        sql.addDiscordIntegrationInformation(ac.getDiscordIDFromToken(args[0]), p.uniqueId)
                    } catch (e: Exception){
                        p.sendMessage("$prefix Exception occurred! Please contact to server administrator!")
                        return true
                    }

                    ac.removeToken(args[0])
                    p.sendMessage("$prefix Your account has been integrated!")
                    return true
                }
                p.sendMessage("$prefix Provided token is invalid! Check your token and try again.")
                return true
            }
            return true
        }
        return false
    }
}