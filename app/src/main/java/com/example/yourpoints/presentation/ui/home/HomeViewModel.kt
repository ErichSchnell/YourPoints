package com.example.yourpoints.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val TAG = "HomeViewModel Intern Test"

@HiltViewModel
class HomeViewModel @Inject constructor(

): ViewModel() {

    private val _uiState = MutableStateFlow(HomeViewState.LOADING)
    val uiState:StateFlow<HomeViewState> = _uiState


    fun navigateTo(navigateToAnnotator: () -> Unit) {
        navigateToAnnotator()
    }
}

sealed class HomeViewState{
    object LOADING: HomeViewState()
    object SUCCESS: HomeViewState()
}