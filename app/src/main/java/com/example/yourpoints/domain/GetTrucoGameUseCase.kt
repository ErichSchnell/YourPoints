package com.example.yourpoints.domain

import android.util.Log
import com.example.yourpoints.data.AnnotatorRepository
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import javax.inject.Inject

private const val TAG = "GetTrucoGameUseCase Intern Test"
class GetTrucoGameUseCase @Inject constructor(
    private val annotatorRepository: AnnotatorRepository
) {
    operator fun invoke(id:Int) =  try {
        annotatorRepository.getTrucoGameFromDatabase(id).toUi()
    } catch (e: Exception) {
        Log.i(TAG, "Error mensaje: ${e.message}")
        TrucoUi()
    }

}