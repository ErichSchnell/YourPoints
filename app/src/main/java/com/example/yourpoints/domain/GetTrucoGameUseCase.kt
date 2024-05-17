package com.example.yourpoints.domain

import com.example.yourpoints.data.AnnotatorRepository
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrucoGameUseCase @Inject constructor(
    private val annotatorRepository: AnnotatorRepository
) {
    operator fun invoke(id:Int) =  try {
        annotatorRepository.getTrucoGameFromDatabase(id).toUi()
    } catch (e: Exception) {
        TrucoUi()
    }

}