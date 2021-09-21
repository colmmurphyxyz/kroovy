package xyz.colmmurphy.kroovy.commands.misc

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import xyz.colmmurphy.kroovy.commands.CommandHandleException
import xyz.colmmurphy.kroovy.commands.KroovyCommand

class PollCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name = "poll"

    private val charList = listOf(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    )

    private lateinit var options: List<OptionMapping>
    private lateinit var answers: List<String>

    override fun handle() {
        options = event.options
        answers = (event.getOption("answers")?.asString?.split(",")
            ?: throw CommandHandleException("You didn't provide enough options for this command"))
        if (answers.size > 9) {
            throw CommandHandleException("You cannot provide more than 9 answers for this poll")
        }
    }

    override fun execute() {
        val embed = EmbedBuilder()
            .setThumbnail(event.member!!.user.avatarUrl)
            .setTitle(event.getOption("question")!!.asString)
            .setDescription("Asked by ${event.member!!.effectiveName}")
            .setColor(randomColour())


        for (i in answers.indices) {
            val ans = answers[i]
            embed.addField("", "${String(Character.toChars(0x0031 + i))} - $ans", false)
        }
        event.channel.sendMessageEmbeds(embed.build())
            .queue {
                for (i in 1..answers.size) {
                    val num = 30 + i
                    it.addReaction("U+${num}U+fe0fU+20e3")
                        .queue()
                }
                event.reply("Created poll").setEphemeral(true)
                    .queue()
            }
    }

    private fun randomColour(): Int {
        return kotlin.math.floor(Math.random() * 16_777_216 /* 16^6 */).toInt()
    }
}