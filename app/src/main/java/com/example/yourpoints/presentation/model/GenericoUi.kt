package com.example.yourpoints.presentation.model

import com.example.yourpoints.domain.model.GenericoDomain
import com.example.yourpoints.domain.model.GenericoPlayerDomain


data class GenericoUi(
    val id:Int = 0,
    val dataCreated: String = "",
    val selected:Boolean = false,

    val name: String = "",

    val pointToInit:Int = 0,
    val pointToFinish:Int? = null,
    val finishToWin:Boolean? = null,
    val finished:Boolean = false,

    val roundMax:Int? = null,
    val roundPlayed:Int = 1,

    val player:List<GenericoPlayerUi> = emptyList()
) {
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
        player = this.player.map { it.copy(playerPoint = 0, addVictoryFlag = true) }.toMutableList()
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
    pointToInit = pointToInit,
    pointToFinish = pointToFinish,
    finishToWin = finishToWin,
    roundMax = round,
    roundPlayed = roundPlayed,
    player = player.map { it.toUi() }.toMutableList(),
)

fun GenericoPlayerDomain.toUi() = GenericoPlayerUi(
    playerName = playerName,
    playerPoint = playerPoint,
    victories = victories,
)