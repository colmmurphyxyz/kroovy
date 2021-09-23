package xyz.colmmurphy.kroovy.commands.ucc

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result;
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import xyz.colmmurphy.kroovy.commands.CommandHandleException
import xyz.colmmurphy.kroovy.commands.KroovyCommand

class CourseCommand : KroovyCommand() {
    override lateinit var event: SlashCommandEvent
    override val name = "course"

    private lateinit var data: String
    private lateinit var doc: org.jsoup.nodes.Document

    override fun handle() {
        try {
            doc = Jsoup.connect("https://www.ucc.ie/en/${event.getOption("course")!!.asString}").get()
        } catch (e: Exception) {
            e.printStackTrace()
            throw CommandHandleException("Something went wrong: ${e.javaClass.name}\n${e.message}")
        }
    }

    override fun execute() {
        val factFile = doc.select("ul[class*=course__facts]")
        val courseFacts = factFile.select("li")
        val embed = EmbedBuilder()
            .setColor(randomColour())
            .setTitle("${courseFacts[1].getTextElement()} - ${courseFacts[0].getTextElement()}")
            .setDescription("${courseFacts[3].getTextElement()} ${courseFacts[4].getTextElement()}")
            .addField("Entry Requirements", courseFacts[7].getTextElement().substringBefore("See"), true)
            .addField("CAO Points Range", courseFacts[9].getTextElement(), true)
            .addField("Course Outline",
                doc.select("div[class*=content-wrap__inner]").select("p")[11].select("p")[0].text(),
                false)
        event.channel.sendMessageEmbeds(embed.build())
            .queue { event.reply("\uD83D\uDC4D").setEphemeral(true).queue() }
    }

    private fun org.jsoup.nodes.Element.getTextElement(): String = this.select("p").text()

    private fun randomColour(): Int {
        return kotlin.math.floor(Math.random() * 16_777_216 /* 16^6 */).toInt()
    }
}