package jp.faketuna.minecraft2fa.paper.event

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AuthSuccessEventListener: Listener {

    @EventHandler
    fun onAuthSuccess(event: AuthSuccessEvent){
        Bukkit.broadcast(Component.text("Auth success: ${event.uuid}"))
    }
}