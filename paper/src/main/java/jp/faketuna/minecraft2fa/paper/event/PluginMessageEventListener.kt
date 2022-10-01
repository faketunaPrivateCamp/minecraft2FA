package jp.faketuna.minecraft2fa.paper.event

import com.google.common.io.ByteStreams
import jp.faketuna.minecraft2fa.shared.manager.AuthInformationManager
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.util.*

class PluginMessageEventListener: PluginMessageListener {
    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray?) {
        if (!channel.equals("mc2fa:authentication", ignoreCase = true)) return

        val inp = ByteStreams.newDataInput(message!!)
        val subChannel = inp.readUTF()
        if (subChannel.equals("authInformationShare", ignoreCase = true)){
            val uuid = UUID.fromString(inp.readUTF())
            val isVerified = inp.readBoolean()
            if (isVerified){
                AuthInformationManager().addAuthorizedUser(uuid)
            }
        }
    }
}