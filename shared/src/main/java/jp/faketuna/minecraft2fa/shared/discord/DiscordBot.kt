package jp.faketuna.minecraft2fa.shared.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent

open class DiscordBot(private val token: String): ListenerAdapter() {

    object DiscordObject{
        private lateinit var jda: JDA

        fun setJDAInstance(jda: JDA){
            this.jda = jda
        }

        fun getJDAInstance(): JDA{
            return this.jda
        }
    }

    private var jda: JDA = JDABuilder.createLight(token)
        .addEventListeners(this)
        .setActivity(Activity.playing("Coding"))
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .build()

    init {
        jda.upsertCommand("ping", "just a ping pong command").queue()
        DiscordObject.setJDAInstance(jda)
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name == "ping"){
            val time: Long = System.currentTimeMillis()
            event.reply("pong!")
                .setEphemeral(true)
                .flatMap { event.hook.editOriginal("Pong: ${System.currentTimeMillis() - time}") }
                .queue()
        }

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

    fun getJDAInstance(): JDA{
        return DiscordObject.getJDAInstance()
    }

    fun shutdownBot(){
        jda.shutdownNow()
    }
}