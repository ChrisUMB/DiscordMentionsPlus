package me.chris.nms

import net.minecraft.server.v1_8_R1.ChatSerializer
import net.minecraft.server.v1_8_R1.EnumTitleAction
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class TitleMessageSender1_8_R1 : TitleMessageSender {
    override fun send(player: Player, title: String, subtitle: String) {
        val titlePacket = PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\" $title\"}"), 20, 40, 20)
        val subtitlePacket = PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"$subtitle\"}"), 20, 40, 20)
        (player as CraftPlayer)
        player.handle.playerConnection.sendPacket(titlePacket)
        player.handle.playerConnection.sendPacket(subtitlePacket)
    }
}