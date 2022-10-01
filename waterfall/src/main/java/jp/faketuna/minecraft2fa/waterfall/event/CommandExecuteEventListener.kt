package jp.faketuna.minecraft2fa.waterfall.event

import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class CommandExecuteEventListener: Listener {

    @EventHandler
    fun onCommandExecute(event: ChatEvent){
        if(event.sender !is ProxiedPlayer){
            return
        }
        val player = event.sender as ProxiedPlayer
        if (event.isCommand){
            if (event.message == "/discordconnect") return
            if (player.hasPermission("mc2fa.connect")){
                player.sendMessage(TextComponent("You are not authenticated. Please authorize in discord to execute command."))
            }
        }
    }
}