package jp.faketuna.minecraft2fa.shared.discord

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import jp.faketuna.minecraft2fa.shared.auth.AccountConnection
import jp.faketuna.minecraft2fa.shared.auth.AuthManager
import jp.faketuna.minecraft2fa.shared.config.Config
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.Modal
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.text.TextInput
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.FileUpload
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

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
        jda.upsertCommand("connect", "Discord integration command.")
            .addSubcommands(SubcommandData("cancel", "Used for cancelling the registration."))
            .addSubcommands(SubcommandData("start", "For stating registration."))
            .queue()
        jda.upsertCommand("auth", "2 factor authentication command.")
            .addSubcommands(SubcommandData("verify", "Verify the your minecraft session with 2fa."))
            .addSubcommands(SubcommandData("register", "Register the 2fa for this account."))
            .addSubcommands(SubcommandData("unregister", "Unregister the 2fa for this account."))
            .addSubcommands(SubcommandData("cancel", "Cancel the 2fa registration."))
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
                                    " Please execute `/connectdiscord ${ac.getTokenFromDiscordID()}` in minecraft server. or type `/connect cancel` in discord to cancel registration."
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
                                        " Please execute `/connectdiscord $token` in minecraft server."
                            )
                        }.queue()
                }

                if (event.subcommandName.equals("cancel", ignoreCase = true)){
                    if (!ac.isRegisterInProgress()){
                        event.reply("Registration is not started! to start, type `/connectdiscord start`").setEphemeral(true).queue()
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

        if(event.name == "auth"){
            if (event.member!!.roles.contains(event.member!!.guild.getRoleById(Config.Config.getRoleID()))){
                val discordID = event.member!!.idLong
                val authManager = AuthManager()
                if (event.subcommandName.equals("register", ignoreCase = true)){
                    if(authManager.isUserRegisteringProgress(discordID)){
                        event.reply("Your in registering progress! if you want cancel please type `/auth cancel`.")
                            .setEphemeral(true)
                            .queue()
                        return
                    }/*
                    if (!authManager.isUserHasIntegration(discordID)){
                        event.reply("Your not connected the minecraft with discord. Please `/connect start` in first.")
                            .setEphemeral(true)
                            .queue()
                    }

                    if (!authManager.isUserHas2FA(discordID)){
                        event.reply("Your already registered 2fa! to unregister type `/auth unregister`.")
                            .setEphemeral(true)
                            .queue()
                        return
                    }
                    */
                    val secretKey = authManager.generateSecretKey()
                    authManager.addRegisteringUser(discordID, secretKey)
                    val optAuthURI = "otpauth://totp/Minecraft2faAuthentication:${event.member!!.effectiveName}?secret=$secretKey&issuer=minecraft2fa"

                    try{
                        val writer = QRCodeWriter()
                        val bitMatrix = writer.encode(optAuthURI, BarcodeFormat.QR_CODE, 256, 256)
                        val image = MatrixToImageWriter.toBufferedImage(bitMatrix)
                        val bos = ByteArrayOutputStream()
                        ImageIO.write(image, "png", bos)
                        event.reply("Read QR code with Any topt compatible authenticator or paste key `$secretKey`.")
                            .addFiles(FileUpload.fromData(bos.toByteArray(), "qr.png"))
                            .setEphemeral(true)
                            .setActionRow(Button.primary("ready", "Verify code"))
                            .queue()
                    } catch (e: Exception){
                        e.printStackTrace()
                        event.reply("Failed to generate QR code! Please try again.")
                            .setEphemeral(true)
                            .queue()
                    }
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

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if (!event.member!!.roles.contains(event.member!!.guild.getRoleById(Config.Config.getRoleID()))) return
        if (event.componentId == "ready"){
            if (!AuthManager().isUserRegisteringProgress(event.member!!.idLong)) return
            val totpCode = TextInput.create("2fa-verification-input", "2FA authentication code", TextInputStyle.SHORT)
                .setMinLength(6)
                .setMaxLength(6)
                .setRequired(true)
                .build()
            val modal = Modal.create("2fa-verification-modal", "Enter 2FA code shown in your authenticator!")
                .addActionRows(ActionRow.of(totpCode))
                .build()
            event.replyModal(modal).queue()
        }
    }

    override fun onModalInteraction(event: ModalInteractionEvent) {
        if (!event.member!!.roles.contains(event.member!!.guild.getRoleById(Config.Config.getRoleID()))) return
        if (event.modalId == "2fa-verification-modal"){
            val verificationCode = event.getValue("2fa-verification-input")!!.asString.toInt()
            val authManager = AuthManager()
            if (authManager.isValidCode(authManager.getSecretKeyFromRegisteringUser(event.member!!.idLong).toString(), verificationCode)){
                event.reply("Code verified and registered 2fa!")
                    .setEphemeral(true)
                    .queue()
            } else{
                event.reply("Invalid code! Please try again.")
                    .setEphemeral(true)
                    .queue()
            }
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