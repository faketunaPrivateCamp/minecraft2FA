package jp.faketuna.minecraft2fa.shared.database

import jp.faketuna.minecraft2fa.shared.objcets.Database
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.UUID

class MySQL(private val connectionAddress: String, private val user: String, private val password: String): Database {
    private val integrationTableName = "minecraft2fa_discord_integration"
    private val authDataTableName = "minecraft2fa_auth_data"
    private val databaseName = "minecraft2fa"
    override val address = "jdbc:mysql://$connectionAddress/$databaseName"

    override fun getDiscordIntegrationInformation(discordID: Long): HashMap<String, String?>{
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        val result = HashMap<String, String?>()
        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("SELECT * FROM $integrationTableName WHERE discord_id = $discordID")
            result["discord_id"] = response.getLong("discord_id").toString()
            result["minecraft_uuid"] = response.getString("minecraft_uuid")
            result["auth_id"] = response.getString("auth_id")
        } catch (e: Exception){
            e.printStackTrace()
            response?.close()
            statement?.close()
            connection?.close()
            throw SQLException()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
        return result
    }

    override fun addDiscordIntegrationInformation(discordID: Long, minecraftUUID: UUID){
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate("INSERT INTO $integrationTableName(discord_id, minecraft_uuid) VALUES($discordID, $minecraftUUID)")
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun updateDiscordIntegrationMinecraftUUID(discordID: Long, minecraftUUID: UUID){
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate("UPDATE $integrationTableName SET minecraft_uuid = \'$minecraftUUID\' WHERE discord_id = $discordID")
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun updateDiscordIntegrationAuthID(discordID: Long, authID: String){
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate("UPDATE $integrationTableName SET auth_id = \'$authID\' WHERE discord_id = $discordID")
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun removeDiscordIntegrationInformation(discordID: Long) {
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate("DELETE FROM $integrationTableName WHERE discord_id = \'$discordID\'")
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun get2FAInformation(authID: String): HashMap<String, String?>{
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        val result = HashMap<String, String?>()
        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("SELECT * FROM $authDataTableName WHERE auth_id = $authID")
            result["2fa_secret_key"] = response.getString("2fa_secret_key")
            result["2fa_backup_codes"] = response.getString("2fa_backup_codes")
        } catch (e: Exception){
            e.printStackTrace()
            response?.close()
            statement?.close()
            connection?.close()
            throw SQLException()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
        return result
    }

    override fun add2FAInformation(authID: String, secretKey: String, backupCodes: String) {
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate("INSERT INTO $authDataTableName(auth_id, 2fa_secret_key, 2fa_backup_codes) VALUES($authID, $secretKey, $backupCodes)")
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun update2FASecretKeyInformation(authID: String, secretKey: String) {
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate("UPDATE $authDataTableName SET 2fa_secret_key = \'$secretKey\' WHERE auth_id = $authID")
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun update2FABackupCodeInformation(authID: String, backupCodes: String) {
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate("UPDATE $authDataTableName SET 2fa_backup_codes = \'$backupCodes\' WHERE auth_id = $authID")
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun remove2FAInformation(authID: String) {
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate("DELETE FROM $authDataTableName WHERE auth_id = \'$authID\'")
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun isDatabaseExists(): Boolean {
        var connection: Connection? = null

        try{
            connection = DriverManager.getConnection(address, user, password)
        } catch (e: Exception){
            e.printStackTrace()
            connection?.close()
            throw SQLException()
        } finally {
            connection?.close()
        }
        return true
    }

    override fun isTablesExists(): Boolean {
        val authTableExists = is2FATableExists()
        val diTableExists = isDiscordIntegrationTableExists()
        if(authTableExists == diTableExists && authTableExists){
            return true
        }
        return false
    }

    override fun isDiscordIntegrationTableExists(): Boolean{
        var connection: Connection? = null
        var meta: DatabaseMetaData? = null
        var response: ResultSet? = null
        val resp: Boolean

        try{
            connection = DriverManager.getConnection(address, user, password)
            meta = connection.metaData
            response = meta.getTables(null, null, integrationTableName, null)
            resp = response.next()
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            response?.close()
            connection?.close()
        }
        return resp
    }

    override fun is2FATableExists(): Boolean{
        var connection: Connection? = null
        var meta: DatabaseMetaData? = null
        var response: ResultSet? = null
        val resp: Boolean

        try{
            connection = DriverManager.getConnection(address, user, password)
            meta = connection.metaData
            response = meta.getTables(null, null, authDataTableName, null)
            resp = response.next()
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            response?.close()
            connection?.close()
        }
        return resp
    }

    override fun createDiscordIntegrationTable() {
        val createTableSQL: String = "CREATE TABLE $integrationTableName(" +
                "discord_id BIGINT(18) NOT NULL, " +
                "minecraft_uuid VARCHAR(36) NOT NULL, " +
                "auth_id VARCHAR(128), " +
                "PRIMARY KEY (discord_id)," +
                "FOREIGN KEY(auth_id)" +
                " REFERENCES $authDataTableName(auth_id)" +
                ")"
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int

        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate(createTableSQL)
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    override fun create2FATable() {
        val createTableSQL: String = "CREATE TABLE $authDataTableName(" +
                "auth_id VARCHAR(128) NOT NULL, " +
                "2fa_secret_key VARCHAR(128) NOT NULL, " +
                "2fa_backup_codes VARCHAR(128) NOT NULL, " +
                "PRIMARY KEY (auth_id)" +
                ")"
        var connection: Connection? = null
        var statement: Statement? = null
        val response: Int

        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeUpdate(createTableSQL)
        } catch (e: Exception){
            e.printStackTrace()
            throw SQLException()
        } finally {
            statement?.close()
            connection?.close()
        }
    }
}