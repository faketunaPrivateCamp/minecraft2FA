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
    fun getDiscordIntegrationInformation(discordID: Long): HashMap<String, String?>?

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
     * Removes Discord integration information from Discord integration database
     * @param discordID
     */
    fun removeDiscordIntegrationInformation(discordID: Long)

    /**
     * Get a 2fa information from Discord integration database
     * @param authID String
     * @return HashMap<String, String?>
     */
    fun get2FAInformation(authID: String): HashMap<String, String?>?

    /**
     * Sets a 2fa information to Discord integration database
     * @param authID String
     * @param secretKey String
     * @param backupCodes String
     */
    fun add2FAInformation(authID: String, secretKey: String, backupCodes: String)

    /**
     * Updates a 2fa Secret key information to Discord integration database
     * @param authID String
     * @param secretKey String
     */
    fun update2FASecretKeyInformation(authID: String, secretKey: String)

    /**
     * Updates a 2fa Secret key information to Discord integration database
     * @param authID String
     * @param backupCodes String
     */
    fun update2FABackupCodeInformation(authID: String, backupCodes: String)

    /**
     * Removes 2fa information from Discord integration database
     * @param authID
     */
    fun remove2FAInformation(authID: String)

    /**
     * Checks Database are exists
     * @return Boolean
     */
    fun isDatabaseExists(): Boolean

    /**
     * Checks table is exists
     * @return Boolean
     */
    fun isTablesExists(): Boolean

    /**
     * Checks table is exists
     * @return Boolean
     */
    fun isDiscordIntegrationTableExists(): Boolean

    /**
     * Checks table is exists
     * @return Boolean
     */
    fun is2FATableExists(): Boolean

    /**
     * Create a database for Discord integration
     */
    fun createDiscordIntegrationDatabase()

    /**
     * Creates a table for Discord integration
     */
    fun createDiscordIntegrationTable()

    /**
     * Creates a table for 2fa
     */
    fun create2FATable()
}