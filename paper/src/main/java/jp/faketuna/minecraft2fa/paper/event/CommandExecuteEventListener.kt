package jp.faketuna.minecraft2fa.paper.event

import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class CommandExecuteEventListener: Listener {

    @EventHandler
    fun onExecuteCommand(event: PlayerCommandPreprocessEvent){
        if (event.message == "/connectdiscord") return
        if (event.player.hasPermission("mc2fa.connect")){
            if (!AuthInformationManager().isUserAuthorized(event.player.uniqueId)){
                event.player.sendMessage("You are not authenticated. Please authorize in discord to execute command.")
                event.isCancelled = true
            }
        }
    }
}