package me.chris.discordmentionsplus.listeners

import blue.sparse.bukkitk.events.listen
import blue.sparse.bukkitk.extensions.colored
import me.chris.discordmentions.DPlayer
import me.chris.discordmentionsplus.DiscordMentionsPlus
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerChatTabCompleteEvent

class ChatMentionHandler(plugin: DiscordMentionsPlus) {

    val useActionBar = plugin.config.getBoolean("notification-type.action-bar")
    val useTitleMessage = plugin.config.getBoolean("notification-type.action-bar")
    val notificationSound = Sound.valueOf(plugin.config.getString("notification-sound"))
    val actionBarNotificationFormat = plugin.config.getString("notification-format.action-bar") //replace <sender> and <sent-message>
    val titleMessageTITLENotificationFormat = plugin.config.getString("notification-format.title-message.title") //replace <sender> and <sent-message>
    val titleMessageSUBTITLENotificationFOrmat = plugin.config.getString("notification-format.title-message.subtitle") //replace <sender> and <sent-message>

    init {

        plugin.listen<AsyncPlayerChatEvent> {
            val player = it.player
            if (!player.hasPermission("discordmentions.use")) return@listen

            val message = it.message

            val mentions = message.split(' ').filter { it.startsWith("@") }.mapNotNull { Bukkit.getPlayer(it.drop(1)) }

            if (mentions.isEmpty()) return@listen

            if (useActionBar) {
                mentions.filter { DPlayer[it].showNotifications }.forEach {
                    plugin.abSender.send(it, actionBarNotificationFormat.replace("<sent-message>", message.replace("@${it.name}", "")).replace("<sender>", player.name).colored)
                }
            }

            if (useTitleMessage) {
                mentions.filter { DPlayer[it].showNotifications }.forEach {
                    plugin.titleSender.send(it,
                            titleMessageTITLENotificationFormat.replace("<sent-message", message.replace("@${it.name}", "")).replace("<sender>", player.name).colored,
                            titleMessageSUBTITLENotificationFOrmat.replace("<sent-message>", message.replace("@${it.name}", "")).replace("<sender>", player.name).colored)
                }
            }

            mentions.filter { DPlayer[it].hearSound }.forEach {
                it.playSound(it.location, notificationSound, 10f, 1f)
            }
        }

        plugin.listen<PlayerChatTabCompleteEvent> {
            if (!it.player.hasPermission("discordmentions.use")) return@listen
            val message = it.chatMessage

            val split = message.split(" ")

            if (!split.last().startsWith("@")) return@listen

            Bukkit.getOnlinePlayers()
                    .filter { it.name.toLowerCase().startsWith(split.last().drop(1).toLowerCase()) }
                    .forEach { player -> it.tabCompletions.add("@${player.name}") }
        }
    }
}