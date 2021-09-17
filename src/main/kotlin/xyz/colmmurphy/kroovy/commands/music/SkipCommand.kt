package xyz.colmmurphy.kroovy.commands.music

import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.CommandHandleException
import xyz.colmmurphy.kroovy.commands.KroovyCommand
import xyz.colmmurphy.kroovy.lavaplayer.PlayerManager

class SkipCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name: String = "foo"

    override fun handle() {
        val guildMusicManager = PlayerManager.getMusicManager(event.guild!!)
        if (guildMusicManager.scheduler.getQueue().isEmpty()) {
            throw CommandHandleException("There is nothing to skip to")
        }
    }

    override fun execute() {
        val guildMusicManager = PlayerManager.getMusicManager(event.guild!!)
        guildMusicManager.scheduler.nextTrack()
        event.reply("skipping").setEphemeral(false)
            .queue()
    }
}