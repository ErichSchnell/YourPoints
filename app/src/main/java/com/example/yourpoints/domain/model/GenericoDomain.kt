package com.example.yourpoints.domain.model

import com.example.yourpoints.data.database.entities.GenericoEntity
import com.example.yourpoints.presentation.model.GenericoPlayerUi
import com.example.yourpoints.presentation.model.GenericoUi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class GenericoDomain (
    val id:Int,
    val dataCreated: String,

    val withPoints:Boolean,
    val pointToInit:Int,
    val pointToFinish:Int,
    val finishToWin:Boolean,

    val withRounds:Boolean,
    val round:Int,

    val playerMax:Int,
    val player:MutableList<GenericoPlayerDomain> = mutableListOf()
)

data class GenericoPlayerDomain(
    val playerName:String = "",
    val playerPoint:Int = 0,
    val victories:Int = 0
)


fun GenericoEntity.toDomain() :GenericoDomain{
    val listType = object : TypeToken<List<GenericoPlayerDomain>>() {}.type
    return GenericoDomain(
        id = id,
        dataCreated = dataCreated,
        withPoints = withPoints,
        pointToInit = pointToInit,
        pointToFinish = pointToFinish,
        finishToWin = finishToWin,
        withRounds = withRounds,
        round = round,
        playerMax = playerMax,
        player = Gson().fromJson(player, listType),
    )
}

fun GenericoUi.toDomain() = GenericoDomain(
    id = id,
    dataCreated = dataCreated,
    withPoints = withPoints,
    pointToInit = pointToInit,
    pointToFinish = pointToFinish,
    finishToWin = finishToWin,
    withRounds = withRounds,
    round = round,
    playerMax = playerMax,
    player = player.map { it.toDomain() }.toMutableList(),
)

fun GenericoPlayerUi.toDomain() = GenericoPlayerDomain(
    playerName = playerName,
    playerPoint = playerPoint,
    victories = victories,
)
