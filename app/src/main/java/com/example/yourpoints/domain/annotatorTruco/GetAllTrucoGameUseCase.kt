package com.example.yourpoints.domain.annotatorTruco

import android.util.Log
import com.example.yourpoints.data.TrucoRepository
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.TypePlayer
import com.example.yourpoints.domain.model.toDomain
import com.example.yourpoints.presentation.model.TrucoPlayerUi
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.model.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class  GetAllTrucoGameUseCase @Inject constructor(private val trucoRepository: TrucoRepository) {

    private val TAG = "GetAllTrucoGameUseCase Intern Test"

    operator fun invoke(): Flow<List<TrucoUi>> =
        trucoRepository.getAllGames().map { item ->
            item?.map {
                it?.toUi() ?: TrucoUi()
            } ?: emptyList<TrucoUi>()
        }
}