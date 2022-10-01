package jp.faketuna.minecraft2fa.waterfall.event

import com.google.common.io.ByteStreams
import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.connection.Server
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.UUID

class PluginMessageEventListener: Listener {

    @EventHandler
    fun onPluginMessage(event: PluginMessageEvent){
        if (!event.tag.equals("mc2fa:authentication", ignoreCase = true)) return

        val inp = ByteStreams.newDataInput(event.data)
        val subChannel = inp.readUTF()
        val userUUID = inp.readUTF()

        if (subChannel.equals("authInformationShare", ignoreCase = true)){
            // from server
            if (event.receiver is ProxiedPlayer) {
                val receiver = event.receiver as ProxiedPlayer
                sendAuthData(receiver.server, UUID.fromString(userUUID))
            }

            // to server
            if (event.receiver is Server){
                val receiver = event.receiver as Server
            }
        }
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