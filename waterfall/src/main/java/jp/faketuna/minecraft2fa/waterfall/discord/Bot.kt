package jp.faketuna.minecraft2fa.waterfall.discord

import jp.faketuna.minecraft2fa.shared.discord.DiscordBot
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class Bot(private val token: String, private val intents: Int): DiscordBot(token, intents){
    private var jda: JDA = DiscordObject.getJDAInstance()

    init {
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name == "test"){
            event.reply("Bot name: ${jda.status.name}")
                .setEphemeral(true)
                .queue()
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if(event.message.contentRaw == "test"){
            event.message.reply("Bot status: ${jda.status.name}")
                .queue()
        }
    }
}