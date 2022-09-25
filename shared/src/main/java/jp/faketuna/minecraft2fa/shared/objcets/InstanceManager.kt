package jp.faketuna.minecraft2fa.shared.objcets

import jp.faketuna.minecraft2fa.shared.discord.DiscordBot

interface InstanceManager {

    fun getDiscordBotInstance(): DiscordBot

    fun setDiscordBotInstance(discordBot: DiscordBot)

    fun isDiscordBotInitialized(): Boolean
}