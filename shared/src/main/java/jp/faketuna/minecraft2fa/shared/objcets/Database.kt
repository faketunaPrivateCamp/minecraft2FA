package jp.faketuna.minecraft2fa.shared.objcets


import java.util.UUID
import kotlin.collections.HashMap

interface Database {
    val address: String

    /**
     * Get a User information from Discord integration database
     * @param discordID Long
     * @return HashMap<String, String?>
     */
    fun getDiscordIntegrationInformation(discordID: Long): HashMap<String, String?>

    /**
     * Adds User data to Discord integration database
     * @param discordID Long
     * @param minecraftUUID UUID
     */
    fun addDiscordIntegrationInformation(discordID: Long, minecraftUUID: UUID)

    /**
     * Updates a Discord ID of Discord integration database
     * @param discordID Long
     * @param minecraftUUID UUID
     */
    fun updateDiscordIntegrationMinecraftUUID(discordID: Long, minecraftUUID: UUID)

    /**
     * Updates a Authentication ID of Discord integration database
     * @param discordID Long
     * @param authID String
     */
    fun updateDiscordIntegrationAuthID(discordID: Long, authID: String)

    /**
     * Get a 2fa information from Discord integration database
     * @param authID String
     * @return HashMap<String, String?>
     */
    fun get2FAInformation(authID: String): HashMap<String, String?>
}