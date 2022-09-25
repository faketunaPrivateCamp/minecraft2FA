package jp.faketuna.minecraft2fa.shared.config


open class Config {
    object Config{
        private var token: String = ""

        fun getToken(): String{
            return token
        }

        fun setToken(token: String){
            this.token = token
        }
    }
}