package xyz.colmmurphy.kroovy.commands.music

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.Command
import xyz.colmmurphy.kroovy.commands.CommandHandleException
import xyz.colmmurphy.kroovy.commands.KroovyCommand
import xyz.colmmurphy.kroovy.lavaplayer.PlayerManager
import xyz.colmmurphy.kroovy.listeners.KroovyListener
import java.net.URI
import java.net.URISyntaxException

class PlayCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name: String = "foo"

    override fun handle() {
        val selfVoiceState = event.guild!!.selfMember.voiceState!!

        val member = event.member!!
        val memberVoiceState = member.voiceState!!
        if (!memberVoiceState.inVoiceChannel()) {
            throw(CommandHandleException("You need to be in a voice channel to use this command"))
        }

        if (!selfVoiceState.inVoiceChannel()) {
//            throw(CommandHandleException("I need to be in a voice channel for this command to work"))
            val audioManager = event.guild!!.audioManager
            val memberChannel = memberVoiceState.channel!!

            audioManager.openAudioConnection(memberChannel)
            return;
        }

        if (memberVoiceState.channel!! != selfVoiceState.channel) {
            throw(CommandHandleException("You need to be in the same voice channel as me to use this command"))
        }
    }

    override fun execute() {
        var link = event.getOption("track")!!.asString
        println(link)
        // the isUrl method returns true on single-word inputs e.g "everlong", for this reason I've disabled it for the time being
        if (!isUrl(link) || true) {
            link = "ytsearch: $link"
        }

        PlayerManager.loadAndPlay(event.textChannel, link)
        event.reply("\uD83D\uDC4D")
            .queue()
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