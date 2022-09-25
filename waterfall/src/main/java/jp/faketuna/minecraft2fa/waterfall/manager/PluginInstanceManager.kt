package jp.faketuna.minecraft2fa.waterfall.manager

import jp.faketuna.minecraft2fa.shared.discord.DiscordBot
import jp.faketuna.minecraft2fa.shared.manager.SharedPluginInstanceManager
import jp.faketuna.minecraft2fa.shared.objcets.InstanceManager

class PluginInstanceManager: SharedPluginInstanceManager(), InstanceManager {

    override fun getDiscordBotInstance(): DiscordBot {
        return Manager.getDiscordBotInstance()
    }

    override fun setDiscordBotInstance(discordBot: DiscordBot) {
        Manager.setDiscordBotInstance(discordBot)
    }

    override fun isDiscordBotInitialized(): Boolean {
        return Manager.isDiscordBotInitialized()
    }
}