package com.example.yourpoints.domain.annotatorTruco

import android.util.Log
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTrucoGameUseCase @Inject constructor(private val trucoRepository: TrucoRepository) {

    private val TAG = "GetAllTrucoGameUseCase Intern Test"

    operator fun invoke(): Flow<List<TrucoUi>> =  try {
        trucoRepository.getAllGames().map { item -> item.map { it.toUi() } }
    } catch (e: Exception) {
        Log.i(TAG, "Error mensaje: ${e.message}")
        flow<List<TrucoUi>> { emptyList<TrucoUi>() }
    }

}