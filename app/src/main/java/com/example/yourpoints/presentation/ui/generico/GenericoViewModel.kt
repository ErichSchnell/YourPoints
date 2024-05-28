package com.example.yourpoints.presentation.ui.generico

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourpoints.domain.annotatorGenerico.AddGenericoGameUseCase
import com.example.yourpoints.domain.annotatorGenerico.GetGenericoGameUseCase
import com.example.yourpoints.domain.annotatorGenerico.UpdateGenericoGameUseCase
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.GenericoPlayerUi
import com.example.yourpoints.presentation.model.GenericoUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

private val TAG = "GenericoViewModel intern Test"

@HiltViewModel
class GenericoViewModel @Inject constructor(
    private val getGame: GetGenericoGameUseCase,
    private val addGame: AddGenericoGameUseCase,
    private val updateGame: UpdateGenericoGameUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<GenericoUiState>(GenericoUiState.LOADING)
    val uiState:StateFlow<GenericoUiState> = _uiState

    private val _game = MutableStateFlow<GenericoUi>(GenericoUi())
    val game:StateFlow<GenericoUi> = _game

    fun initGenerico(gameId: Int) {
        if (gameId == 0){
            _uiState.value = GenericoUiState.CREATE
        } else {
            _uiState.value = GenericoUiState.VIEW_POINTS
        }
    }

    private fun getDate(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(Calendar.getInstance().time)
    }

    fun createGame(
        pointFlag: Boolean,
        pointInit: Int,
        pointFinish: Int,
        finishToWin: Boolean,
        roundFlag: Boolean,
        rounds: Int,
        cantPlayers: Int,
    ) {

        val players: MutableList<GenericoPlayerUi> = mutableListOf()
        for(i in 0 until cantPlayers){
            players.add(GenericoPlayerUi(playerName = "Player ${i + 1}"))
        }

        _game.value = GenericoUi(
            id = getDate().hashCode(),
            dataCreated = getDate(),
            withPoints = pointFlag,
            pointToInit = pointInit,
            pointToFinish = pointFinish,
            finishToWin = finishToWin,
            withRounds = roundFlag,
            round = rounds,
            playerMax = cantPlayers,
            player = players
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    addGame(_game.value.toDomain())

                    Log.i(TAG, "addGame: creado")
                    Log.i(TAG, "_game.value: ${_game.value}")

                    _uiState.value = GenericoUiState.SELECT_NAME
                } catch (e:Exception){
                    Log.i(TAG, "addGame room: No se agrego game")
                    Log.i(TAG, "addGame error: ${e.message}")

                }
            }
        }
    }

    fun updateNames(names: List<String>) {
        var index = 0
        val playerAux: MutableList<GenericoPlayerUi> = mutableListOf()
        names.forEach{
            playerAux.add(_game.value.player[index].copy(playerName = names[index]))
            index++
        }

        _game.value = _game.value.copy(
            player = playerAux
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    updateGame(_game.value.toDomain())

                    Log.i(TAG, "updateGame: listorti")
                    Log.i(TAG, "_game.value: ${_game.value}")

                    _uiState.value = GenericoUiState.VIEW_POINTS
                } catch (e:Exception){
                    Log.i(TAG, "updateGame room: No se actualizo game")
                    Log.i(TAG, "updateGame error: ${e.message}")

                }
            }
        }



    }

    fun updatePoints(points: List<Int>) {
        _game.value = _game.value.copy(
            player = _game.value.player.map {item -> item.copy( playerPoint = points[_game.value.player.indexOf(item)])}.toMutableList()
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    updateGame(_game.value.toDomain())

                    Log.i(TAG, "updateGame: listorti")
                    Log.i(TAG, "_game.value: ${_game.value}")

                    _uiState.value = GenericoUiState.VIEW_POINTS
                } catch (e:Exception){
                    Log.i(TAG, "updateGame room: No se actualizo game")
                    Log.i(TAG, "updateGame error: ${e.message}")

                }
            }
        }
    }

    fun changeView() {
        _uiState.value = GenericoUiState.SET_POINTS
    }
}

sealed class GenericoUiState(){
    object LOADING: GenericoUiState()
    object CREATE: GenericoUiState()
    object SELECT_NAME: GenericoUiState()
    object SET_POINTS: GenericoUiState()
    object VIEW_POINTS: GenericoUiState()
}