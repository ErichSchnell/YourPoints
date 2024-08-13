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
import com.example.yourpoints.presentation.ui.theme.string_generic_name_empty
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

    private val _loading = MutableStateFlow(false)
    val loading:StateFlow<Boolean> = _loading

    private val _showToast = MutableStateFlow("")
    val showToast:StateFlow<String> = _showToast


    private val _playerSelected = MutableStateFlow<GenericoPlayerUi?>(null)
    val playerSelected:StateFlow<GenericoPlayerUi?> = _playerSelected

    private val _showDialogChangeName = MutableStateFlow(false)
    val showDialogChangeName:StateFlow<Boolean> = _showDialogChangeName

    fun initGenerico(gameId: Int) {
        if (gameId == 0){
            _uiState.value = GenericoUiState.CREATE
        } else {
            getGenericoGame(gameId)
        }
    }

    fun getGenericoGame(gameId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _game.value = getGame(gameId)
                    Log.i(TAG, "Partida Encontrada. ID:$gameId")
                    _uiState.value = GenericoUiState.GAME

                } catch (e:Exception){
                    Log.i(TAG, "Partida No Encontrada. ID:$gameId")
                    Log.i(TAG, "Error mensaje: ${e.message}")
                }
            }
        }
    }

    private fun getDate(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(Calendar.getInstance().time)
    }

    fun createGame(
        name: String,
        pointFlag: Boolean,
        pointInit: Int,
        pointFinish: Int,
        finishToWin: Boolean,
        roundFlag: Boolean,
        rounds: Int,
        cantPlayers: Int,
    ) {
        if (name.isEmpty()){ _showToast.value = string_generic_name_empty ; return }

        val players: MutableList<GenericoPlayerUi> = mutableListOf()
        for(i in 0 until cantPlayers){
            players.add(GenericoPlayerUi(playerName = "Player ${i + 1}", playerPoint = pointInit))
        }


        _game.value = GenericoUi(
            id = getDate().hashCode(),
            dataCreated = getDate(),
            name = name,
            withPoints = pointFlag,
            pointToInit = pointInit,
            pointToFinish = pointFinish,
            finishToWin = finishToWin,
            withRounds = roundFlag,
            roundMax = rounds,
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

        for(i in names.indices){
            for(j in i+1 until names.size){
                if (names[i] == names[j]) return
            }
        }

        _game.value = _game.value.setPlayers(
            _game.value.player.map { it.setName(names[index++]) }
        )

        _uiState.value = GenericoUiState.GAME

        updateRoomGame()
    }

    fun updatePoints(points: List<Int>) {

        var index = 0
        _game.value = _game.value
            .setPlayers(
                players = _game.value.player.map { it.setPoint(points[index++])}
            )
            .incRound()
            .setPoint(false)

        verifyPlayerReachedGoal()

        updateRoomGame()
    }
    private fun verifyPlayerReachedGoal(){
        var gameFinished:Boolean = _game.value.finished
        if (_game.value.withPoints){
            _game.value = _game.value.setPlayers(
                players = _game.value.player.map {
                    if(it.playerPoint >= _game.value.pointToFinish && it.addVictoryFlag){
                        gameFinished = true
                        it.addVictories().setAddVictoryFlag(false)
                    } else {
                        if (it.playerPoint < _game.value.pointToFinish && !it.addVictoryFlag){
                            it.lessVictories().setAddVictoryFlag(true)
                        } else {
                            it
                        }
                    }
                }
            )
        }
        _game.value = _game.value.setFinish(gameFinished)
    }
    private fun updateRoomGame(){
        Log.i(TAG, "updateGenericoGame: listorti")
        _loading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    updateGame(_game.value.toDomain())
                    _loading.value = false
                } catch (e:Exception){
                    Log.i(TAG, "updateGenericoGame room: No se actualizo game")
                    Log.i(TAG, "updateGenericoGame error: ${e.message}")
                }

            }
        }
    }


    fun changeViewSetPoints() {
        _game.value = _game.value.setPoint(true)
        Log.i(TAG, "changeViewSetPoints: ${_game.value.isSetPoint}")
    }

    fun resetGame() {
        _game.value = _game.value.resetGame()
        updateRoomGame()
    }

    fun addPLayer() {
        val players = _game.value.player.toMutableList()

        players.add(
            GenericoPlayerUi(
                playerName = "Player ${players.size + 1}", playerPoint = 0
            )
        )

        _game.value = _game.value.setPlayers(players.toList())
        updateRoomGame()
    }

    fun deletePLayer() {
        if(_playerSelected.value == null) return

        val players = _game.value.player.toMutableList()

        if (players.remove(_playerSelected.value)){
            _game.value = _game.value.setPlayers(players.toList())

            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    updateRoomGame()
                }
            }


        } else {
            Log.i(TAG, "deletePLayer: error")
        }

        _playerSelected.value = null

    }

    fun changeName(name:String) {
        if(_playerSelected.value == null) return

        _game.value.player.map {playerAux ->
            if (playerAux.playerName == name) return
        }
        val players = _game.value.player.map {playerAux ->
            if (playerAux.playerName == _playerSelected.value!!.playerName){
                playerAux.copy(playerName = name)
            } else {
                playerAux
            }
        }

        _game.value = _game.value.setPlayers(players.toList())
        updateRoomGame()

        _playerSelected.value = null
        _showDialogChangeName.value = false

    }
    fun setPlayerSelected(player: GenericoPlayerUi?) {
        Log.i(TAG, "setPlayerSelected: $player")
        _playerSelected.value = player
    }

    fun setDialogChangeName(value: Boolean) {
        _showDialogChangeName.value = value
    }

    fun clearToast() {
        _showToast.value = ""
    }
}

sealed class GenericoUiState(){
    object LOADING: GenericoUiState()
    object CREATE: GenericoUiState()
    object SELECT_NAME: GenericoUiState()
    object GAME: GenericoUiState()
}