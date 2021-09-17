package xyz.colmmurphy.kroovy.commands.music

import net.dv8tion.jda.api.entities.GuildVoiceState
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.CommandHandleException
import xyz.colmmurphy.kroovy.commands.KroovyCommand

class LeaveCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name = "leave"

    private lateinit var memberVoiceState: GuildVoiceState

    override fun handle() {
        val self = event.guild!!.selfMember
        val selfVoiceState = self.voiceState!!

        if (!selfVoiceState.inVoiceChannel()) {
            throw CommandHandleException("I am not in a voice channel")
        }

        memberVoiceState = event.member!!.voiceState!!
        if (!memberVoiceState.inVoiceChannel()) {
            throw CommandHandleException("You need to be in a voice channel to use this command")
        }
    }

    override fun execute() {
        event.guild!!.audioManager.closeAudioConnection()

        event.reply("slán")
            .queue()
    }
}