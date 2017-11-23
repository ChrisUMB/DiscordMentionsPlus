package me.chris.nms

import blue.sparse.bukkitk.extensions.colored
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

class ActionBarSender1_11_R1 : ActionBarSender {
    override fun send(player: Player, message: String) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(message.colored))
    }
}