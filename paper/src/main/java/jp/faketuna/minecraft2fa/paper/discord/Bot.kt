package jp.faketuna.minecraft2fa.paper.discord

import jp.faketuna.minecraft2fa.shared.discord.DiscordBot

class Bot(private val token: String, private val intents: Int): DiscordBot(token) {
}