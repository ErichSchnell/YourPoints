package com.example.yourpoints.domain.model

import com.example.yourpoints.data.database.AnnotatorRepository
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrucoGamesUseCase @Inject constructor(
    private val annotatorRepository: AnnotatorRepository
) {
    suspend operator fun invoke() = annotatorRepository.getAllTrucoGamesFromDatabase().map { items -> items.map { it.toUi() } }

}