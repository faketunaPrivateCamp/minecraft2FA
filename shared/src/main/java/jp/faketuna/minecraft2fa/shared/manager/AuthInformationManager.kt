package jp.faketuna.minecraft2fa.shared.manager

import java.util.UUID

class AuthInformationManager {
    object Manager{
        private val authorizedUser = mutableListOf<UUID>()
        private val authorizeExpiredUser = mutableListOf<UUID>()

        fun addAuthorizedUser(uuid: UUID){
            authorizedUser.add(uuid)
        }
        fun removeAuthorizedUser(uuid: UUID){
            authorizedUser.remove(uuid)
        }
        fun isUserAuthorized(uuid: UUID): Boolean{
            return authorizedUser.contains(uuid)
        }

        fun addAuthorizeExpiredUser(uuid: UUID){
            authorizeExpiredUser.add(uuid)
        }
        fun removeAuthorizeExpiredUser(uuid: UUID){
            authorizeExpiredUser.remove(uuid)
        }
        fun isUserAuthExpired(uuid: UUID): Boolean{
            return authorizeExpiredUser.contains(uuid)
        }

    }

    fun addAuthorizedUser(uuid: UUID){
        Manager.addAuthorizedUser(uuid)
    }
    fun removeAuthorizedUser(uuid: UUID){
        Manager.removeAuthorizedUser(uuid)
    }
    fun isUserAuthorized(uuid: UUID): Boolean{
        return Manager.isUserAuthorized(uuid)
    }

    fun addAuthorizeExpiredUser(uuid: UUID){
        Manager.addAuthorizeExpiredUser(uuid)
    }
    fun removeAuthorizeExpiredUser(uuid: UUID){
        Manager.removeAuthorizeExpiredUser(uuid)
    }
    fun isUserAuthExpired(uuid: UUID): Boolean{
        return Manager.isUserAuthExpired(uuid)
    }
}