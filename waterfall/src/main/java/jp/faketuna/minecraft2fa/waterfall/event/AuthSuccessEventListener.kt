package jp.faketuna.minecraft2fa.waterfall.event

import com.google.common.io.ByteStreams
import jp.faketuna.minecraft2fa.shared.config.ConfigManager
import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import jp.faketuna.minecraft2fa.waterfall.manager.PluginInstanceManager
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.Server
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.*
import java.util.concurrent.TimeUnit

class AuthSuccessEventListener: Listener {

    @EventHandler
    fun onAuthSuccess(event: AuthSuccessEvent) {
        // TODO PluginMessaging 処理
        AuthInformationManager().addAuthorizedUser(event.getUUID())
        val prefix = ConfigManager().getConfigManager(PluginInstanceManager().getPlugin()).getPluginPrefix()
        val player = ProxyServer.getInstance().getPlayer(event.getUUID())
        if (player != null){
            println("player has in")
            player.sendMessage(TextComponent("$prefix §aYour now verified and can execute moderation commands!"))
            sendAuthData(player.server, player.uniqueId)
        }
        ProxyServer.getInstance().scheduler.schedule(
            PluginInstanceManager().getPlugin(),
            Runnable {
                AuthInformationManager().addAuthorizeExpiredUser(event.getUUID())
            },
            PluginInstanceManager().getConfigManager(PluginInstanceManager().getPlugin()).getSessionExpireTime(),
            TimeUnit.SECONDS
        )
    }

    private fun sendAuthData(server: Server, uuid: UUID){
        val networkPlayers = ProxyServer.getInstance().players
        if (networkPlayers.isNullOrEmpty()) return

        val outStream = ByteStreams.newDataOutput()
        outStream.writeUTF("authInformationShare")
        outStream.writeUTF(uuid.toString())
        outStream.writeBoolean(AuthInformationManager().isUserAuthorized(uuid))

        server.sendData("mc2fa:authentication", outStream.toByteArray())
    }
}