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
    }

    override fun execute() {
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