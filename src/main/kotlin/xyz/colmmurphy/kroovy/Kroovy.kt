package xyz.colmmurphy.kroovy

import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import xyz.colmmurphy.kroovy.listeners.KroovyListener
import java.io.File


val TOKEN = File("token.txt").readText()

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

            guild.upsertCommand(
                "ping", "Ping the bot's response time"
            ).queue { cmd: Command -> println("Upserted Command ${cmd.name}")}

            guild.upsertCommand(
                "play", "Play a video from YouTube",
            ).queue { _ -> println("Upserted command") }

            guild.upsertCommand(
                "skip", "Skip the currently playing track",
            ).queue { _ -> println("upserted command") }
            Unit
        }
    }
}

fun main() = Kroovy.main()