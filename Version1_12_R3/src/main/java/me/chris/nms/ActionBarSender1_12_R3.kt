package me.chris.nms

import blue.sparse.bukkitk.extensions.colored
import net.minecraft.server.v1_12_R1.ChatComponentText
import net.minecraft.server.v1_12_R1.ChatMessageType
import net.minecraft.server.v1_12_R1.PacketPlayOutChat
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class ActionBarSender1_12_R3 : ActionBarSender {
    override fun send(player: Player, message: String) {
        val chatComponentMessage = ChatComponentText(message.colored)
        val bar = PacketPlayOutChat(chatComponentMessage, ChatMessageType.CHAT)
        (player as CraftPlayer)
        player.handle.playerConnection.sendPacket(bar)
    }
}