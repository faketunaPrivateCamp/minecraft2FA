package jp.faketuna.minecraft2fa.shared.discord

import jp.faketuna.minecraft2fa.shared.auth.AccountConnection
import jp.faketuna.minecraft2fa.shared.config.Config
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
        jda.upsertCommand("connect", "Start integration").queue()
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

        if(event.name == "connect"){
            if (event.member!!.roles.contains(event.member!!.guild.getRoleById(Config.Config.getRoleID()))){
                val ac = AccountConnection()
                val token = ac.registerToken(event.member!!.idLong)
                event.reply("Generating token...")
                    .setEphemeral(true)
                    .flatMap { event.hook.editOriginal("Token generated." +
                            " Please execute `/connect $token` in server.") }.queue()
            } else{
                event.reply("This command only can executed from admins!")
                    .setEphemeral(true).queue()
            }
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
        if(event.message.contentRaw == "check"){
            event.message.reply("${AccountConnection.VerificationObject.getTokenMap()}")
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