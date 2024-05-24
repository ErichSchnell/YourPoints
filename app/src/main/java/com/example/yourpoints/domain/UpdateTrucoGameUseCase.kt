package com.example.yourpoints.domain

import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.data.database.entities.toEntity
import com.example.yourpoints.domain.model.TrucoDomain
import javax.inject.Inject

class UpdateTrucoGameUseCase @Inject constructor(
    private val trucoRepository: TrucoRepository
) {
    suspend operator fun invoke(trucoGame: TrucoDomain){
        trucoRepository.updateTrucoGame(trucoGame.toEntity())
    }
}