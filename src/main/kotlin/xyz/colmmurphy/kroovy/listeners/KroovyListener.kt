package xyz.colmmurphy.kroovy.listeners

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import xyz.colmmurphy.kroovy.lavaplayer.AudioPlayerSendHandler
import xyz.colmmurphy.kroovy.Kroovy
import xyz.colmmurphy.kroovy.lavaplayer.GuildMusicManager
import xyz.colmmurphy.kroovy.lavaplayer.PlayerManager
import java.net.URI
import java.net.URISyntaxException

class KroovyListener : ListenerAdapter() {
    companion object {
        const val prefix = Kroovy.prefix
    }
    override fun onMessageReceived(e: MessageReceivedEvent) {
        if (e.author.isBot || e.isWebhookMessage || !e.message.contentRaw.startsWith(Kroovy.prefix)) return

        val message = e.message
        val author = e.author
        val channel = e.channel
        val cmds = message.contentRaw.split(" ")

        when (cmds[0]) {
            "${prefix}join" -> {
                val channel = e.textChannel
                val self = e.guild.selfMember
                val selfVoiceState = self.voiceState!!

                if (selfVoiceState.inVoiceChannel()) {
                    channel.sendMessage("I am already in a voice channel")
                        .queue()
                    return
                }

                val memberVoiceState = e.member!!.voiceState!!
                if (!memberVoiceState.inVoiceChannel()) {
                    channel.sendMessage("You need to be in a voice channel for this command to work")
                        .queue()
                }

                val audioManager = e.guild.audioManager
                val memberChannel = memberVoiceState.channel!!

                audioManager.openAudioConnection(memberChannel)

                channel.sendMessageFormat("Connecting to `\uD83D\uDD0A %s`", memberChannel.name)
                    .queue()
            }
            "${prefix}leave" -> {
                if (e.guild.selfMember.voiceState!!.inVoiceChannel()) {
                    e.guild.audioManager.closeAudioConnection()
                }
            }
            "${prefix}play" -> {
                val channel = e.textChannel
                val self = e.guild.selfMember
                val selfVoiceState = e.guild.selfMember.voiceState!!

                if (!selfVoiceState.inVoiceChannel()) {
                    channel.sendMessage("I need to be in a voice channel to use this command")
                        .queue()
                    return
                }

                val member = e.member!!
                val memberVoiceState = member.voiceState!!
                if (!memberVoiceState.inVoiceChannel()) {
                    channel.sendMessage("You need to be in a voice channel for this command to work")
                        .queue()
                    return
                }

                if (memberVoiceState.channel!! != selfVoiceState.channel) {
                    e.channel.sendMessage("You need to be in the same voice channel as me for this command to work")
                        .queue()
                }

                channel.sendMessage("ok")
                    .queue()

                var link = message.contentRaw.substringAfter("${prefix}play ")

                if (!isUrl(link)) {
                   link = "ytsearch: " + link
                }
                PlayerManager.loadAndPlay(channel, link)
            }
            "${prefix}skip" -> {
                PlayerManager.getMusicManager(e.guild).scheduler.nextTrack()
            }
            else -> {
                channel.sendMessage("`${cmds[0]}` is not a valid command")
                    .queue()
            }
        }
    }

    private fun isUrl(url: String): Boolean {
        try {
            val uri = URI(url)
        } catch (e: URISyntaxException) {
            return false
        }
        return true
    }
}