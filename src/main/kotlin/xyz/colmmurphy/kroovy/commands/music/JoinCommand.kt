package xyz.colmmurphy.kroovy.commands.music

import net.dv8tion.jda.api.entities.GuildVoiceState
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.CommandHandleException
import xyz.colmmurphy.kroovy.commands.KroovyCommand

class JoinCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name = "join"

    private lateinit var memberVoiceState: GuildVoiceState

    override fun handle() {
        val self = event.guild!!.selfMember
        val selfVoiceState = self.voiceState!!

        if (selfVoiceState.inVoiceChannel()) {
            throw CommandHandleException("I am already in a voice channel")
        }

        memberVoiceState = event.member!!.voiceState!!
        if (!memberVoiceState.inVoiceChannel()) {
            throw CommandHandleException("You need to be in a voice channel to use this command")
        }
    }

    override fun execute() {
        val audioManager = event.guild!!.audioManager
        val memberChannel = memberVoiceState.channel!!

        audioManager.openAudioConnection(memberChannel)

        event.reply("Connecting to `\uD83D\uDD0A ${memberChannel.name}`")
            .queue()
    }
}