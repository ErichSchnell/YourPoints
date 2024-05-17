package com.example.yourpoints.presentation.ui.truco

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourpoints.domain.AddTrucoGameUseCase
import com.example.yourpoints.domain.GetTrucoGameUseCase
import com.example.yourpoints.domain.UpdateTrucoGameUseCase
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.TypePlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

const val TAG = "TrucoViewModel Intern Test"

@HiltViewModel
class TrucoViewModel @Inject constructor(
    private val setGame: AddTrucoGameUseCase,
    private val getGame: GetTrucoGameUseCase,
    private val updateGame: UpdateTrucoGameUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<TrucoViewState>(TrucoViewState.LOADING)
    val uiState: StateFlow<TrucoViewState> = _uiState

    private val _game = MutableStateFlow<TrucoUi>(TrucoUi())
    val game: StateFlow<TrucoUi> = _game

    private val _id = MutableStateFlow(0)


    fun initAnnotator(gameId: Int) {
        if (gameId == 0){
            _id.value = getDate().hashCode()
            addNewGame()
        } else {
            _id.value = gameId
            loadGame()
        }
    }

    private fun addNewGame(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    setGame(TrucoUi(id = _id.value, pointLimit = 30).toDomain())
                    loadGame()
                    Log.i(TAG, "Partida Inicializada. ID:${_id.value}")
                } catch (e:Exception){
                    Log.i(TAG, "Partida No Inicializada. ID:${_id.value}")
                }
            }
        }
    }
    private fun loadGame(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _game.value = getGame(_id.value)
                    Log.i(TAG, "Partida Encontrada. ID:${_id.value}")
                    _uiState.value = TrucoViewState.SUCCESS

                } catch (e:Exception){
                    Log.i(TAG, "Partida No Encontrada. ID:${_id.value}")
                    Log.i(TAG, "Partida No Encontrada. ID:${_id.value}")
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
    object SUCCESS: TrucoViewState()
}