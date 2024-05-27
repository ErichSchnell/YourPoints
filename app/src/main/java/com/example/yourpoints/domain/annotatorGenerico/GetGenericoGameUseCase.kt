package com.example.yourpoints.domain.annotatorGenerico

import android.util.Log
import com.example.yourpoints.data.GenericoRepository
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.presentation.model.GenericoUi
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import javax.inject.Inject

class GetGenericoGameUseCase @Inject constructor(
    private val genericoRepository: GenericoRepository
) {

    private val TAG = "GetGenericoGameUseCase Intern Test"

    operator fun invoke(id:Int) =  try {
        genericoRepository.getGenericoGameFromDatabase(id).toUi()
    } catch (e: Exception) {
        Log.i(TAG, "Error mensaje: ${e.message}")
        GenericoUi(player = mutableListOf())
    }

}