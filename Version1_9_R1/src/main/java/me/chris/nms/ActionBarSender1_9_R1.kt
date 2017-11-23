package me.chris.nms

import blue.sparse.bukkitk.extensions.colored
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import net.minecraft.server.v1_9_R1.ChatComponentText
import net.minecraft.server.v1_9_R1.PacketPlayOutChat
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class ActionBarSender1_9_R1 : ActionBarSender {
    override fun send(player: Player, message: String) {
        val chatComponentMessage = ChatComponentText(message.colored)
        val bar = PacketPlayOutChat(chatComponentMessage, 2)
        (player as CraftPlayer)
        player.handle.playerConnection.sendPacket(bar)
    }
}