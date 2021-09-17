package xyz.colmmurphy.kroovy.commands

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import xyz.colmmurphy.kroovy.Kroovy
import kotlin.reflect.KClass

enum class Command(
    val commandName: String,
    val description: String,
    val example: String,
    val commandClass: KClass<out KroovyCommand>,
    val ownerOnly: Boolean = false
) {
    PLAY("play", "Play a video from YouTube", "/play Never Gonna Give You Up Rick Astley",
        xyz.colmmurphy.kroovy.commands.music.PlayCommand::class),
    SKIP("skip", "Skip the currently playing song", "/skip",
        xyz.colmmurphy.kroovy.commands.music.SkipCommand::class),
    HELP("help", "Displays this menu", "/help",
        xyz.colmmurphy.kroovy.commands.util.HelpCommand::class),
    PING("ping", "Ping the bot's response time", "/ping",
        xyz.colmmurphy.kroovy.commands.util.PingCommand::class);

    companion object {
        val commandsMap: HashMap<String, KClass<out KroovyCommand>> = HashMap()

        init {
            values().forEach {
                commandsMap[it.commandName] = it.commandClass
            }
        }

        fun upsertCommands(guild: Guild) {
            for (i in values()) {
                guild.upsertCommand(
                    i.commandName, i.description
                ).queue { println("Upserted command ${i.commandName}") }
            }
        }

        fun upsertCommands() {
            val guild = Kroovy.jda.getGuildById("781264866920235008")!!
            for (i in values()) {
                guild.upsertCommand(
                    i.commandName, i.description
                ).queue { println("Upserted command ${i.commandName}") }
            }
        }
    }
}