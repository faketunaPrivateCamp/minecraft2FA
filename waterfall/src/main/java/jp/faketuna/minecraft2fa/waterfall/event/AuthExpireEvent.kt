package jp.faketuna.minecraft2fa.waterfall.event

import net.md_5.bungee.api.plugin.Event
import java.util.UUID

class AuthExpireEvent(private val uuid: UUID): Event() {
    fun getUUID(): UUID{
        return this.uuid
    }
}