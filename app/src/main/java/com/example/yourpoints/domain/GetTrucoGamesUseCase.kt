package com.example.yourpoints.domain

import android.util.Log
import com.example.yourpoints.data.AnnotatorRepository
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val TAG = "GetTrucoGamesUseCase Intern Test"
class GetTrucoGamesUseCase @Inject constructor(private val annotatorRepository: AnnotatorRepository) {
    operator fun invoke(): Flow<List<TrucoUi>> =  try {
        annotatorRepository.getAllTrucoGamesFromDatabase().map { item -> item.map { it.toUi() } }
    } catch (e: Exception) {
        flow<List<TrucoUi>> { emptyList<TrucoUi>() }
    }

}