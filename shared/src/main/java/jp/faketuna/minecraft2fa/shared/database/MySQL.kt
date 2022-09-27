package jp.faketuna.minecraft2fa.shared.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.util.UUID

class MySQL(private val connectionAddress: String, private val user: String, private val password: String) {
    private val address = "jdbc:mysql://$connectionAddress"

    fun getDiscordIntegrationInformation(discordID: Long): HashMap<String, String?>{
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        val result = HashMap<String, String?>()
        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("SELECT * FROM minecraft2fa_discord_integration where discord_id = $discordID")
            result["discord_id"] = response.getLong("discord_id").toString()
            result["minecraft_uuid"] = response.getString("minecraft_uuid")
            result["auth_id"] = response.getString("auth_id")
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
        return result
    }

    fun addDiscordIntegrationInformation(discordID: Long, minecraftUUID: UUID){
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("INSERT INTO minecraft2fa_discord_integration(discord_id, minecraft_uuid) VALUES($discordID, $minecraftUUID)")
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
    }

    fun updateDiscordIntegrationMinecraftUUID(discordID: Long, minecraftUUID: UUID){
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("UPDATE minecraft2fa_discord_integration SET minecraft_uuid = \'$minecraftUUID\' WHERE discord_id = $discordID")
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
    }

    fun updateDiscordIntegrationAuthID(discordID: Long, authID: String){
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("UPDATE minecraft2fa_discord_integration SET auth_id = \'$authID\' WHERE discord_id = $discordID")
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
    }

    fun get2FAInformation(authID: String): HashMap<String, String?>{
        var connection: Connection? = null
        var statement: Statement? = null
        var response: ResultSet? = null


        val result = HashMap<String, String?>()
        try{
            connection = DriverManager.getConnection(address, user, password)
            statement = connection.createStatement()
            response = statement.executeQuery("SELECT * FROM minecraft2fa_auth_data where auth_id = $authID")
            result["2fa_secret_key"] = response.getString("2fa_secret_key")
            result["2fa_backup_codes"] = response.getString("2fa_backup_codes")
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            response?.close()
            statement?.close()
            connection?.close()
        }
        return result
    }
}