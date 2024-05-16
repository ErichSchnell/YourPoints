package com.example.yourpoints.data.database

import android.util.Log
import com.example.yourpoints.data.database.dao.TrucoDao
import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val TAG = "AnnotatorRepository Intern Test"
class AnnotatorRepository @Inject constructor(
    //private val trucoDao: TrucoDao
) {

    suspend fun getAllTrucoGamesFromDatabase(){//:  Flow<List<TrucoDomain>>
        Log.i(TAG, "antes del try")
//        return try {
//            Log.i(TAG, "en el try")
//            trucoDao.getGames().map { items -> items.map { it.toDomain() } }
//            flow { emptyList<TrucoDomain>() }
//        } catch (e:Exception){
//            Log.i(TAG, "en el catch")
//            flow { emptyList<TrucoDomain>() }
//        }

    }

    suspend fun getTrucoGameFromDatabase(id:String) {
        //trucoDao.getGame(id)
    }

    suspend fun addTrucoGame(trucoEntity: TrucoEntity){
        //trucoDao.addGame(trucoEntity)
    }

}