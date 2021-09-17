package xyz.colmmurphy.kroovy.commands

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import xyz.colmmurphy.kroovy.Kroovy
import kotlin.reflect.KClass

enum class Command(
    val commandName: String,
    val description: String,
    val example: String,
    val commandClass: KClass<out KroovyCommand>,
    val options: List<OptionData>? = null,
    val ownerOnly: Boolean = false
) {
    SHUTDOWN(
        "shutdown", "Fairly self-explanatory imo", "/shutdown",
        xyz.colmmurphy.kroovy.commands.admin.ShutDownCommand::class,
        null,
        true),

    JOIN("join", "Have the bot connect to a voice channel", "/join",
        xyz.colmmurphy.kroovy.commands.music.JoinCommand::class),
    LEAVE("leave", "Makes the bot leave the voice channel", "/leave",
        xyz.colmmurphy.kroovy.commands.music.LeaveCommand::class),
    PLAY("play", "Play a video from YouTube", "/play Never Gonna Give You Up Rick Astley",
        xyz.colmmurphy.kroovy.commands.music.PlayCommand::class,
        listOf(OptionData(OptionType.STRING, "track", "The name of the track to play", true))),
    SKIP("skip", "Skip the currently playing song", "/skip",
        xyz.colmmurphy.kroovy.commands.music.SkipCommand::class),
    QUEUE("queue", "View all songs currently in the queue", "/queue",
        xyz.colmmurphy.kroovy.commands.music.QueueCommand::class),

    HELP("help", "Displays this menu", "/help",
        xyz.colmmurphy.kroovy.commands.util.HelpCommand::class),
    PING("ping", "Ping the bot's response time", "/ping",
        xyz.colmmurphy.kroovy.commands.util.PingCommand::class);

    companion object {
        val commandsMap: HashMap<String, Command> = HashMap()

        init {
            values().forEach {
                commandsMap[it.commandName] = it
            }
        }

        fun upsertCommands(guild: Guild) {
            for (i in values()) {
                guild.upsertCommand(
                    i.commandName, i.description
                ).queue {
                    i.options?.let { options ->
                        guild.editCommandById(it.id).addOptions(options)
                            .queue()
                        println("added options to command ${i.commandName}")
                    }
                    println("Upserted command ${i.commandName}")

                }
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