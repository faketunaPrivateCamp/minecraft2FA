package jp.faketuna.minecraft2fa.paper.event

import jp.faketuna.minecraft2fa.paper.manager.PluginInstanceManager
import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AuthSuccessEventListener: Listener {

    @EventHandler
    fun onAuthSuccess(event: AuthSuccessEvent) {
        AuthInformationManager().addAuthorizedUser(event.uuid)
        Bukkit.getPlayer(event.uuid)?.sendMessage(Component.text("§aYour now verified and can execute moderation commands!"))
        Bukkit.getScheduler().runTaskLater(PluginInstanceManager().getPlugin(), Runnable {
            AuthInformationManager().addAuthorizeExpiredUser(event.uuid)
        }, PluginInstanceManager().getConfigManager(PluginInstanceManager().getPlugin()).getSessionExpireTime() * 20)
    }
}