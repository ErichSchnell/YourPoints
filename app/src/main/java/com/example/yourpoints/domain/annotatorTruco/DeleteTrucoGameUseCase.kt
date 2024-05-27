package com.example.yourpoints.domain.annotatorTruco

import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.data.database.entities.toEntity
import com.example.yourpoints.domain.model.TrucoDomain
import javax.inject.Inject

class DeleteTrucoGameUseCase  @Inject constructor(
    private val trucoRepository: TrucoRepository
) {
    suspend operator fun invoke(trucoGame: TrucoDomain){
        trucoRepository.deleteTrucoGame(trucoGame.toEntity())
    }
}