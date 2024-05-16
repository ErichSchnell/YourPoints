package com.example.yourpoints.domain.model

import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.presentation.model.TrucoPlayerModelUi
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.TypePlayer

data class TrucoDomain (
    val id:Int = 0,
    val pointLimit:Int = 0,
    val player1: TrucoPlayerModelUi = TrucoPlayerModelUi("Nosotros"),
    val player2: TrucoPlayerModelUi = TrucoPlayerModelUi("Ellos"),
    val winner: TypePlayer = TypePlayer.VACIO
)

fun TrucoEntity.toDomain() = TrucoDomain(
    id = id,
    pointLimit = pointLimit,
    player1 = player1,
    player2 = player2,
    winner = winner,
)

fun TrucoUi.toDomain() = TrucoDomain(
    id = id,
    pointLimit = pointLimit,
    player1 = player1,
    player2 = player2,
    winner = winner,
)