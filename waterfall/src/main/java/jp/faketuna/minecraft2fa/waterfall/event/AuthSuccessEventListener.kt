package jp.faketuna.minecraft2fa.waterfall.event

import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class AuthSuccessEventListener: Listener {

    @EventHandler
    fun onAuthSuccess(event: AuthSuccessEvent){
        // TODO PluginMessaging 処理
        AuthInformationManager().addAuthorizedUser(event.getUUID())
    }
}