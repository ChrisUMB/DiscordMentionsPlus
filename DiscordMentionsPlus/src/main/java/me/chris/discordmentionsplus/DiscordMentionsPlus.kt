package me.chris.discordmentionsplus

import blue.sparse.bukkitk.commands.command
import blue.sparse.bukkitk.events.listen
import blue.sparse.bukkitk.extensions.colored
import me.chris.discordmentions.listeners.ChatMentionHandler
import me.chris.discordmentionsplus.helpers.NotificationOptions
import me.chris.nms.*
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class DiscordMentionsPlus : JavaPlugin() {

    val abSender: ActionBarSender = getActionBarSender()
    val titleSender: TitleMessageSender = getTitleMessageSender()

    override fun onEnable() {
        handleConfig()
        handleJoin()
        handleOnlines()
        handleCommand()
        ChatMentionHandler(this)
    }

    override fun onDisable() {

    }

    private fun getActionBarSender(): ActionBarSender {
        val version = Bukkit.getServer().javaClass.`package`.name
        val switchableVersion = version.substring(version.lastIndexOf('.') + 1)

        val versionedActionBarSender: ActionBarSender =
                when (switchableVersion) {
                    "v1_8_R1" -> ActionBarSender1_8_R1()
                    "v1_8_R2" -> ActionBarSender1_8_R2()
                    "v1_8_R3" -> ActionBarSender1_8_R3()
                    "v1_9_R1", "V1_9_R2", "V1_9_R3" -> ActionBarSender1_9_R1()
                    "v1_10_R1" -> ActionBarSender1_10_R1()
                    "v1_11_R1" -> ActionBarSender1_11_R1()
                    "v1_12_R1" -> ActionBarSender1_12_R1()
                    else -> {
                        ActionBarSender1_12_R1()
                    }
                }

        println("!!! DiscordMentionsPlus hooked into TitleMessageSender version ${versionedActionBarSender.javaClass.simpleName}")
        return versionedActionBarSender
    }

    private fun getTitleMessageSender(): TitleMessageSender {
        val version = Bukkit.getServer().javaClass.`package`.name
        val switchableVersion = version.substring(version.lastIndexOf('.') + 1)

        val versionedTitleMessageSender: TitleMessageSender =
                when (switchableVersion) {
                    "v1_8_R1" -> TitleMessageSender1_8_R1()
                    "v1_8_R2" -> TitleMessageSender1_8_R2()
                    "v1_8_R3" -> TitleMessageSender1_8_R3()
                    "v1_9_R1", "V1_9_R2", "V1_9_R3" -> TitleMessageSender1_9_R1()
                    "v1_10_R1" -> TitleMessageSender1_10_R1()
                    "v1_11_R1" -> TitleMessageSender1_11_R1()
                    "v1_11_R2" -> TitleMessageSender1_11_R2()
                    "v1_12_R1" -> TitleMessageSender1_12_R1()
                    "v1_12_R2" -> TitleMessageSender1_12_R2()
                    "v1_12_R3" -> TitleMessageSender1_12_R3()
                    else -> {
                        TitleMessageSender1_12_R3()
                    }
                }

        println("!!! DiscordMentionsPlus hooked into TitleMessageSender version ${versionedTitleMessageSender.javaClass.simpleName}")
        return versionedTitleMessageSender
    }

    private fun handleOnlines() {
        Bukkit.getOnlinePlayers().forEach {
            val userdataFile = File(dataFolder, "userdata/${it.player.uniqueId}.yml")
            val userdataConfig = YamlConfiguration.loadConfiguration(userdataFile)
            userdataConfig.apply {
                if (!isSet("showNotifications"))
                    set("showNotifications", true)
                if (!isSet("hearSound"))
                    set("hearSound", true)
                save(userdataFile)
            }
        }
    }

    private fun handleConfig() {
        config.apply {
            addDefault("notification-type.action-bar", true)
            addDefault("notification-type.title-message", true)
            addDefault("notification-sound", "ANVIL_BREAK")
            addDefault("notification-format.action-bar", "&c&l<sender> &chas mentioned you.")
            addDefault("notification-format.title-message.title", "&c&l<sender> &chas mentioned you.")
            addDefault("notification-format.title-message.subtitle", "<sent-message>")
            options().copyDefaults(true)
        }
        saveConfig()
    }

    private fun handleJoin() {
        listen<PlayerJoinEvent> {

            val userdataFile = File(dataFolder, "userdata/${it.player.uniqueId}.yml")
            val userdataConfig = YamlConfiguration.loadConfiguration(userdataFile)
            userdataConfig.apply {
                if (!isSet("showNotifications"))
                    set("showNotifications", true)
                if (!isSet("hearSound"))
                    set("hearSound", true)
                save(userdataFile)
            }
        }
    }

    private fun handleCommand() {
        command("mentions", "Display the mentions options.", "/mentions", "discordmentions.use", "&cYou don't have permission to do this.".colored) {
            val player = sender as? Player ?: return@command
            NotificationOptions.displayOptionsGUI(player)
        }
    }

    companion object {
        val plugin by lazy { JavaPlugin.getPlugin(DiscordMentionsPlus::class.java) }
    }
}
