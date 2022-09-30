package jp.faketuna.minecraft2fa.waterfall.commands

import jp.faketuna.minecraft2fa.shared.auth.AccountConnection
import jp.faketuna.minecraft2fa.waterfall.manager.PluginInstanceManager
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command

class ConnectCommand: Command("connectdiscord", "mc2fa.connect") {
    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (sender !is ProxiedPlayer){
            sender!!.sendMessage(TextComponent("This command can only executed from player!"))
            return
        }
        val p: ProxiedPlayer = sender
        if (args.isNullOrEmpty()){
            p.sendMessage(TextComponent("Not enough arguments!"))
        } else if(args.size >= 2){
            p.sendMessage(TextComponent("Too many arguments!"))
        } else{
            val ac = AccountConnection(0)
            if (ac.isValidToken(args[0])){
                try{
                    val sql = PluginInstanceManager().getMySQLInstance()
                    sql.addDiscordIntegrationInformation(ac.getDiscordIDFromToken(args[0]), p.uniqueId)
                } catch (e: Exception){
                    p.sendMessage(TextComponent("Exception occurred! Please contact to server administrator!"))
                    return
                }
                ac.removeToken(args[0])
                p.sendMessage(TextComponent("Your account has been integrated!"))
                return
            }
            p.sendMessage(TextComponent("Provided token is invalid! Check your token and try again."))
            return
        }
    }
}