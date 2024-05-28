package com.example.yourpoints.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yourpoints.data.database.entities.GenericoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GenericoDao {
    @Query("select * from generico_table order by dataCreated desc")
    fun getGames(): Flow<List<GenericoEntity>>

    @Query("select * from generico_table where id = :id ")
    fun getGame(id: Int): GenericoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(game: GenericoEntity)

    @Update
    suspend fun updateGame(game: GenericoEntity)

    @Delete
    suspend fun deleteGame(item: GenericoEntity)
}
