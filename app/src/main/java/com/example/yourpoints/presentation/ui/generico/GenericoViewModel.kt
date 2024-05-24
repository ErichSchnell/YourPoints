package com.example.yourpoints.presentation.ui.generico

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class GenericoViewModel @Inject constructor(

): ViewModel() {

    private val _uiState = MutableStateFlow<GenericoUiState>(GenericoUiState.LOADING)
    val uiState:StateFlow<GenericoUiState> = _uiState

    fun initGenerico(gameId: Int) {
        if (gameId == 0){
            _uiState.value = GenericoUiState.CREATE
        } else {
            _uiState.value = GenericoUiState.SUCCESS
        }
    }

    private fun getDate(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(Calendar.getInstance().time)
    }
}

sealed class GenericoUiState(){
    object LOADING: GenericoUiState()
    object CREATE: GenericoUiState()
    object SUCCESS: GenericoUiState()
}