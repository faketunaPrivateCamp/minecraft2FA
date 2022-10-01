package jp.faketuna.minecraft2fa.waterfall.event

import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
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
            val args = event.message.split(" ")
            if (args[0] == "/connectdiscord") return
            if (AuthInformationManager().isUserAuthorized(player.uniqueId)) return
            if (player.hasPermission("mc2fa.connect")){
                player.sendMessage(TextComponent("You are not in verified session. Please verify your session in discord to execute command."))
                event.isCancelled = true
            }
        }
    }
}