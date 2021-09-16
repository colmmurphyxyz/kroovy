package xyz.colmmurphy.kroovy.commands.util

import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.CommandHandleError
import xyz.colmmurphy.kroovy.commands.KroovyCommand
import kotlin.properties.Delegates
import kotlin.reflect.full.memberProperties

class PingCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name: String = "ping"
    override val description: String = "Ping the bot's response time"
    override val example: String = "`/ping`"

    companion object {
        val name = "foo"
    }

    var time: Long = 0L

    override fun handle(): CommandHandleError? {
        time = System.currentTimeMillis()
        return null
    }

    override fun execute() {
        event.reply("Pong!").setEphemeral(false)
            .queue { hook -> hook.editOriginal("Pong! ${System.currentTimeMillis() - time} ms").queue() }
    }
}