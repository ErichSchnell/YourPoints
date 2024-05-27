package com.example.yourpoints.domain.annotatorGenerico

import com.example.yourpoints.data.GenericoRepository
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.data.database.entities.toEntity
import com.example.yourpoints.domain.model.GenericoDomain
import com.example.yourpoints.domain.model.TrucoDomain
import javax.inject.Inject

class AddGenericoGameUseCase @Inject constructor(
    private val genericoRepository: GenericoRepository
) {
    suspend operator fun invoke(genericoDomain: GenericoDomain){
        genericoRepository.addGenericoGame(genericoDomain.toEntity())
    }
}