package jp.faketuna.minecraft2fa.shared.auth

import com.warrenstrange.googleauth.GoogleAuthenticator
import com.warrenstrange.googleauth.GoogleAuthenticatorKey
import jp.faketuna.minecraft2fa.shared.manager.SharedPluginInstanceManager
import java.math.BigInteger
import java.security.MessageDigest

class AuthManager {
    private val manager = SharedPluginInstanceManager()
    private val mysql = manager.getMySQLInstance()
    private val gAuth = GoogleAuthenticator()
    private lateinit var key: GoogleAuthenticatorKey

    object Manager{
        private val registeringUsers = HashMap<Long, String>()
        private val registeringUsersBackupCode = HashMap<Long, List<Int>>()

        fun addRegisteringUser(discordID: Long, token: String){
            registeringUsers[discordID] = token
        }

        fun removeRegisteringUser(discordID: Long){
            registeringUsers.remove(discordID)
        }

        fun getSecretKeyFromRegisteringUser(discordID: Long): String?{
            return registeringUsers[discordID]
        }

        fun isUserRegisteringProgress(discordID :Long): Boolean{
            if (registeringUsers.keys.contains(discordID)) return true
            return false
        }

        fun setUserBackupCode(discordID: Long ,codes: List<Int>){
            this.registeringUsersBackupCode[discordID] = codes
        }

        fun getUserBackupCode(discordID: Long): List<Int>?{
            return this.registeringUsersBackupCode[discordID]
        }
    }

    fun isUserHas2FA(discordID: Long): Boolean{
        val userInfo: HashMap<String, String?>
        try {
             userInfo = mysql.getDiscordIntegrationInformation(discordID)
        } catch (e: Exception){
            return false
        }
        for (map in userInfo){
            if (map.key.contains("auth_id") && !map.value.isNullOrEmpty()) return true
        }
        return false
    }

    fun isUserHasIntegration(discordID: Long): Boolean{
        val userInfo: HashMap<String, String?>
        try{
            userInfo = mysql.getDiscordIntegrationInformation(discordID)
        } catch (e: Exception){
            return false
        }
        for (map in userInfo){
            if (map.key == "discord_id" && map.value == "$discordID") return true
        }
        return true
    }

    fun addRegisteringUser(discordID: Long, token: String){
        Manager.addRegisteringUser(discordID, token)
    }

    fun removeRegisteringUser(discordID: Long){
        Manager.removeRegisteringUser(discordID)
    }

    fun getSecretKeyFromRegisteringUser(discordID: Long): String?{
        return Manager.getSecretKeyFromRegisteringUser(discordID)
    }

    fun isUserRegisteringProgress(discordID: Long): Boolean{
        return Manager.isUserRegisteringProgress(discordID)
    }

    fun generateSecretKey(): GoogleAuthenticatorKey{
        key = gAuth.createCredentials()
        return key
    }

    fun setBackupCodes(discordID: Long, key: GoogleAuthenticatorKey){
        Manager.setUserBackupCode(discordID, key.scratchCodes)
    }

    fun getBackupCodes(discordID: Long): List<Int>?{
        return Manager.getUserBackupCode(discordID)
    }

    fun isValidCode(secretKey: String, verificationCode: Int): Boolean{
        return gAuth.authorize(secretKey, verificationCode)
    }

    fun generateHash(discordID: Long, secretKey: String): String{
        val sha3512 = MessageDigest.getInstance("SHA3-512")
        val result = sha3512.digest("$discordID-$secretKey".toByteArray())
        return String.format("%040x", BigInteger(1, result))
    }

}