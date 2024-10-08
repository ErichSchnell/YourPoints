package com.example.yourpoints.presentation.model

import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.TrucoPlayerDomain
import com.example.yourpoints.domain.model.TypePlayer

data class TrucoUi(
    val id:Int = 0,
    val selected:Boolean = false,
    val dataCreated: String = "",
    val pointLimit:Int = 0,
    val player1: TrucoPlayerUi = TrucoPlayerUi("Team 1"),
    val player2: TrucoPlayerUi = TrucoPlayerUi("Team 2"),
    val winner: TypePlayer = TypePlayer.VACIO
) {
    fun changeSelect() = this.copy(
        selected = !selected
    )
    fun resetPoint(point:Int) = this.copy(
        pointLimit = point,
        player1 = this.player1.setPoint(0),
        player2 = this.player2.setPoint(0),
        winner = TypePlayer.VACIO
    )
    fun clearWinner() = this.copy(
        winner = TypePlayer.VACIO
    )
    fun setNamePlayer1(name:String) = this.copy(player1 = this.player1.setName(name))
    fun increasePlayer1() = this.copy(player1 = this.player1.setPoint(this.player1.playerPoint.inc()))
    fun decreasePlayer1() = this.copy(player1 = this.player1.setPoint(this.player1.playerPoint.dec()))
    fun setNamePlayer2(name:String) = this.copy(player2 = this.player2.setName(name))
    fun increasePlayer2() = this.copy(player2 = this.player2.setPoint(this.player2.playerPoint.inc()))
    fun decreasePlayer2() = this.copy(player2 = this.player2.setPoint(this.player2.playerPoint.dec()))

}

data class TrucoPlayerUi(
    val playerName:String = "",
    val playerPoint:Int = 0,
    val victories:Int = 0
) {
    fun setName(name:String) = this.copy(playerName = name)
    fun setPoint(point:Int) = this.copy(playerPoint = point)
}

fun TrucoDomain.toUi() = TrucoUi(
    id = id,
    pointLimit = pointLimit,
    dataCreated = dataCreated,
    player1 = player1.toUi(),
    player2 = player2.toUi(),
    winner = winner,
)

fun TrucoPlayerDomain.toUi() = TrucoPlayerUi(
    playerName = playerName,
    playerPoint = playerPoint,
    victories = victories,
)