package xyz.colmmurphy.kroovy.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler
import java.nio.ByteBuffer

class AudioPlayerSendHandler(
    private val audioPlayer: AudioPlayer
    ) : AudioSendHandler {

    private val buffer: ByteBuffer = ByteBuffer.allocate(1024)
    private val frame: MutableAudioFrame = MutableAudioFrame()

    override fun canProvide(): Boolean {
        return audioPlayer.provide(frame)
    }

    override fun provide20MsAudio(): ByteBuffer {
        return buffer.flip()
    }

    override fun isOpus(): Boolean = true

    init {
        frame.setBuffer(buffer)
    }
}