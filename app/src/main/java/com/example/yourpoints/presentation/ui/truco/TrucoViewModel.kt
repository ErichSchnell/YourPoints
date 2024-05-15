package com.example.yourpoints.presentation.ui.truco

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yourpoints.presentation.model.TrucoModelUI
import com.example.yourpoints.presentation.model.TypePlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val TAG = "TrucoViewModel Intern Test"

@HiltViewModel
class TrucoViewModel @Inject constructor(

): ViewModel() {

    private val _uiState = MutableStateFlow<TrucoViewState>(TrucoViewState.LOADING)
    val uiState: StateFlow<TrucoViewState> = _uiState

    private val _game = MutableStateFlow<TrucoModelUI>(TrucoModelUI())
    val game: StateFlow<TrucoModelUI> = _game

    fun initAnnotator(annotatorTime: String) {
        _game.value = TrucoModelUI(pointLimit = 30)
        _uiState.value = TrucoViewState.SUCCESS

        Log.i(TAG, "initAnnotator: ${_game.value}")
    }



    fun setAnnotator(pointMax:Int){
        _game.value = _game.value.resetPoint(pointMax)
    }
    fun clearWinner(){
        _game.value = _game.value.clearWinner()
    }

    fun changeNamePlayer1(name: String) {
        _game.value = _game.value.setNamePlayer1(name)
        Log.i(TAG, "player1: ${_game.value.player1.playerName}")
    }
    fun increasePlayer1() {
        if(_game.value.player1.playerPoint == _game.value.pointLimit){
            _game.value = _game.value.copy(winner = TypePlayer.PLAYER1)

            Log.i(TAG, "player1 winner ?: ${_game.value.winner}")
        }
        if(_game.value.player1.playerPoint < _game.value.pointLimit){
            _game.value = _game.value.increasePlayer1()
        }

        Log.i(TAG, "player1: ${_game.value.player1.playerPoint}")
    }
    fun decreasePlayer1(){
        if(_game.value.player1.playerPoint > 0){
            _game.value = _game.value.decreasePlayer1()
        }
        Log.i(TAG, "player1: ${_game.value.player1.playerPoint}")
    }

    fun changeNamePlayer2(name: String) {
        _game.value = _game.value.setNamePlayer2(name)
        Log.i(TAG, "player2: ${_game.value.player2.playerName}")
    }
    fun increasePlayer2(){
        if(_game.value.player2.playerPoint == _game.value.pointLimit){
            _game.value = _game.value.copy(winner = TypePlayer.PLAYER2)
            Log.i(TAG, "player2 winner ?: ${_game.value.winner}")
        }
        if(_game.value.player2.playerPoint < _game.value.pointLimit){
            _game.value = _game.value.increasePlayer2()
        }
        Log.i(TAG, "player2: ${_game.value.player2.playerPoint}")
    }
    fun decreasePlayer2(){
        if(_game.value.player2.playerPoint > 0){
            _game.value = _game.value.decreasePlayer2()
        }
        Log.i(TAG, "player2: ${_game.value.player2.playerPoint}")
    }
}

sealed class TrucoViewState(){
    object LOADING: TrucoViewState()
    object SUCCESS: TrucoViewState()
}