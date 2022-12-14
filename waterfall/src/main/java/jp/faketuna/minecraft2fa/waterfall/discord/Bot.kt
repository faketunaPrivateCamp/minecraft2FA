package jp.faketuna.minecraft2fa.waterfall.discord

import jp.faketuna.minecraft2fa.shared.manager.AuthManager
import jp.faketuna.minecraft2fa.shared.discord.DiscordBot
import jp.faketuna.minecraft2fa.waterfall.event.AuthSuccessEvent
import jp.faketuna.minecraft2fa.waterfall.manager.PluginInstanceManager
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import java.util.*

class Bot(private val token: String): DiscordBot(token){
    private var jda: JDA = DiscordObject.getJDAInstance()

    init {
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        super.onSlashCommandInteraction(event)
    }

    override fun onModalInteraction(event: ModalInteractionEvent) {
        super.onModalInteraction(event)
        if (event.modalId == "2fa-verification-verify-modal"){
            val verificationCode = event.getValue("2fa-verification-verify-input")!!.asString.toInt()
            val authManager = AuthManager()
            val mysql = PluginInstanceManager().getMySQLInstance()
            val discordID = event.member!!.idLong
            val dii = mysql.getDiscordIntegrationInformation(discordID)
            val secretKey = mysql.get2FAInformation(dii["auth_id"].toString())["2fa_secret_key"].toString()
            if (authManager.isValidCode(secretKey, verificationCode)){
                try{
                    val uuid = UUID.fromString(dii["minecraft_uuid"])
                    PluginInstanceManager().getPlugin().proxy.pluginManager.callEvent(AuthSuccessEvent(uuid))
                } catch (e: Exception){
                    e.printStackTrace()
                    event.reply("An error occurred! Please try again.")
                        .setEphemeral(true)
                        .queue()
                    return
                }
                event.reply("Code verified! You can now use admin command in server!")
                    .setEphemeral(true)
                    .queue()
            } else{
                event.reply("Invalid code! Please try again.")
                    .setEphemeral(true)
                    .queue()
            }
        }
    }
}