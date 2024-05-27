package com.example.yourpoints.domain.annotatorTruco

import android.util.Log
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import javax.inject.Inject


class GetTrucoGameUseCase @Inject constructor(
    private val trucoRepository: TrucoRepository
) {

    private val TAG = "GetTrucoGameUseCase Intern Test"

    operator fun invoke(id:Int) =  try {
        trucoRepository.getTrucoGameFromDatabase(id).toUi()
    } catch (e: Exception) {
        Log.i(TAG, "Error mensaje: ${e.message}")
        TrucoUi()
    }

}