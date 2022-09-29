package jp.faketuna.minecraft2fa.shared.discord

import jp.faketuna.minecraft2fa.shared.auth.AccountConnection
import jp.faketuna.minecraft2fa.shared.config.Config
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
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
        jda.upsertCommand("connect", "Start integration")
            .addSubcommands(SubcommandData("cancel", "Used for cancelling the registration."))
            .addSubcommands(SubcommandData("start", "For stating registration."))
            .queue()
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
                val discordID = event.member!!.idLong
                val ac = AccountConnection(discordID)
                if (event.subcommandName.equals("start", ignoreCase = true)) {
                    if (ac.isRegisterInProgress()) {
                        event.reply(
                            "You are already in the registration process!" +
                                    " Please execute `/connect ${ac.getTokenFromDiscordID()}` in minecraft server. or type `/connect cancel` in discord to cancel registration."
                        )
                            .setEphemeral(true)
                            .queue()
                        return
                    }

                    val token = ac.registerToken()
                    event.reply("Generating token...")
                        .setEphemeral(true)
                        .flatMap {
                            event.hook.editOriginal(
                                "Token generated," +
                                        " Please execute `/connect $token` in minecraft server."
                            )
                        }.queue()
                }

                if (event.subcommandName.equals("cancel", ignoreCase = true)){
                    if (!ac.isRegisterInProgress()){
                        event.reply("Registration is not started! to start, type `/connect start`").setEphemeral(true).queue()
                        return
                    }
                    ac.removeToken()
                    event.reply("Registration cancelled.").setEphemeral(true).queue()
                }
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