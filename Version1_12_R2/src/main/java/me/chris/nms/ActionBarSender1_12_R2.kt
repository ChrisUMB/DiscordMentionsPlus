package me.chris.nms

import blue.sparse.bukkitk.extensions.colored
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

class ActionBarSender1_12_R2 : ActionBarSender {
    override fun send(player: Player, message: String) {
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(message.colored))
    }
}