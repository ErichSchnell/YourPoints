package com.example.yourpoints.domain.model

import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.presentation.model.TrucoUi

data class TrucoDomain (
    val id:Int = 0,
    val pointLimit:Int = 0,
)

fun TrucoEntity.toDomain() = TrucoDomain(
    id = id,
    pointLimit = pointLimit,
)

fun TrucoUi.toDomain() = TrucoDomain(
    id = id,
    pointLimit = pointLimit,
)