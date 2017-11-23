package me.chris.discordmentionsplus.helpers

import blue.sparse.bukkitk.extensions.colored
import blue.sparse.bukkitk.extensions.inventory.item
import blue.sparse.bukkitk.extensions.inventory.meta
import blue.sparse.bukkitk.extensions.inventory.openInventory
import me.chris.discordmentions.DPlayer
import me.chris.discordmentionsplus.DiscordMentionsPlus
import org.bukkit.Material
import org.bukkit.entity.Player

object NotificationOptions {

    fun displayOptionsGUI(player: Player) {
        val dplayer = DPlayer[player]
        player.openInventory(DiscordMentionsPlus.plugin, "Options", 1) {
            val showNotificationsItem = item(Material.WOOL, 1) {
                meta {
                    displayName = if (dplayer.showNotifications) "&aShow Notifications | Enabled".colored else "&cShow Notifications | Disabled".colored
                    lore = listOf("&fToggle notifications from mentions in chat.".colored)
                    durability = if (dplayer.showNotifications) 5 else 14
                }
            }

            val hearSoundItem = item(Material.WOOL, 1) {
                meta {
                    displayName = if (dplayer.hearSound) "&aHear Sound | Enabled".colored else "&cShow Notifications | Disabled".colored
                    lore = listOf("&fToggle hearing sound from mentions.".colored)
                    durability = if (dplayer.hearSound) 5 else 14
                }
            }

            setItem(1, showNotificationsItem)
            setItem(7, hearSoundItem)

            onClick(1) {
                isCancelled = true
                dplayer.showNotifications = !dplayer.showNotifications
                displayOptionsGUI(player)
            }

            onClick(7) {
                isCancelled = true
                dplayer.hearSound = !dplayer.hearSound
                displayOptionsGUI(player)
            }
        }
    }
}