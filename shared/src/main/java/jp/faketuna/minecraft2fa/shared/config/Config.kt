package jp.faketuna.minecraft2fa.shared.config


open class Config {
    object Config{
        private var token: String = ""
        private var roleID: Long = -1
        private var mySQLServerAddress: String = ""
        private var mySQLUserID: String = ""
        private var mySQLUserPassword: String = ""
        private var sessionExpireTime: Long = 0
        private const val pluginPrefix: String = "[§6Minecraft2FA§r]"

        fun getToken(): String{
            return token
        }

        fun setToken(token: String){
            this.token = token
        }

        fun getRoleID(): Long{
            return roleID
        }

        fun setRoleID(roleID: Long){
            this.roleID = roleID
        }
        
        fun getMySQLServerAddress(): String{
            return this.mySQLServerAddress
        }        
        fun setMySQLServerAddress(address: String){
            this.mySQLServerAddress = address
        }
        
        fun getMySQLUserID(): String{
            return this.mySQLUserID
        }
        fun setMySQLUserID(userID: String){
            this.mySQLUserID = userID
        }

        fun getMySQLUserPassword(): String{
            return this.mySQLUserPassword
        }
        fun setMySQLUserPassword(password: String){
            this.mySQLUserPassword = password
        }

        fun getSessionExpireTime(): Long{
            return this.sessionExpireTime
        }
        fun setSessionExpireTime(expireTime: Long){
            this.sessionExpireTime = expireTime
        }

        fun getPluginPrefix(): String{
            return this.pluginPrefix
        }
    }
}