package jp.faketuna.minecraft2fa.paper.event

import com.google.common.io.ByteStreams
import jp.faketuna.minecraft2fa.paper.manager.PluginInstanceManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val plugin = PluginInstanceManager().getPlugin()
        if (!event.player.hasPermission("mc2fa.connect")) return

        val outStream = ByteStreams.newDataOutput()
        outStream.writeUTF("authInformationShare")
        outStream.writeUTF(event.player.uniqueId.toString())

        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            event.player.sendPluginMessage(plugin, "mc2fa:authentication", outStream.toByteArray())
        }, 30)
    }
}