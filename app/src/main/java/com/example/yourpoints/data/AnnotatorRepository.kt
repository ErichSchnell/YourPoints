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

const val TAG = "AnnotatorRepository Intern Test"
@Singleton
class AnnotatorRepository @Inject constructor(
    private val trucoDao: TrucoDao
) {

    suspend fun getAllTrucoGamesFromDatabase(): List<TrucoDomain>{
        return trucoDao.getGames().map { it.toDomain() }
    }

    suspend fun getTrucoGameFromDatabase(id:String): TrucoDomain {
        return trucoDao.getGame(id).toDomain()
    }

    suspend fun addTrucoGame(trucoEntity: TrucoEntity){
        trucoDao.addGame(trucoEntity)
    }

}