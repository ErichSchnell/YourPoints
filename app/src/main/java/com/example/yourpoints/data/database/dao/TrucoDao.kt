package com.example.yourpoints.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yourpoints.data.database.entities.TrucoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrucoDao {
    @Query("select * from truco_table order by dataCreated desc")
    fun getGames(): Flow<List<TrucoEntity?>?>

    @Query("select * from truco_table where id = :id ")
    fun getGame(id: Int): TrucoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(game: TrucoEntity)

    @Update
    suspend fun updateGame(game: TrucoEntity)

    @Delete
    suspend fun deleteGame(item: TrucoEntity)
}