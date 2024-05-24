package com.example.yourpoints.domain.model

import com.example.yourpoints.data.database.entities.GenericoEntity
import com.example.yourpoints.presentation.model.GenericoPlayerUi
import com.example.yourpoints.presentation.model.GenericoUi


data class GenericoDomain (
    val id:Int,
    val pointLimit:Int,
    val dataCreated:String,
    val player1: GenericoPlayerDomain,
    val player2: GenericoPlayerDomain,
    val winner: TypePlayer = TypePlayer.VACIO,
)

data class GenericoPlayerDomain(
    val playerName:String = "",
    val playerPoint:Int = 0,
    val victories:Int = 0
)


fun GenericoEntity.toDomain() = GenericoDomain(
    id = id,
    pointLimit = pointLimit,
    dataCreated = dataCreated,
    player1 = GenericoPlayerDomain(
        playerName = playerName1,
        playerPoint = playerPoint1,
        victories = victories1,
    ),
    player2 = GenericoPlayerDomain(
        playerName = playerName2,
        playerPoint = playerPoint2,
        victories = victories2,
    )
)

fun GenericoUi.toDomain() = GenericoDomain(
    id = id,
    pointLimit = pointLimit,
    dataCreated = dataCreated,
    player1 = player1.toDomain(),
    player2 = player2.toDomain(),
    winner = winner
)

fun GenericoPlayerUi.toDomain() = GenericoPlayerDomain(
    playerName = playerName,
    playerPoint = playerPoint,
    victories = victories,
)
