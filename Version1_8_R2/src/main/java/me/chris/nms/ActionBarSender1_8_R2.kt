package me.chris.nms

import blue.sparse.bukkitk.extensions.colored
import net.minecraft.server.v1_8_R2.ChatComponentText
import net.minecraft.server.v1_8_R2.PacketPlayOutChat
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer
import org.bukkit.entity.Player

class ActionBarSender1_8_R2 : ActionBarSender {
    override fun send(player: Player, message: String) {
        val chatComponentMessage = ChatComponentText(message.colored)
        val bar = PacketPlayOutChat(chatComponentMessage, 2)
        (player as CraftPlayer)
        player.handle.playerConnection.sendPacket(bar)
    }

}