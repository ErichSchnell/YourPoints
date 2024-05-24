package com.example.yourpoints.presentation.model

import com.example.yourpoints.domain.model.GenericoDomain
import com.example.yourpoints.domain.model.GenericoPlayerDomain
import com.example.yourpoints.domain.model.TypePlayer


data class GenericoUi(
    val id:Int = 0,
    val dataCreated: String = "",
    val selected:Boolean = false,

    val withPoints:Boolean = false,
    val pointToInit:Int = 0,
    val pointToFinish:Int = 100,
    val finishToWin:Boolean = true,

    val withRounds:Boolean = false,
    val round:Int = 10,

    val playerMax:Int = 30,
    val player:List<GenericoPlayerUi>
) {

}

data class GenericoPlayerUi(
    val playerName:String = "",
    val playerPoint:Int = 0,
    val victories:Int = 0
) {
    fun setName(name:String) = this.copy(playerName = name)
    fun setPoint(point:Int) = this.copy(playerPoint = point)
    fun setVictories(victories:Int) = this.copy(victories = victories)
}

fun GenericoDomain.toUi() = GenericoUi(
    id = id,
    dataCreated = dataCreated,
    withPoints = withPoints,
    pointToInit = pointToInit,
    pointToFinish = pointToFinish,
    finishToWin = finishToWin,
    withRounds = withRounds,
    round = round,
    playerMax = playerMax,
    player = player.map { it.toUi() },
)

fun GenericoPlayerDomain.toUi() = GenericoPlayerUi(
    playerName = playerName,
    playerPoint = playerPoint,
    victories = victories,
)