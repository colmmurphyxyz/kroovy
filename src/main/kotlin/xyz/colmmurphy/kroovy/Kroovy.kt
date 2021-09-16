package xyz.colmmurphy.kroovy

import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
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
        }
    }
}

fun main() = Kroovy.main()