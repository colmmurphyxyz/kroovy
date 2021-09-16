package xyz.colmmurphy.kroovy.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import net.dv8tion.jda.api.audio.AudioSendHandler

class GuildMusicManager(manager: AudioPlayerManager) {

    val audioPlayer: AudioPlayer
    val scheduler: TrackScheduler
    private val sendHandler: AudioPlayerSendHandler

    fun getSendHandler(): AudioPlayerSendHandler {
        return sendHandler
    }

    init {
        this.audioPlayer = manager.createPlayer()
        this.scheduler = TrackScheduler(this.audioPlayer)

        this.audioPlayer.addListener(this.scheduler)
        this.sendHandler = AudioPlayerSendHandler(this.audioPlayer)
    }
}