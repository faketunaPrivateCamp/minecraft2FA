package jp.faketuna.minecraft2fa.shared.auth

import kotlin.collections.HashMap

class AccountConnection {
    object VerificationObject{
        private var tokenMap = HashMap<String, Long>()

        fun getTokenMap(): HashMap<String, Long>{
            return tokenMap
        }

        fun setTokenMap(map: HashMap<String, Long>){
            tokenMap = map
        }
    }


    fun isValidToken(token: String): Boolean{
        val discordID: Long? = VerificationObject.getTokenMap()[token]
        if (discordID != null){
            return true
        }
        return false
    }

    fun registerToken(discordID: Long): String{
        val alphabet = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val len = 12
        val token = (1..len).map { alphabet.random() }.joinToString("")
        VerificationObject.getTokenMap()[token] = discordID
        return token
    }

    fun removeToken(token: String){
        VerificationObject.getTokenMap().remove(token)
    }

    fun removeToken(discordID: Long){
        for (map in VerificationObject.getTokenMap()){
            if (map.value == discordID){
                VerificationObject.getTokenMap().remove(map.key)
            }
        }
    }
}