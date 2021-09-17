package xyz.colmmurphy.kroovy.commands.music

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import xyz.colmmurphy.kroovy.commands.KroovyCommand
import xyz.colmmurphy.kroovy.lavaplayer.PlayerManager

class QueueCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name = "queue"

    override fun handle() {

    }

    override fun execute() {
        val guildMusicManager = PlayerManager.getMusicManager(event.guild!!)

        val queue = guildMusicManager.scheduler.getQueue()
        if (queue.isEmpty()) {
            event.reply("The queue is currently empty")
                .queue()
            return
        }

        val nowPlaying = guildMusicManager.audioPlayer.playingTrack
        var qString = "**1**: ${nowPlaying.info.author} - ${nowPlaying.info.title}\n"
        var n = 2
        queue.forEach {
            qString += "**$n**: ${it.info.author} - ${it.info.title}\n"
            n++
        }

        event.reply(qString)
            .queue()
    }
}