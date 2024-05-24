package com.example.yourpoints.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourpoints.domain.DeleteTrucoGameUseCase
import com.example.yourpoints.domain.GetAllGamesUseCase
import com.example.yourpoints.domain.UpdateTrucoGameUseCase
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
    private val deleteGame: DeleteTrucoGameUseCase,
    private val updateGame: UpdateTrucoGameUseCase
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
//                        _gameSelected.value = verifyGameSalected()

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

    fun deleteOldGame(id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _games.value.forEach{
                        if (it is TrucoUi && it.id == id){
                            deleteGame(it.toDomain())
                            //_gameSelected.value = verifyGameSalected()
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