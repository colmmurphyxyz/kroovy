package xyz.colmmurphy.kroovy.commands.admin

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.CommandHandleException
import xyz.colmmurphy.kroovy.commands.KroovyCommand

class ShutDownCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name: String = "shutdown"
    override val ownerOnly = true

    override fun handle() {
        if (event.member!!.id != "417097416085602315") {
            throw CommandHandleException("Only the bot owner can use this command")
        }
    }

    override fun execute() {
        event.reply("shutting down")
            .queue { kotlin.system.exitProcess(2) }

        kotlin.system.exitProcess(2)
    }
}