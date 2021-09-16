package xyz.colmmurphy.kroovy.lavaplayer

import xyz.colmmurphy.kroovy.lavaplayer.GuildMusicManager
import java.util.HashMap
import kotlin.jvm.JvmStatic
import xyz.colmmurphy.kroovy.lavaplayer.test
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import net.dv8tion.jda.api.entities.Guild

object test {
    private val musicManagers: MutableMap<String, GuildMusicManager> = HashMap()
    @JvmStatic
    fun main(args: Array<String>) {
    }

    fun getMusicManager(guild: Guild): GuildMusicManager {
        return musicManagers.computeIfAbsent(guild.id) { guildId: String? ->
            val guildMusicManager = GuildMusicManager(DefaultAudioPlayerManager())
            guildMusicManager
        }
    }
}