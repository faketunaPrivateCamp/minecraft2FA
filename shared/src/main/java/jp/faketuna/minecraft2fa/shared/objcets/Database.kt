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
     * @return Execution result
     */
    fun addDiscordIntegrationInformation(discordID: Long, minecraftUUID: UUID): Int

    /**
     * Updates a Discord ID of Discord integration database
     * @param discordID Long
     * @param minecraftUUID UUID
     * @return Execution result
     */
    fun updateDiscordIntegrationMinecraftUUID(discordID: Long, minecraftUUID: UUID): Int

    /**
     * Updates a Authentication ID of Discord integration database
     * @param discordID Long
     * @param authID String
     * @return Execution result
     */
    fun updateDiscordIntegrationAuthID(discordID: Long, authID: String): Int

    /**
     * Removes Discord integration information from Discord integration database
     * @param discordID
     * @return Execution result
     */
    fun removeDiscordIntegrationInformation(discordID: Long): Int

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
     * @return Execution result
     */
    fun add2FAInformation(authID: String, secretKey: String, backupCodes: String): Int

    /**
     * Updates a 2fa Secret key information to Discord integration database
     * @param authID String
     * @param secretKey String
     * @return Execution result
     */
    fun update2FASecretKeyInformation(authID: String, secretKey: String): Int

    /**
     * Updates a 2fa Secret key information to Discord integration database
     * @param authID String
     * @param backupCodes String
     * @return Execution result
     */
    fun update2FABackupCodeInformation(authID: String, backupCodes: String): Int

    /**
     * Removes 2fa information from Discord integration database
     * @param authID
     * @return Execution result
     */
    fun remove2FAInformation(authID: String): Int

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
     * Creates a table for Discord integration
     * @return Execution result
     */
    fun createDiscordIntegrationTable(): Int

    /**
     * Creates a table for 2fa
     * @return Execution result
     */
    fun create2FATable(): Int
}