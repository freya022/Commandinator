package dev.freya02.commandinator.bot.localization.messages

import net.dv8tion.jda.api.interactions.Interaction

interface IMessageSourceFactory<out T : IMessageSource> {
    fun create(event: Interaction): T
}