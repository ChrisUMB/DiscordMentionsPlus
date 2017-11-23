package me.chris.nms

import org.bukkit.entity.Player

interface TitleMessageSender {
    fun send(player: Player, title: String, subtitle: String)
}