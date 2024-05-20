package com.example.yourpoints.domain.model

import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.presentation.model.TrucoPlayerUi
import com.example.yourpoints.presentation.model.TrucoUi

data class TrucoDomain (
    val id:Int,
    val pointLimit:Int,
    val dataCreated:String,
    val player1: TrucoPlayerDomain,
    val player2: TrucoPlayerDomain,
    val winner: TypePlayer = TypePlayer.VACIO,
)

data class TrucoPlayerDomain(
    val playerName:String = "",
    val playerPoint:Int = 0,
    val victories:Int = 0
)


fun TrucoEntity.toDomain() = TrucoDomain(
    id = id,
    pointLimit = pointLimit,
    dataCreated = dataCreated,
    player1 = TrucoPlayerDomain(
        playerName = playerName1,
        playerPoint = playerPoint1,
        victories = victories1,
    ),
    player2 = TrucoPlayerDomain(
        playerName = playerName2,
        playerPoint = playerPoint2,
        victories = victories2,
    )
)

fun TrucoUi.toDomain() = TrucoDomain(
    id = id,
    pointLimit = pointLimit,
    dataCreated = dataCreated,
    player1 = player1.toDomain(),
    player2 = player2.toDomain(),
    winner = winner
)

fun TrucoPlayerUi.toDomain() = TrucoPlayerDomain(
    playerName = playerName,
    playerPoint = playerPoint,
    victories = victories,
)


