package me.chris.nms

import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

class TitleMessageSender1_8_R3 : TitleMessageSender {
    override fun send(player: Player, title: String, subtitle: String) {
        val titlePacket = PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\" $title\"}"), 20, 40, 20)
        val subtitlePacket = PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"$subtitle\"}"), 20, 40, 20)
        (player as CraftPlayer)
        player.handle.playerConnection.sendPacket(titlePacket)
        player.handle.playerConnection.sendPacket(subtitlePacket)
    }
}