package com.example.yourpoints.domain

import com.example.yourpoints.data.database.AnnotatorRepository
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrucoGameUseCase @Inject constructor(
    private val annotatorRepository: AnnotatorRepository
) {
    suspend operator fun invoke(id:String) {
        //annotatorRepository.getTrucoGameFromDatabase(id).map { it.toDomain().toUi() }
    }

}