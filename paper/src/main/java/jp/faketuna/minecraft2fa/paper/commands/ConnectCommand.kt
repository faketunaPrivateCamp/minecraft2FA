package jp.faketuna.minecraft2fa.paper.commands

import jp.faketuna.minecraft2fa.shared.auth.AccountConnection
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ConnectCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (command.name.equals("connectdiscord", ignoreCase = true)){
            if (sender !is Player){
                sender.sendMessage("This command can only executed from player!")
            }
            val p = sender as Player
            if (args.isNullOrEmpty()){
                p.sendMessage("Not enough arguments!")
            } else if(args.size >= 2){
                p.sendMessage("Too many arguments!")
            } else{
                val ac = AccountConnection(0)
                if (ac.isValidToken(args[0])){
                    // TODO SQLに書き込む作業
                    /*try{
                        val sql = MySQL("", "", "")
                        sql.addDiscordIntegrationInformation(ac.getDiscordIDFromToken(args[0]), p.uniqueId)
                    } catch (e: Exception){
                        p.sendMessage("Exception occurred! Please contact to server administrator!")
                        return true
                    }
                    */

                    ac.removeToken(args[0])
                    p.sendMessage("Your account has been integrated!")
                    return true
                }
                p.sendMessage("Provided token is invalid! Check your token and try again.")
                return true
            }
            return true
        }
        return false
    }
}