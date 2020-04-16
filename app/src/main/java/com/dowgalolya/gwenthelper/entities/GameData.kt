package com.dowgalolya.gwenthelper.entities

import com.dowgalolya.gwenthelper.enums.Winner

data class GameData(
    val firstPlayerData: PlayerData,
    val secondPlayerData: PlayerData
) {

    val winner: Winner get() = when {
        firstPlayerData.totalPoints > secondPlayerData.totalPoints -> Winner.FIRST
        firstPlayerData.totalPoints < secondPlayerData.totalPoints -> Winner.SECOND
        else -> Winner.TIE
    }
}