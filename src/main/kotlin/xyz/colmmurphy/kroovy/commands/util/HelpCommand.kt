package xyz.colmmurphy.kroovy.commands.util

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.Kroovy
import xyz.colmmurphy.kroovy.commands.CommandHandleError
import xyz.colmmurphy.kroovy.commands.KroovyCommand
import kotlin.reflect.full.*

class HelpCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name: String = "help"
    override val description = "displays this menu"


    override fun handle(): CommandHandleError? {
        return null
    }

    /**
     * I know this creates an object for every single command.
     * If you know of a better way to do this, submit a pull request or DM Murf#5949 on Discord
     */
    override fun execute() {
        var helpMenu = ""
//        Kroovy.jda.retrieveCommands()
//            .queue { commands ->
//                for (cmd in commands) {
//                    helpMenu += "**${cmd.name}** - ${cmd.description}\n"
//                }
//            }
//        event.reply(helpMenu).setEphemeral(false)
//            .queue()

        for (cmd in Kroovy.commands.values) {
            val foo = cmd.createInstance()
            helpMenu += "**${foo.name}** - ${foo.description}\n"
        }
        event.reply(helpMenu).setEphemeral(false)
            .queue()
    }

}