package com.example.yourpoints.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourpoints.domain.model.GetTrucoGamesUseCase
import com.example.yourpoints.presentation.model.TrucoUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val TAG = "HomeViewModel Intern Test"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGames: GetTrucoGamesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.LOADING)
    val uiState:StateFlow<HomeViewState> = _uiState

    private val _games = MutableStateFlow<List<TrucoUi>>( mutableListOf() )
    val games:StateFlow<List<TrucoUi>> = _games

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    getGames()
//                    getGames().collect {
//                        _games.value = it
//                        if (it.isNotEmpty()){
//                            _uiState.value = HomeViewState.SUCCESS
//                        } else {
//                            _uiState.value = HomeViewState.ERROR
//                        }
//                    }
                } catch (e:Exception){
                    Log.i(TAG, "Exploto todo ")
                    _uiState.value = HomeViewState.ERROR
                }

            }
        }
    }


    fun navigateTo(navigateToAnnotator: () -> Unit) {
        navigateToAnnotator()
    }
}

sealed class HomeViewState{
    object LOADING: HomeViewState()
    object SUCCESS: HomeViewState()
    object ERROR: HomeViewState()
}