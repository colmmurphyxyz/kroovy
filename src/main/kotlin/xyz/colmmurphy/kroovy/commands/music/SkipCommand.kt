package xyz.colmmurphy.kroovy.commands.music

import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.KroovyCommand

class SkipCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name: String = "foo"

    override fun handle() {
        TODO("Not yet implemented")
    }

    override fun execute() {
        TODO("Not yet implemented")
    }
}