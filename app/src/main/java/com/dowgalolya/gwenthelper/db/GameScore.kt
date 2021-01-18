package com.dowgalolya.gwenthelper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.enums.Winner

@Entity(
    tableName = "game_score",
    indices = [Index("date")]
)

data class GameScore
    (
    @PrimaryKey
    val date: String,
    @ColumnInfo(name = "first_player")
    val firstPlayer: String,
    @ColumnInfo(name = "second_player")
    val secondPlayer: String,
    val winner: String,
    @ColumnInfo(name = "first_round_first_player")
    val firstRoundFirstPlayerPoints: Int?,
    @ColumnInfo(name = "second_round_first_player")
    val secondRoundFirstPlayerPoints: Int?,
    @ColumnInfo(name = "third_round_first_player")
    val thirdRoundFirstPlayerPoints: Int?,
    @ColumnInfo(name = "first_round_second_player")
    val firstRoundSecondPlayerPoints: Int?,
    @ColumnInfo(name = "second_round_second_player")
    val secondRoundSecondPlayerPoints: Int?,
    @ColumnInfo(name = "third_round_second_player")
    val thirdRoundSecondPlayerPoints: Int?
)