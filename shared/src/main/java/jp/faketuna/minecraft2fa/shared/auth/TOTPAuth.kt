package jp.faketuna.minecraft2fa.shared.auth

import com.warrenstrange.googleauth.GoogleAuthenticator
import com.warrenstrange.googleauth.GoogleAuthenticatorKey

class TOTPAuth{
    private val gAuth = GoogleAuthenticator()
    private lateinit var key: GoogleAuthenticatorKey

    fun generateSecretKey(): String{
        key = gAuth.createCredentials()
        return key.key
    }

    fun getBackupCodes(key: GoogleAuthenticatorKey): List<Int>?{
        if (!this::key.isInitialized) return null
        return key.scratchCodes
    }

    fun isValidCode(secretKey: String, verificationCode: Int): Boolean{
        return gAuth.authorize(secretKey, verificationCode)
    }
}