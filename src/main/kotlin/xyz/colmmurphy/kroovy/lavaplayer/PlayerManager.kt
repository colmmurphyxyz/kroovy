package xyz.colmmurphy.kroovy.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import kotlin.collections.*
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel

object PlayerManager {

    val musicManagers: HashMap<String, GuildMusicManager> = HashMap<String, GuildMusicManager>()
    val audioPlayerManager: AudioPlayerManager

    init {
        audioPlayerManager = DefaultAudioPlayerManager()

        AudioSourceManagers.registerRemoteSources(audioPlayerManager)
        AudioSourceManagers.registerLocalSource(audioPlayerManager)
    }

    fun getMusicManager(guild: Guild): GuildMusicManager =
        musicManagers.computeIfAbsent(guild.id
        ) { guildId: String ->
            val guildMusicManager = GuildMusicManager(audioPlayerManager)

            guild.audioManager.sendingHandler = guildMusicManager.getSendHandler()

            guildMusicManager
        }

    fun loadAndPlay(channel: TextChannel, trackUrl: String) {
        val musicManager: GuildMusicManager = this.getMusicManager(channel.guild)

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack?) {
                musicManager.scheduler.queue(track!!)

                channel.sendMessage("Adding to queue: `")
                    .append(track.info.title)
                    .append("` by `")
                    .append(track.info.author)
                    .append("`")
                    .queue()
            }

            override fun playlistLoaded(playlist: AudioPlaylist?) {
                println("Playlist loaded")
            }

            override fun noMatches() {
                println("No Matches :(")
            }

            override fun loadFailed(exception: FriendlyException?) {
                println("load failed")
                exception?.printStackTrace()
            }

        })
    }

    fun getInstance(): PlayerManager {
        return this
    }
}