package xyz.colmmurphy.kroovy

import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import xyz.colmmurphy.kroovy.commands.Command
import xyz.colmmurphy.kroovy.commands.KroovyCommand
import xyz.colmmurphy.kroovy.listeners.KroovyListener
import java.io.File
import kotlin.reflect.KClass

val TOKEN = File("token.txt").readText()

const val PRODUCTION = false

class Kroovy {
    companion object {
        const val prefix = ";"

        lateinit var jda: JDA

        @JvmStatic
        fun main() = runBlocking {

            jda = JDABuilder.create(
                TOKEN,
                mutableListOf<GatewayIntent>(
                    GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.GUILD_MESSAGE_TYPING,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_MEMBERS
                )
            )
                .enableCache(
                    CacheFlag.VOICE_STATE
                )
                .addEventListeners(
                    KroovyListener()
                )
                .build()
                .awaitReady()

            println("logged in")

            val guild = jda.getGuildById("781264866920235008")!!

            if (!PRODUCTION) {
                Command.upsertCommands(guild)
            }

            for (command in Command.values()) {
                jda.upsertCommand(
                    command.commandName, command.description
                ).queue {
                    command.options?.let { options ->
                        jda.editCommandById(it.id).addOptions(options)
                            .queue()
                        println("added options to command ${command.commandName}")
                    }
                    println("Upserted command ${command.commandName}")
                }
            }

            println("xyz")
        }
    }
}

fun main() = Kroovy.main()