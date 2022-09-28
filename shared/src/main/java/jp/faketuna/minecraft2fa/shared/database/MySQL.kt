package jp.faketuna.minecraft2fa.shared.database

import jp.faketuna.minecraft2fa.shared.objcets.Database
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.util.UUID

class MySQL(private val connectionAddress: String, private val user: String, private val password: String): Database {
    private val integrationTableName = "minecraft2fa_discord_integration"
    private val authDataTableName = "minecraft2fa_auth_data"
    private val databaseName = "minecraft2fa"
    override val address = "jdbc:mysql://$connectionAddress/$databaseName"

    override fun getDiscordIntegrationInformation(discordID: Long): HashMap<String, String?>?{
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
            return null
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
        var response: ResultSet? = null


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("INSERT INTO $integrationTableName(discord_id, minecraft_uuid) VALUES($discordID, $minecraftUUID)")
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
    }

    override fun updateDiscordIntegrationMinecraftUUID(discordID: Long, minecraftUUID: UUID){
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("UPDATE $integrationTableName SET minecraft_uuid = \'$minecraftUUID\' WHERE discord_id = $discordID")
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
    }

    override fun updateDiscordIntegrationAuthID(discordID: Long, authID: String){
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("UPDATE $integrationTableName SET auth_id = \'$authID\' WHERE discord_id = $discordID")
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
    }

    override fun get2FAInformation(authID: String): HashMap<String, String?>?{
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
            return null
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
        return result
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

        try{
            connection = DriverManager.getConnection(address, user, password)
            meta = connection.metaData
            response = meta.getTables(null, null, integrationTableName, null)
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            connection?.close()
        }
        if(response!!.next()){
            return true
        }
        return false
    }

    override fun is2FATableExists(): Boolean{
        var connection: Connection? = null
        var meta: DatabaseMetaData? = null
        var response: ResultSet? = null

        try{
            connection = DriverManager.getConnection(address, user, password)
            meta = connection.metaData
            response = meta.getTables(null, null, authDataTableName, null)
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            connection?.close()
        }
        if(response!!.next()){
            return true
        }
        return false
    }

    override fun createDiscordIntegrationTable() {
        val createTableSQL: String = "CREATE TABLE $integrationTableName(" +
                "discord_id BIGINT(18) NOT NULL, " +
                "minecraft_uuid VARCHAR(36) NOT NULL, " +
                "auth_id VARCHAR(128), " +
                "PRIMARY KEY (discord_id)" +
                ")"
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null

        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery(createTableSQL)
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
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
        var response: ResultSet? = null

        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery(createTableSQL)
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
    }
}