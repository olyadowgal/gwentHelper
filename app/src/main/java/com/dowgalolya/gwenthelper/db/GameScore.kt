package com.dowgalolya.gwenthelper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.enums.Winner

@Entity(
    tableName = "game_score",
    indices = [Index("date")])

data class GameScore
    (
    @PrimaryKey
    val date : String,
    @ColumnInfo(name = "first_player")
    val firstPlayer : String,
    @ColumnInfo(name = "second_player")
    val secondPlayer : String,
    val winner : String
)