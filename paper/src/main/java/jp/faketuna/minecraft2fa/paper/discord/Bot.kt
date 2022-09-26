package jp.faketuna.minecraft2fa.paper.discord

import jp.faketuna.minecraft2fa.shared.discord.DiscordBot
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class Bot(private val token: String): DiscordBot(token) {
    private var jda: JDA = DiscordObject.getJDAInstance()

    init {
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        TODO()
    }
}