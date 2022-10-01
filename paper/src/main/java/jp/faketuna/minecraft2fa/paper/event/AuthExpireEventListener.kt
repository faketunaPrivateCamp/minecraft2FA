package jp.faketuna.minecraft2fa.paper.event

import jp.faketuna.minecraft2fa.paper.manager.PluginInstanceManager
import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AuthExpireEventListener: Listener {

    @EventHandler
    fun onAuthSuccess(event: AuthExpireEvent) {
        val prefix = ConfigManager().getConfigManager(PluginInstanceManager().getPlugin()).getPluginPrefix()
        Bukkit.getPlayer(event.uuid)?.sendMessage(Component.text("$prefix §cYour verified session is ended. When you logout, You need verify again."))
        AuthInformationManager().addAuthorizeExpiredUser(event.uuid)
    }
}