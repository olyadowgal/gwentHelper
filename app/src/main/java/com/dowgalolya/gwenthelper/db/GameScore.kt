package com.dowgalolya.gwenthelper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dowgalolya.gwenthelper.enums.Winner

@Entity(
    tableName = "game_score",
    indices = [Index("game_date")])

data class GameScore
    (
    @PrimaryKey
    val date : String,
    @ColumnInfo(name = "first_player_name")
    val firstPlayerName : String,
    @ColumnInfo(name = "second_player_name")
    val secondPlayerName : String,
    val winner : Winner
)