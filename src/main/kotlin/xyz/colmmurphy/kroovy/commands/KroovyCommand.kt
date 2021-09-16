package xyz.colmmurphy.kroovy.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

abstract class KroovyCommand {
    abstract var event: SlashCommandEvent
    abstract val name: String
    open val description: String? = null
    open val example: String? = null
    open val ownerOnly: Boolean = false

    abstract fun handle(): CommandHandleError?

    abstract fun execute()
}

//interface KroovyCommand {
//
//    val name: String
//    val description: String
//    val example: String
//
//    fun handle()
//
//    fun execute()
//
//}