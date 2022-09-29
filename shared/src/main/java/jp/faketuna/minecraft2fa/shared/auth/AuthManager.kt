package jp.faketuna.minecraft2fa.shared.auth

import com.warrenstrange.googleauth.GoogleAuthenticator
import com.warrenstrange.googleauth.GoogleAuthenticatorKey
import jp.faketuna.minecraft2fa.shared.manager.SharedPluginInstanceManager

class AuthManager {
    private val manager = SharedPluginInstanceManager()
    private val mysql = manager.getMySQLInstance()
    private val gAuth = GoogleAuthenticator()
    private lateinit var key: GoogleAuthenticatorKey

    object Manager{
        private val registeringUsers = mutableListOf<Long>()

        fun addRegisteringUser(discordID: Long){
            registeringUsers.add(discordID)
        }

        fun removeRegisteringUser(discordID: Long){
            registeringUsers.remove(discordID)
        }

        fun isUserRegisteringProgress(discordID :Long): Boolean{
            if (registeringUsers.contains(discordID)) return true
            return false
        }
    }

    fun isUserHas2FA(discordID: Long): Boolean{
        val userInfo = mysql.getDiscordIntegrationInformation(discordID)
        for (map in userInfo){
            if (map.value!!.contains("auth_id")) return true
        }
        return false
    }

    fun addRegisteringUser(discordID: Long){
        Manager.addRegisteringUser(discordID)
    }

    fun removeRegisteringUser(discordID: Long){
        Manager.removeRegisteringUser(discordID)
    }

    fun isUserRegisteringProgress(discordID: Long){
        Manager.isUserRegisteringProgress(discordID)
    }

    fun generateSecretKey(): String{
        key = gAuth.createCredentials()
        return key.key
    }

    fun getBackupCodes(): List<Int>?{
        if (!this::key.isInitialized) return null
        return key.scratchCodes
    }

    fun isValidCode(secretKey: String, verificationCode: Int): Boolean{
        return gAuth.authorize(secretKey, verificationCode)
    }

}