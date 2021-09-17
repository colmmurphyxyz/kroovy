package xyz.colmmurphy.kroovy.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

abstract class KroovyCommand {
    abstract var event: SlashCommandEvent
    abstract val name: String

    /**
     * Check for the necessary permissions or other shit before executing the command
     * @throws CommandHandleException if the bot cannot execute the command
     * (due to conditions not being met, missing permissions, etc...)
     */
    @Throws(CommandHandleException::class)
    abstract fun handle()

    abstract fun execute()
}