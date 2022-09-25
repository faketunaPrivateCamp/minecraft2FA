package jp.faketuna.minecraft2fa.shared.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

open class DiscordBot(private val token: String, private val intents: Int): ListenerAdapter() {

    private var jda: JDA = JDABuilder.createLight(token)
        .addEventListeners(this)
        .setActivity(Activity.playing("Coding"))
        .build()

    init {
        jda.upsertCommand("ping", "just a ping pong command").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name == "ping"){
            val time: Long = System.currentTimeMillis()
            event.reply("pong!").setEphemeral(true)
                .flatMap { event.hook.editOriginal("Pong: ${System.currentTimeMillis() - time}") }
                .queue()
        }
    }

    fun shutdownBot(){
        jda.shutdownNow()
    }
}