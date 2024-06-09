package com.example.yourpoints.domain.annotatorGenerico

import android.util.Log
import com.example.yourpoints.data.GenericoRepository
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.presentation.model.GenericoUi
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllGenericoGameUseCase  @Inject constructor(
    private val genericoRepository: GenericoRepository
) {

    private val TAG = "GetAllGenericoGameUseCase Intern Test"

    operator fun invoke(): Flow<List<GenericoUi>> =  try {
        genericoRepository.getAllGames().map { item -> item.map { it.toUi() } }
    } catch (e: Exception) {
        Log.i(TAG, "Error mensaje: ${e.message}")
        flow { emptyList<GenericoUi>() }
    }

}