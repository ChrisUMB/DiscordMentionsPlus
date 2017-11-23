package me.chris.discordmentions.listeners

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
            if (message.first() != '@') return@listen
            val mention = message.split(" ").first()
            val mentionedUserString = message.removePrefix("@").split(" ").first()
            val mentionedUser = Bukkit.getPlayer(mentionedUserString) ?: return@listen

            val dplayer = DPlayer[mentionedUser]

            if (useActionBar && dplayer.showNotifications) {
                plugin.abSender.send(mentionedUser, actionBarNotificationFormat.replace("<sent-message>", message.replace(mention, "")).replace("<sender>", player.name).colored)
            }

            if (useTitleMessage && dplayer.showNotifications) {
                plugin.titleSender.send(mentionedUser,
                        titleMessageTITLENotificationFormat.replace("<sent-message", message.replace(mention, "")).replace("<sender>", player.name).colored,
                        titleMessageSUBTITLENotificationFOrmat.replace("<sent-message>", message.replace(mention, "")).replace("<sender>", player.name).colored)
            }

            if (dplayer.hearSound)
                mentionedUser.playSound(mentionedUser.location, notificationSound, 10f, 1f)
        }

        plugin.listen<PlayerChatTabCompleteEvent> {
            if (it.chatMessage.first() != '@') return@listen
            if (!it.player.hasPermission("discordmentions.use")) return@listen
            for (player in Bukkit.getOnlinePlayers()) {
                if (player.name.toLowerCase().startsWith(it.chatMessage.drop(1).toLowerCase()))
                    it.tabCompletions.add("@${player.name}")
            }
        }
    }
}