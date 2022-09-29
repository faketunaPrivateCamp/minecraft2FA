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
        private val registeringUsers = HashMap<Long, String>()

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
        try{
            mysql.getDiscordIntegrationInformation(discordID)
        } catch (e: Exception){
            return false
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