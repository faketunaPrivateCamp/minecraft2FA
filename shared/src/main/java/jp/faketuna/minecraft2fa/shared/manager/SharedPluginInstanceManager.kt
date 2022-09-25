package jp.faketuna.minecraft2fa.shared.manager

import jp.faketuna.minecraft2fa.shared.discord.DiscordBot

open class SharedPluginInstanceManager {
    object Manager{
        private lateinit var discordBot: DiscordBot

        fun getDiscordBotInstance(): DiscordBot{
            return this.discordBot
        }
        fun setDiscordBotInstance(discordBot: DiscordBot){
            this.discordBot = discordBot
        }
        fun isDiscordBotInitialized(): Boolean{
            return this::discordBot.isInitialized
        }
    }
}