package com.example.yourpoints.presentation.ui.home

import android.os.Build
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourpoints.domain.annotatorGenerico.DeleteGenericoGameUseCase
import com.example.yourpoints.domain.annotatorTruco.DeleteTrucoGameUseCase
import com.example.yourpoints.domain.annotatorGenerico.GetAllGenericoGameUseCase
import com.example.yourpoints.domain.annotatorTruco.GetAllTrucoGameUseCase
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.GenericoUi
import com.example.yourpoints.presentation.model.TrucoUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val TAG = "HomeViewModel Intern Test"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesTruco: GetAllTrucoGameUseCase,
    private val getGamesGenerico: GetAllGenericoGameUseCase,
    private val deleteTrucoGame: DeleteTrucoGameUseCase,
    private val deleteGenericoGame: DeleteGenericoGameUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.LOADING)
    val uiState:StateFlow<HomeViewState> = _uiState

    private val _games = MutableStateFlow<List<Any>>( mutableListOf() )
    val games:StateFlow<List<Any>> = _games

    private val _gamesTruco = MutableStateFlow<List<TrucoUi>>( mutableListOf() )

    private val _gamesGenerico = MutableStateFlow<List<GenericoUi>>( mutableListOf() )

    private val _gameSelected = MutableStateFlow(false)
    val gameSelected:StateFlow<Boolean> = _gameSelected

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getTruco()
                getGenerico()
            }
        }
    }
    fun getTruco(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    getGamesTruco().collect {
                        Log.i(TAG, "getTruco: $it")

                        _gamesTruco.value = it

                        actualizarGames()

                        _gameSelected.value = verifyGameSelected()

                        if (_games.value.isNotEmpty()){
                            _uiState.value = HomeViewState.SUCCESS
                        } else {
                            _uiState.value = HomeViewState.ERROR
                        }
                    }
                } catch (e:Exception){
                    Log.i(TAG, "Exploto todo ")
                    Log.i(TAG, "Error mensaje: ${e.message}")
                    _uiState.value = HomeViewState.ERROR
                }
            }
        }
    }

    fun getGenerico(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    getGamesGenerico().collect {
                        Log.i(TAG, "getGenerico: $it")

                        _gamesGenerico.value = it

                        actualizarGames()

                        _gameSelected.value = verifyGameSelected()

                        if (_games.value.isNotEmpty()){
                            _uiState.value = HomeViewState.SUCCESS
                        } else {
                            _uiState.value = HomeViewState.ERROR
                        }
                    }
                } catch (e:Exception){
                    Log.i(TAG, "Exploto todo ")
                    Log.i(TAG, "Error mensaje: ${e.message}")
                    _uiState.value = HomeViewState.ERROR
                }
            }
        }
    }
    private fun actualizarGames(){
        val gameAux = (_gamesTruco.value + _gamesGenerico.value).toMutableList()
        _games.value = ordenarGamesPorFecha(gameAux)
    }
    private fun ordenarGamesPorFecha(ListGameAux: MutableList<Any>): List<Any>  {//
        var dateIAux: Int
        var dateJAux: Int
        var gameAux: Any
        for( i in ListGameAux.indices){
            dateIAux = if(ListGameAux[i] is TrucoUi) (ListGameAux[i] as TrucoUi).id else (ListGameAux[i] as GenericoUi).id

            for(j in ListGameAux.indices){
                dateJAux = if(ListGameAux[j] is TrucoUi) (ListGameAux[j] as TrucoUi).id else (ListGameAux[j] as GenericoUi).id

                if (dateIAux > dateJAux){

                    gameAux = ListGameAux[i]
                    ListGameAux[i] = ListGameAux[j]
                    ListGameAux[j] = gameAux

                    dateIAux = if(ListGameAux[i] is TrucoUi) (ListGameAux[i] as TrucoUi).id else (ListGameAux[i] as GenericoUi).id
                }

            }
            Log.i(TAG, "ordenarGamesPorFecha ListGameAux: $ListGameAux")
        }
        return ListGameAux
    }

    fun deleteGames(){
        val gamesToDelete: MutableList<Any> = mutableListOf()
        _games.value.forEach {
            when(it){
                is TrucoUi -> {
                    if (it.selected) gamesToDelete.add(it)
                }
                is GenericoUi -> {
                    if (it.selected) gamesToDelete.add(it)
                }
                else -> {

                }
            }
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    gamesToDelete.forEach{
                        when(it) {
                            is TrucoUi -> {
                                deleteTrucoGame(it.toDomain())
                            }
                            is GenericoUi -> {
                                deleteGenericoGame(it.toDomain())
                            }
                        }
                    }

                } catch (e:Exception){
                    Log.i(TAG, "deleteOldGame: NO SE BORRO")
                    Log.i(TAG, "Error mensaje: ${e.message}")
                }
            }
        }
    }

    fun selectGame(index: Int){
        _games.value = _games.value.map {
            if (_games.value[index] == it) {
                when (it) {
                    is TrucoUi -> {
                        Log.i(TAG, "selectGame: $it")
                        it.changeSelect()
                    }
                    is GenericoUi -> {
                        Log.i(TAG, "selectGame: $it")
                        it.changeSelect() }
                    else -> {}
                }
            } else {
                it
            }
        }
        Log.i(TAG, "selectGame: ${_games.value[index]}")
        _gameSelected.value = verifyGameSelected()
    }

    fun selectAll() {
        var sizeAux = 0

        _games.value = _games.value.map {
            when (it) {
                is TrucoUi -> {
                    if (!it.selected) {
                        it.changeSelect()
                    } else {
                        sizeAux++
                        it
                    }
                }
                is GenericoUi -> {
                    if (!it.selected) {
                        it.changeSelect()
                    } else {
                        sizeAux++
                        it
                    }
                }
                else -> {}
            }
        }

        if (_games.value.size == sizeAux) {
            _games.value = _games.value.map {
                when (it) {
                    is TrucoUi -> it.changeSelect()

                    is GenericoUi -> it.changeSelect()

                    else -> {}
                }
            }
        }

        _gameSelected.value = verifyGameSelected()
    }

    fun navigateTo(navigateToAnnotator: (Int) -> Unit, id:Int = 0) {
        navigateToAnnotator(id)
    }



    private fun verifyGameSelected(): Boolean{
        _games.value.forEach {
            Log.i(TAG, "verifyGameSelected: $it")
            when(it){
                is TrucoUi -> {
                    if (it.selected) return true
                }
                is GenericoUi -> {
                    if (it.selected) return true
                }
                else-> {}
            }
        }
        return false
    }


}

sealed class HomeViewState{
    object LOADING: HomeViewState()
    object SUCCESS: HomeViewState()
    object ERROR: HomeViewState()
}