package jp.faketuna.minecraft2fa.paper.event

import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AuthSuccessEventListener: Listener {

    @EventHandler
    fun onAuthSuccess(event: AuthSuccessEvent){
        AuthInformationManager().addAuthorizedUser(event.uuid)
    }
}