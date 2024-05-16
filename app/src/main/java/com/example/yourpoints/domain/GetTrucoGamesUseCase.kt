package com.example.yourpoints.domain.model

import android.util.Log
import com.example.yourpoints.data.database.AnnotatorRepository
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val TAG = "GetTrucoGamesUseCase Intern Test"
class GetTrucoGamesUseCase @Inject constructor(private val annotatorRepository: AnnotatorRepository) {
    suspend operator fun invoke() {
        Log.i(TAG, "invoke: hola")
    }
/*
        Flow<List<TrucoUi>> {
        Log.i(TAG, "antes del try")
        return try {
        Log.i(TAG, "en el try")
            annotatorRepository.getAllTrucoGamesFromDatabase().map { items -> items.map { it.toUi() } }
        } catch (e:Exception){
        Log.i(TAG, "en el catch")
            flow { emptyList<TrucoUi>() }
        }
*/
}