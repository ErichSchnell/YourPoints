package com.example.yourpoints.domain.model

sealed class TypePlayer(){
    object VACIO:TypePlayer()
    object PLAYER1:TypePlayer()
    object PLAYER2:TypePlayer()
}