package xyz.colmmurphy.kroovy.commands.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.Kroovy
import xyz.colmmurphy.kroovy.commands.Command
import xyz.colmmurphy.kroovy.commands.KroovyCommand
import java.awt.Color
import kotlin.reflect.full.*

class HelpCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name: String = "help"


    override fun handle() {
        println("handling help command")
    }

    /**
     * I know this creates an object for every single command.
     * If you know of a better way to do this, submit a pull request or DM Murf#5949 on Discord
     */
    override fun execute() {
        println("executing help command")
        var helpMenu = ""
        for (cmd in Command.values()) {
            if (!cmd.ownerOnly) {
                helpMenu += "**${cmd.commandName}** - ${cmd.description}\n" +
                        "`${cmd.example}`\n\n"
            }
        }
        event.reply(helpMenu).setEphemeral(false)
            .queue()
    }

}