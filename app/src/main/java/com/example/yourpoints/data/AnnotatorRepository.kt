package com.example.yourpoints.data.database

import com.example.yourpoints.data.database.dao.TrucoDao
import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnnotatorRepository @Inject constructor(
    private val trucoDao: TrucoDao
) {

    suspend fun getAllTrucoGamesFromDatabase() = trucoDao.getGames().map {items -> items.map { it.toDomain() } }

    suspend fun getTrucoGameFromDatabase(id:String) = trucoDao.getGame(id)

    suspend fun addTrucoGame(trucoEntity: TrucoEntity){
        trucoDao.addGame(trucoEntity)
    }

}