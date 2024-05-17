package com.example.yourpoints.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yourpoints.data.database.entities.TrucoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrucoDao {
    @Query("select * from truco_table order by id desc")
    suspend fun getGames(): List<TrucoEntity>

    @Query("select * from truco_table where id = :id ")
    suspend fun getGame(id: String): TrucoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(game: TrucoEntity)
}