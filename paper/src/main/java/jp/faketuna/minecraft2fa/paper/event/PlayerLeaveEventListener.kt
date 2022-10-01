package jp.faketuna.minecraft2fa.paper.event

import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerLeaveEventListener: Listener {

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent){
        val authManager = AuthInformationManager()
        val playerUUID = event.player.uniqueId
        if (authManager.isUserAuthExpired(playerUUID)){
            authManager.removeAuthorizedUser(playerUUID)
            authManager.removeAuthorizeExpiredUser(playerUUID)
        }
    }
}