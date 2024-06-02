package com.example.yourpoints.data

import com.example.yourpoints.data.database.dao.GenericoDao
import com.example.yourpoints.data.database.dao.TrucoDao
import com.example.yourpoints.data.database.entities.GenericoEntity
import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.domain.model.GenericoDomain
import com.example.yourpoints.domain.model.TrucoDomain
import com.example.yourpoints.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "LocalRepository Intern Test"
@Singleton
class TrucoRepository @Inject constructor(
    private val trucoDao: TrucoDao,
) {
    /*
    * ----------------- TRUCO DAO ---------------------------
    * */
    fun getAllGames(): Flow<List<TrucoDomain>>{
        return trucoDao.getGames().map{ item -> item.map{ it.toDomain() } }
    }
    fun getTrucoGameFromDatabase(id:Int): TrucoDomain? {
        return trucoDao.getGame(id)?.toDomain()
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
    /*
    * ----------------------------------------------------
    * */

}

@Singleton
class GenericoRepository @Inject constructor(
    private val genericoDao: GenericoDao,
) {
    /*
    * ----------------- GENERICO DAO ---------------------------
    * */
    fun getAllGames(): Flow<List<GenericoDomain>> {
        return genericoDao.getGames().map{ item -> item.map{ it.toDomain() } }
    }
    fun getGenericoGameFromDatabase(id:Int): GenericoDomain {
        return genericoDao.getGame(id).toDomain()
    }
    suspend fun addGenericoGame(genericoEntity: GenericoEntity){
        genericoDao.addGame(genericoEntity)
    }
    suspend fun updateGenericoGame(genericoEntity: GenericoEntity){
        genericoDao.updateGame(genericoEntity)
    }
    suspend fun deleteGenericoGame(genericoEntity: GenericoEntity){
        genericoDao.deleteGame(genericoEntity)
    }
    /*
    * ----------------------------------------------------
    * */

}