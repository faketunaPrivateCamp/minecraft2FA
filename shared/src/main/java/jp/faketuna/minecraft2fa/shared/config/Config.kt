package jp.faketuna.minecraft2fa.shared.config


open class Config {
    object Config{
        private var token: String = ""
        private var roleID: Long = -1

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
    }
}