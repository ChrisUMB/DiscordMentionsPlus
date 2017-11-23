package me.chris.discordmentions

import me.chris.discordmentionsplus.DiscordMentionsPlus
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class DPlayer(player: Player) {

    private val file = File(DiscordMentionsPlus.plugin.dataFolder, "userdata/${player.uniqueId}.yml")
    private val conf = YamlConfiguration.loadConfiguration(file)

    var showNotifications: Boolean
        get() = conf.getBoolean("showNotifications")
        set(value) {
            conf.set("showNotifications", value)
            conf.save(file)
        }

    var hearSound: Boolean
        get() = conf.getBoolean("hearSound")
        set(value) {
            conf.set("hearSound", value)
            conf.save(file)
        }


    companion

    object {
        private val dplayers = mutableMapOf<Player, DPlayer>()

        operator fun get(player: Player) = dplayers.getOrPut(player) { DPlayer(player) }
    }
}