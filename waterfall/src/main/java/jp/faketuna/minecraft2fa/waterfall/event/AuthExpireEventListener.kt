package jp.faketuna.minecraft2fa.waterfall.event

import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import jp.faketuna.minecraft2fa.waterfall.manager.PluginInstanceManager
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class AuthExpireEventListener: Listener {

    @EventHandler
    fun onAuthSuccess(event: AuthExpireEvent) {
        val prefix = ConfigManager().getConfigManager(PluginInstanceManager().getPlugin()).getPluginPrefix()
        ProxyServer.getInstance().getPlayer(event.getUUID())
            ?.sendMessage(TextComponent("$prefix §cYour verified session is ended. When you logout, You need verify again."))
        AuthInformationManager().addAuthorizeExpiredUser(event.getUUID())
    }
}