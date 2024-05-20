package com.example.yourpoints.data

import android.util.Log
import com.example.yourpoints.data.database.dao.TrucoDao
import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "AnnotatorRepository Intern Test"
@Singleton
class AnnotatorRepository @Inject constructor(
    private val trucoDao: TrucoDao
) {

    fun getAllTrucoGamesFromDatabase(): Flow<List<TrucoDomain>>{
        return trucoDao.getGames().map{ item -> item.map{ it.toDomain() } }
    }

    fun getTrucoGameFromDatabase(id:Int): TrucoDomain {
        return trucoDao.getGame(id).toDomain()
    }

    suspend fun addTrucoGame(trucoEntity: TrucoEntity){
        trucoDao.addGame(trucoEntity)
    }

    suspend fun updateTrucoGame(trucoEntity: TrucoEntity){
        trucoDao.updateGame(trucoEntity)
    }

    suspend fun deleteTrucoGame(trucoEntity: TrucoEntity){
        trucoDao.deleteGame(trucoEntity)
    }

}