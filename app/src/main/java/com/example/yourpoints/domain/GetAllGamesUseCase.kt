package com.example.yourpoints.domain

import com.example.yourpoints.data.GenericoRepository
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.domain.model.GenericoDomain
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.presentation.model.GenericoUi
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetAllGamesUseCase @Inject constructor(
    private val trucoRepository: TrucoRepository,
    private val genericoRepository: GenericoRepository
) {
    operator fun invoke(): Flow<List<Any>> {

        val trucoGames = trucoRepository.getAllGames().map { item -> item.map { it.toUi() } }
        val genericoGames = genericoRepository.getAllGames().map { item -> item.map { it.toUi() } }

        val game = merge(trucoGames, genericoGames)

        return game
    }
}