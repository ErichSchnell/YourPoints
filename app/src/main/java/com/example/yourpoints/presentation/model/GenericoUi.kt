package com.example.yourpoints.presentation.model

import com.example.yourpoints.domain.model.GenericoDomain
import com.example.yourpoints.domain.model.GenericoPlayerDomain


data class GenericoUi(
    val id:Int = 0,
    val isSetPoint:Boolean = false,
    val dataCreated: String = "",
    val selected:Boolean = false,

    val name: String = "",

    val withPoints:Boolean = false,
    val pointToInit:Int = 0,
    val pointToFinish:Int = 100,
    val finishToWin:Boolean = true,
    val finished:Boolean = false,

    val withRounds:Boolean = false,
    val roundMax:Int = 10,
    val roundPlayed:Int = 1,

    val player:List<GenericoPlayerUi> = emptyList()
) {
    fun setPoint(value:Boolean) = this.copy(
        isSetPoint = value
    )
    fun changeSelect() = this.copy(
        selected = !selected
    )
    fun setFinish(value:Boolean) = this.copy(
        finished = value
    )
    fun setPlayers(players:List<GenericoPlayerUi>) = this.copy(
        player = players
    )

    fun resetGame() = this.copy(
        finished = false,
        roundPlayed = 1,
        player = this.player.map { it.copy(playerPoint = this.pointToInit, addVictoryFlag = true) }
    )

    fun incRound() = this.copy(
        roundPlayed = roundPlayed.inc()
    )

}

data class GenericoPlayerUi(
    val playerName:String = "",
    val playerPoint:Int = 0,
    val victories:Int = 0,
    val addVictoryFlag:Boolean = true,
) {
    fun setName(name:String) = this.copy(playerName = name)
    fun setPoint(point:Int) = this.copy(playerPoint = point)
    fun setVictories(victories:Int) = this.copy(victories = victories)
    fun addVictories() = this.copy(victories = if(addVictoryFlag) victories.inc() else victories)
    fun lessVictories() = this.copy(victories = victories.dec())
    fun setAddVictoryFlag(value:Boolean) = this.copy(addVictoryFlag = value)
}

fun GenericoDomain.toUi() = GenericoUi(
    id = id,
    dataCreated = dataCreated,
    name = name,
    withPoints = withPoints,
    pointToInit = pointToInit,
    pointToFinish = pointToFinish,
    finishToWin = finishToWin,
    withRounds = withRounds,
    roundMax = round,
    roundPlayed = roundPlayed,
    player = player.map { it.toUi() },
)

fun GenericoPlayerDomain.toUi() = GenericoPlayerUi(
    playerName = playerName,
    playerPoint = playerPoint,
    victories = victories,
)