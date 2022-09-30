package jp.faketuna.minecraft2fa.shared.manager

import java.util.UUID

class AuthInformationManager {
    object Manager{
        private val authorizedUser = mutableListOf<UUID>()

        fun addAuthorizedUser(uuid: UUID){
            authorizedUser.add(uuid)
        }
        fun removeAuthorizedUser(uuid: UUID){
            authorizedUser.remove(uuid)
        }
        fun isUserAuthorized(uuid: UUID): Boolean{
            return authorizedUser.contains(uuid)
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
}