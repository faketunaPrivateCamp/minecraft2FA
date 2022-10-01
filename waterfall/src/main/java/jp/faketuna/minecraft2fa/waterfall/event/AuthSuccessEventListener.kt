package jp.faketuna.minecraft2fa.waterfall.event

import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import jp.faketuna.minecraft2fa.waterfall.manager.PluginInstanceManager
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.concurrent.TimeUnit

class AuthSuccessEventListener: Listener {

    @EventHandler
    fun onAuthSuccess(event: AuthSuccessEvent){
        // TODO PluginMessaging 処理
        AuthInformationManager().addAuthorizedUser(event.getUUID())
        ProxyServer.getInstance().scheduler.schedule(PluginInstanceManager().getPlugin(), Runnable {
            AuthInformationManager().removeAuthorizedUser(event.getUUID())
        }, PluginInstanceManager().getConfigManager(PluginInstanceManager().getPlugin()).getSessionExpireTime(), TimeUnit.SECONDS)
    }
}