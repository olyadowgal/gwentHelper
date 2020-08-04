package com.dowgalolya.gwenthelper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.enums.Winner

@Entity(
    tableName = "game_score",
    indices = [Index("game_date")])

data class GameScore
    (
    @PrimaryKey
    @ColumnInfo(name = "game_date")
    val gameDate : String,

    @ColumnInfo(name = "first_player")
    val firstPlayer : PlayerData,

    @ColumnInfo(name = "second_player")
    val secondPlayerData : PlayerData,

    @ColumnInfo(name = "winner")
    val winner : Winner
)