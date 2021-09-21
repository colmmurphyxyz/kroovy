package xyz.colmmurphy.kroovy.commands.misc

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import xyz.colmmurphy.kroovy.commands.KroovyCommand

class PollCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name = "poll"

    lateinit var options: List<OptionMapping>

    override fun handle() {
        options = event.options
    }

    override fun execute() {
        val embed = EmbedBuilder()
            .setThumbnail(event.member!!.user.avatarUrl)
            .setTitle(event.getOption("question")!!.asString)
            .setDescription("Asked by ${event.member!!.effectiveName}")

        for (i in 1 until options.size) {
            val opt = options[i]
            embed.addField("", "${String(Character.toChars(0x0030 + i))} - ${opt.asString}", false)
        }
        event.channel.sendMessageEmbeds(embed.build())
            .queue {
                for (i in 1 until options.size) {
                    val num = 30 + i
                    it.addReaction("U+${num}U+fe0fU+20e3")
                        .queue()
                }
                event.reply("Created poll").setEphemeral(true)
                    .queue()
            }
    }
}