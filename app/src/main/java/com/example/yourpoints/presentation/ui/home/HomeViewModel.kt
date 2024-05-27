package com.example.yourpoints.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourpoints.domain.annotatorTruco.DeleteTrucoGameUseCase
import com.example.yourpoints.domain.GetAllGamesUseCase
import com.example.yourpoints.domain.annotatorTruco.UpdateTrucoGameUseCase
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.GenericoUi
import com.example.yourpoints.presentation.model.TrucoUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "HomeViewModel Intern Test"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGames: GetAllGamesUseCase,
    private val deleteGame: DeleteTrucoGameUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.LOADING)
    val uiState:StateFlow<HomeViewState> = _uiState

    private val _games = MutableStateFlow<List<Any>>( mutableListOf() )
    val games:StateFlow<List<Any>> = _games

    private val _gameSelected = MutableStateFlow(false)
    val gameSelected:StateFlow<Boolean> = _gameSelected

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    getGames().collect {

                        _games.value = it
                        _gameSelected.value = verifyGameSelected()

                        if (it.isNotEmpty()){
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
                                deleteGame(it.toDomain())
                            }
                            is GenericoUi -> {
                                //deleteGame(it.toDomain())
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
                    is GenericoUi -> { it }
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
                        //it.changeSelect()
                        it
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

                    is GenericoUi -> it

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