package jp.faketuna.minecraft2fa.waterfall.event

import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class PlayerLeaveEventListener: Listener {

    @EventHandler
    fun onPlayerLeave(event: PlayerDisconnectEvent){
        val authManager = AuthInformationManager()
        val playerUUID = event.player.uniqueId
        if (authManager.isUserAuthExpired(playerUUID)){
            authManager.removeAuthorizedUser(playerUUID)
            authManager.removeAuthorizeExpiredUser(playerUUID)
        }
    }
}