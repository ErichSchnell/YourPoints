package com.example.yourpoints.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yourpoints.domain.model.GenericoDomain


@Entity(tableName = "generico_table")
data class GenericoEntity(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "dataCreated") val dataCreated: String = "",

    @ColumnInfo(name = "pointLimit") val pointLimit: Int = 0,

    @ColumnInfo(name = "playerName1") val playerName1: String = "",
    @ColumnInfo(name = "playerPoint1") val playerPoint1: Int = 0,
    @ColumnInfo(name = "victories1") val victories1: Int = 0,

    @ColumnInfo(name = "playerName2") val playerName2: String = "",
    @ColumnInfo(name = "playerPoint2") val playerPoint2: Int = 0,
    @ColumnInfo(name = "victories2") val victories2: Int = 0,

    @ColumnInfo(name = "winner") val winner: Int = 0,
)


fun GenericoDomain.toEntity() = GenericoEntity(
    id = id,
    dataCreated = dataCreated,
    pointLimit = pointLimit,
    playerName1 = player1.playerName,
    playerPoint1 = player1.playerPoint,
    victories1 = player1.victories,
    playerName2 = player2.playerName,
    playerPoint2 = player2.playerPoint,
    victories2 = player2.victories,
    winner = 0,
)

