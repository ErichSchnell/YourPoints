package com.example.yourpoints.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.presentation.model.TrucoPlayerModelUi
import com.example.yourpoints.presentation.model.TypePlayer

@Entity(tableName = "truco_table")
data class TrucoEntity (
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "pointLimit") val pointLimit:Int = 0,
    @ColumnInfo(name = "player1") val player1: TrucoPlayerModelUi = TrucoPlayerModelUi("Nosotros"),
    @ColumnInfo(name = "player2") val player2: TrucoPlayerModelUi = TrucoPlayerModelUi("Ellos"),
    @ColumnInfo(name = "winner") val winner: TypePlayer = TypePlayer.VACIO
)


fun TrucoDomain.toEntity() = TrucoEntity(
    id = id,
    pointLimit = pointLimit,
    player1 = player1,
    player2 = player2,
    winner = winner,
)