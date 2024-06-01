package com.example.yourpoints.presentation.ui.truco

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourpoints.domain.annotatorTruco.AddTrucoGameUseCase
import com.example.yourpoints.domain.annotatorTruco.GetTrucoGameUseCase
import com.example.yourpoints.domain.annotatorTruco.UpdateTrucoGameUseCase
import com.example.yourpoints.domain.model.TypePlayer
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.TrucoUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

private const val TAG = "TrucoViewModel Intern Test"

@HiltViewModel
class TrucoViewModel @Inject constructor(
    private val setGame: AddTrucoGameUseCase,
    private val getGame: GetTrucoGameUseCase,
    private val updateGame: UpdateTrucoGameUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<TrucoViewState>(TrucoViewState.LOADING)
    val uiState: StateFlow<TrucoViewState> = _uiState

    private val _game = MutableStateFlow(TrucoUi())
    val game: StateFlow<TrucoUi> = _game

    private val _id = MutableStateFlow(0)

    fun initAnnotator(gameId: Int) {
        if (gameId == 0){
            _uiState.value = TrucoViewState.CREATE
        } else {
            loadGame(gameId)
        }
    }

    fun addNewGame(pointMax: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val date = getDate()

                    _game.value = TrucoUi(id = date.hashCode(), dataCreated = date, pointLimit = pointMax)
                    setGame(_game.value.toDomain())

                    _uiState.value = TrucoViewState.SUCCESS
                    Log.i(TAG, "Partida Inicializada. ID:${_id.value}")
                } catch (e:Exception){
                    Log.i(TAG, "Partida No Inicializada. ID:${_id.value}")
                    Log.i(TAG, "Error mensaje: ${e.message}")
                }
            }
        }
    }
    private fun loadGame(gameId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _game.value = getGame(gameId)
                    Log.i(TAG, "Partida Encontrada. ID:$gameId")
                    _uiState.value = TrucoViewState.SUCCESS

                } catch (e:Exception){
                    Log.i(TAG, "Partida No Encontrada. ID:$gameId")
                    Log.i(TAG, "Error mensaje: ${e.message}")
                }
            }
        }
    }
    private fun updateInfoGame(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    updateGame(_game.value.toDomain())
                } catch (e:Exception){
                    Log.i(TAG, "updateInfoGame: NO SE PUDO ACTUALIZAR")
                    Log.i(TAG, "Error mensaje: ${e.message}")
                }
            }
        }
    }





    fun setAnnotator(pointMax:Int){
        _game.value = _game.value.resetPoint(pointMax)
        updateInfoGame()
    }
    fun clearWinner(){
        _game.value = _game.value.clearWinner()
        updateInfoGame()
    }

    fun changeNamePlayer1(name: String) {
        _game.value = _game.value.setNamePlayer1(name)
        updateInfoGame()
        Log.i(TAG, "player1: ${_game.value.player1.playerName}")
    }
    fun increasePlayer1() {
        if(_game.value.player1.playerPoint == _game.value.pointLimit){
            _game.value = _game.value.copy(winner = TypePlayer.PLAYER1)
            updateInfoGame()

            Log.i(TAG, "player1 winner ?: ${_game.value.winner}")
        }
        if(_game.value.player1.playerPoint < _game.value.pointLimit){
            _game.value = _game.value.increasePlayer1()
            updateInfoGame()
        }

        Log.i(TAG, "player1: ${_game.value.player1.playerPoint}")
    }
    fun decreasePlayer1(){
        if(_game.value.player1.playerPoint > 0){
            _game.value = _game.value.decreasePlayer1()
            updateInfoGame()
        }
        Log.i(TAG, "player1: ${_game.value.player1.playerPoint}")
    }

    fun changeNamePlayer2(name: String) {
        _game.value = _game.value.setNamePlayer2(name)
        updateInfoGame()
        Log.i(TAG, "player2: ${_game.value.player2.playerName}")
    }
    fun increasePlayer2(){
        if(_game.value.player2.playerPoint == _game.value.pointLimit){
            _game.value = _game.value.copy(winner = TypePlayer.PLAYER2)
            updateInfoGame()
            Log.i(TAG, "player2 winner ?: ${_game.value.winner}")
        }
        if(_game.value.player2.playerPoint < _game.value.pointLimit){
            _game.value = _game.value.increasePlayer2()
            updateInfoGame()
        }
        Log.i(TAG, "player2: ${_game.value.player2.playerPoint}")
    }
    fun decreasePlayer2(){
        if(_game.value.player2.playerPoint > 0){
            _game.value = _game.value.decreasePlayer2()
            updateInfoGame()
        }
        Log.i(TAG, "player2: ${_game.value.player2.playerPoint}")
    }

    private fun getDate(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(Calendar.getInstance().time)
    }
}

sealed class TrucoViewState(){
    object LOADING: TrucoViewState()
    object CREATE: TrucoViewState()
    object SUCCESS: TrucoViewState()
}