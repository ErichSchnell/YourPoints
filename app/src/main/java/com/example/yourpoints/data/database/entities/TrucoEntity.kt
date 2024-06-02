package com.example.yourpoints.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yourpoints.domain.model.TrucoDomain

@Entity(tableName = "truco_table")
data class TrucoEntity (
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "dataCreated") val dataCreated: String,

    @ColumnInfo(name = "pointLimit") val pointLimit: Int,

    @ColumnInfo(name = "playerName1") val playerName1: String,
    @ColumnInfo(name = "playerPoint1") val playerPoint1: Int,
    @ColumnInfo(name = "victories1") val victories1: Int,

    @ColumnInfo(name = "playerName2") val playerName2: String,
    @ColumnInfo(name = "playerPoint2") val playerPoint2: Int,
    @ColumnInfo(name = "victories2") val victories2: Int,

    @ColumnInfo(name = "winner") val winner: Int,
)


fun TrucoDomain.toEntity() = TrucoEntity(
    id = id,
    pointLimit = pointLimit,
    dataCreated = dataCreated,
    playerName1 = player1.playerName,
    playerPoint1 = player1.playerPoint,
    victories1 = player1.victories,
    playerName2 = player2.playerName,
    playerPoint2 = player2.playerPoint,
    victories2 = player2.victories,
    winner = 0,
)