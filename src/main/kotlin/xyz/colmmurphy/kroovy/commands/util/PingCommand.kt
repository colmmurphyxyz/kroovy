package xyz.colmmurphy.kroovy.commands.util

import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.CommandHandleException
import xyz.colmmurphy.kroovy.commands.KroovyCommand
import kotlin.properties.Delegates
import kotlin.reflect.full.memberProperties

class PingCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name = "ping"

    private val time: Long

    override fun handle() {
    }

    override fun execute() {
        event.reply("Pong!").setEphemeral(false)
            .queue { hook -> hook.editOriginal("Pong! ${System.currentTimeMillis() - time} ms").queue() }
    }

    init {
        time = System.currentTimeMillis()
    }
}