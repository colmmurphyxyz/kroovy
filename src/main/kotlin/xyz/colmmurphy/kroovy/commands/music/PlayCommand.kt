package xyz.colmmurphy.kroovy.commands.music

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.CommandHandleError
import xyz.colmmurphy.kroovy.commands.KroovyCommand

class PlayCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name: String = "foo"
    override val description: String = "bar"
    override val example: String = "foobar"

    override fun handle(): CommandHandleError? {
        TODO("Not yet implemented")
    }

    override fun execute() {
        TODO("Not yet implemented")
    }
}