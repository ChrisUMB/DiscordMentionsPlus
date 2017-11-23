package me.chris.nms

import org.bukkit.entity.Player

interface ActionBarSender {
    fun send(player: Player, message: String)
}