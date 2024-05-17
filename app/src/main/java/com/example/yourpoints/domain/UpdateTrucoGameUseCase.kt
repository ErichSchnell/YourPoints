package com.example.yourpoints.domain

import com.example.yourpoints.data.AnnotatorRepository
import com.example.yourpoints.data.database.entities.toEntity
import com.example.yourpoints.domain.model.TrucoDomain
import javax.inject.Inject

class UpdateTrucoGameUseCase @Inject constructor(
    private val annotatorRepository: AnnotatorRepository
) {
    suspend operator fun invoke(trucoGame: TrucoDomain){
        annotatorRepository.updateTrucoGame(trucoGame.toEntity())
    }
}